package clientapi

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.time.LocalDate
import java.util.*
import java.util.concurrent.TimeUnit

object SimpleJWT {
    const val sessionField = "session"
    const val encPasswordField = "encp"
    private val algorithm = Algorithm.HMAC256("YOUR_SUPER_PUPER_SECRET_MYSTICALLY_PHRASE")
    val authTokenVerifier = JWT.require(algorithm)
        .build()

    fun signAuthToken(userId: Int, session: String): String = JWT.create()
        .withIssuer(userId.toString())
        .withClaim(sessionField, session)
        .withExpiresAt(Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(2)))
        .sign(algorithm)

    fun signRefreshToken(userId: Int, session: String, encPassword: String): String = JWT.create()
        .withIssuer(userId.toString())
        .withClaim(sessionField, session)
        .withClaim(encPasswordField, encPassword)
        .withExpiresAt(Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(90)))
        .sign(algorithm)
}