package clientapi

import io.ktor.http.HttpStatusCode
import java.lang.Exception

class ApiException(val errorCode: HttpStatusCode, val reason: Reason) : Exception() {
    enum class Reason {
        PASSWORD_EASY,
        EMAIL_ALREADY_REGISTERED,
        EMAIL_INVALID,
        EMAIL_NOT_REGISTERED,
        PASSWORD_INCORRECT,
        SESSION_EXPIRED,
        REFRESH_TOKEN_EXPIRED,
        CODE_INVALID_OR_EXPIRED,
    }
}