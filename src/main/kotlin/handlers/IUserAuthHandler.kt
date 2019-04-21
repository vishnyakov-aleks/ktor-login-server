package handlers

import pojo.User

interface IUserAuthHandler {
    fun createUser(email: String, password: String): Boolean
    fun authUser(email: String, password: String): User
    fun validateSession(session: String)
    fun createTokenWithSession(userId: Int, userAgent: String): String
    fun sendPasswordResetCode(email: String)
    fun resetPassword(code: String, newPassword: String)
    fun createRefreshToken(authToken: String): String
    fun extendAuthToken(refreshToken: String, userAgent: String): String
}