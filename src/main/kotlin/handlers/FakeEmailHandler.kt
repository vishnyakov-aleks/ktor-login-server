package handlers

class FakeEmailHandler : IEmailHandler {
    override fun sendResetPassMail(email: String, code: String) {
        println("Email sent: $email, $code")
    }
}