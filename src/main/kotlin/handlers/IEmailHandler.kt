package handlers

interface IEmailHandler {
    fun sendResetPassMail(email: String, code: String)
}