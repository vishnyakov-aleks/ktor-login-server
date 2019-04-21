package clientapi

import clientapi.SimpleJWT.sessionField
import clientapi.request.*
import clientapi.response.ErrorResponse
import clientapi.response.OkResponse
import clientapi.result.AuthTokenResult
import com.fasterxml.jackson.databind.SerializationFeature
import config.Config
import handlers.IUserAuthHandler
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.authenticate
import io.ktor.auth.jwt.jwt
import io.ktor.auth.parseAuthorizationHeader
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.request.receive
import io.ktor.request.userAgent
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.routing.routing
import io.netty.util.internal.ThrowableUtil
import kodein
import org.kodein.di.generic.instance

private val userAuthHandler by kodein.instance<IUserAuthHandler>()

fun Application.module() {
    install(StatusPages) {
        exception<ApiException> { exception ->
            call.respond(exception.errorCode,
                ErrorResponse(exception.errorCode, exception.reason, null)
            )
        }

        exception<Exception> { exception ->
            exception.printStackTrace()
            if (Config.DEBUG) {
                call.respond(HttpStatusCode.Unauthorized, ErrorResponse(HttpStatusCode.InternalServerError, null,
                    ThrowableUtil.stackTraceToString(exception)))
            }
        }
    }

    install(ContentNegotiation) { jackson { enable(SerializationFeature.INDENT_OUTPUT) } }

    install(Authentication) {
        jwt {
            verifier(SimpleJWT.authTokenVerifier)
            validate {
                println(this.request.parseAuthorizationHeader()?.render())
                println(it.payload.expiresAt)
                val session = it.payload.getClaim(sessionField).asString()
                userAuthHandler.validateSession(session)
                UserIdPrincipal(session)
            }
        }
    }

    routing {
        route("/auth") {
            post("register") {
                val request = call.receive<RegisterReq>()
                userAuthHandler.createUser(request.email, request.password)
                call.respond(OkResponse(HttpStatusCode.Accepted))
            }

            post("login") {
                val request = call.receive<LoginReq>()
                val user = userAuthHandler.authUser(request.email, request.password)
                val authToken = userAuthHandler.createTokenWithSession(user.id,
                    call.request.userAgent() ?: "no userAgent")
                val refreshToken = userAuthHandler.createRefreshToken(authToken)
                call.respond(OkResponse(HttpStatusCode.OK, AuthTokenResult(authToken, refreshToken)))
            }

            post("resetpass/send-code") {
                val request = call.receive<ResetPassSendCodeReq>()
                userAuthHandler.sendPasswordResetCode(request.email)
                call.respond(OkResponse(HttpStatusCode.OK))
            }

            post("resetpass/set") {
                val request = call.receive<ResetPassApproveReq>()
                userAuthHandler.resetPassword(request.code, request.newPassword)
                call.respond(OkResponse(HttpStatusCode.Accepted))

            }

            post("refresh-token") {
                val request = call.receive<RefreshTokenReq>()
                val newAuthToken = userAuthHandler.extendAuthToken(request.refreshToken, call.request.userAgent()?: "no userAgent")
                val newRefreshToken = userAuthHandler.createRefreshToken(newAuthToken)
                call.respond(OkResponse(HttpStatusCode.OK, AuthTokenResult(newAuthToken, newRefreshToken)))
            }
        }

        authenticate {
            get("/test") {
                call.respond(OkResponse(HttpStatusCode.OK))
            }
        }

    }
}
