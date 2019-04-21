package clientapi.response

import clientapi.result.IResult
import io.ktor.http.HttpStatusCode

open class OkResponse (code: HttpStatusCode, val result: IResult? = null) : AbsResponse(code.value)
