package handlers

import clientapi.ApiException
import clientapi.ApiException.Reason.*
import clientapi.SimpleJWT
import io.ktor.http.HttpStatusCode
import kodein
import org.apache.commons.codec.binary.Base64
import org.apache.commons.codec.digest.Md5Crypt
import org.kodein.di.generic.instance
import org.postgresql.util.MD5Digest
import pojo.User
import repositories.ISessionsRepo
import repositories.IUserAuthRepo
import sun.security.provider.MD5
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

class UserAuthHandler : IUserAuthHandler {
    private val userRepo by kodein.instance<IUserAuthRepo>()
    private val sessionRepo by kodein.instance<ISessionsRepo>()
    private val emailHandler by kodein.instance<IEmailHandler>()
    private val codeSymbols = "ABCDEFGHJKMNPQRSTUXVWYZ12456789".toList()

    override fun createUser(email: String, password: String): Boolean {
        if (!isEmailValid(email)) throw ApiException(HttpStatusCode.Conflict, EMAIL_INVALID)
        if (!isPasswordStrong(password)) throw ApiException(HttpStatusCode.Conflict, PASSWORD_EASY)
        if (!isEmailNotRegistered(email)) throw ApiException(HttpStatusCode.Conflict, EMAIL_ALREADY_REGISTERED)
        val encryptedPassword = encryptPassword(password)
        userRepo.addUser(email, encryptedPassword, System.currentTimeMillis())
        return true
    }

    private fun isEmailValid(email: String): Boolean {
        val regexPattern = Pattern.compile("^[(a-zA-Z-0-9-_+.)]+@[(a-zA-Z-0-9-_+.)]+\\.[(a-zA-z)]{2,5}$")
        val regMatcher = regexPattern!!.matcher(email)
        return regMatcher!!.matches()
    }

    private fun isPasswordStrong(password: String): Boolean {
        return password.length >= 6 && !password.all { it.isDigit() }
    }

    private fun isEmailNotRegistered(email: String): Boolean {
        return userRepo.isEmailFree(email)
    }

    private fun getMd5Base64(encTarget: ByteArray): String {
        val mdEnc = MessageDigest.getInstance("MD5")
        // Encryption algorithmy
        val md5Base16 = BigInteger(1, mdEnc.digest(encTarget))     // calculate md5 hash
        return Base64.encodeBase64String(md5Base16.toByteArray()).trim()     // convert from base16 to base64 and remove the new line character
    }

    private fun encryptPassword(password: String) = getMd5Base64(password.toByteArray())

    override fun authUser(email: String, password: String): User {
        val user: User = userRepo.getUserByEmail(email) ?: throw ApiException(HttpStatusCode.Unauthorized, EMAIL_NOT_REGISTERED)

        val encPassword = encryptPassword(password)
        if (user.encPassword != encPassword) throw ApiException(HttpStatusCode.Unauthorized, PASSWORD_INCORRECT)
        return user
    }

    override fun validateSession(session: String) {
        if (sessionRepo.getExpireReasonCode(session) != 0)
            throw ApiException(HttpStatusCode.Unauthorized, SESSION_EXPIRED)
    }

    override fun createTokenWithSession(userId: Int, userAgent: String): String {
        val session = Md5Crypt.apr1Crypt("$userId:${System.currentTimeMillis()}", System.currentTimeMillis().toString())!!
        sessionRepo.addSession(userAgent, userId, session, System.currentTimeMillis())
        return SimpleJWT.signAuthToken(userId, session)
    }

    override fun sendPasswordResetCode(email: String) {
        val user = userRepo.getUserByEmail(email) ?: throw ApiException(HttpStatusCode.Conflict, EMAIL_NOT_REGISTERED)
        val code = generateCode()
        userRepo.addRestorePasswordCode(
            expireTime = System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1),
            code = code,
            userId = user.id)

        emailHandler.sendResetPassMail(email, code)
    }

    override fun resetPassword(code: String, newPassword: String) {
        if (!isPasswordStrong(newPassword))
            throw ApiException(HttpStatusCode.Conflict, PASSWORD_EASY)
        val restoreCode = userRepo.getPasswordRestoreCode(code.toUpperCase())
            ?: throw ApiException(HttpStatusCode.Conflict, CODE_INVALID_OR_EXPIRED)
        if (restoreCode.expireTime < System.currentTimeMillis())
            throw ApiException(HttpStatusCode.Conflict, CODE_INVALID_OR_EXPIRED)

        val encryptedPassword = encryptPassword(newPassword)
        userRepo.setPassword(restoreCode.userId, encryptedPassword)
        userRepo.expirePasswordResetCode(code)
    }

    override fun createRefreshToken(authToken: String): String {
        val decodedJWT = SimpleJWT.authTokenVerifier.verify(authToken)
        val session = decodedJWT.getClaim(SimpleJWT.sessionField).asString()
        val userId = decodedJWT.issuer.toInt()
        val encPassword = userRepo
            .getUserById(userId)!!
            .encPassword

        return SimpleJWT.signRefreshToken(userId, session, encPassword)
    }

    override fun extendAuthToken(refreshToken: String, userAgent: String): String {
        val decodedJWT = SimpleJWT.authTokenVerifier.verify(refreshToken)

        if (decodedJWT.expiresAt.time <= System.currentTimeMillis())
            throw ApiException(HttpStatusCode.Unauthorized, REFRESH_TOKEN_EXPIRED)

        val session = decodedJWT.getClaim(SimpleJWT.sessionField).asString()
        if (sessionRepo.getExpireReasonCode(session) != 0)
            throw ApiException(HttpStatusCode.Unauthorized, REFRESH_TOKEN_EXPIRED)

        val userId = decodedJWT.issuer.toInt()
        val newSession = Md5Crypt.apr1Crypt("$userId:${System.currentTimeMillis()}", System.currentTimeMillis().toString())!!
        sessionRepo.replaceSession(session, newSession)
        return SimpleJWT.signAuthToken(userId, newSession)
    }

    private fun generateCode(length: Int = 7): String {
        return codeSymbols.shuffled().take(length).joinToString("")
    }
}