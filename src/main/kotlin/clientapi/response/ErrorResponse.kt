package clientapi.response

import clientapi.ApiException
import io.ktor.http.HttpStatusCode

class ErrorResponse(code: HttpStatusCode, val reason: ApiException.Reason?, val stackTrace: String?) : AbsResponse(code.value)