import clientapi.module
import config.Config
import handlers.FakeEmailHandler
import handlers.IEmailHandler
import handlers.IUserAuthHandler
import handlers.UserAuthHandler
import io.ktor.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.eagerSingleton
import org.kodein.di.generic.singleton
import repositories.ISessionsRepo
import repositories.IUserAuthRepo
import repositories.SessionsRepo
import repositories.UserAuthRepo

lateinit var kodein: Kodein
    private set

fun main() {
    Config.load()

    kodein = Kodein {
        bind<IUserAuthHandler>() with singleton { UserAuthHandler() }
        bind<IEmailHandler>() with singleton { FakeEmailHandler() }
        bind<IUserAuthRepo>() with singleton { UserAuthRepo() }
        bind<ISessionsRepo>() with eagerSingleton { SessionsRepo() }
    }

    embeddedServer(
        Netty,
        port = 80,
        module = Application::module
    ).apply { start(wait = true) }
}
