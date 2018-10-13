import io.grpc.Server
import io.grpc.ServerBuilder
import io.ktor.application.ApplicationStopPreparing
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.ApplicationEngineEnvironment
import io.ktor.server.engine.ApplicationEngineFactory
import io.ktor.server.engine.BaseApplicationEngine
import java.util.concurrent.TimeUnit

object GRpc : ApplicationEngineFactory<GRpcApplicationEngine, GRpcApplicationEngine.Configuration> {
    override fun create(environment: ApplicationEngineEnvironment, configure: GRpcApplicationEngine.Configuration.() -> Unit): GRpcApplicationEngine {
        return GRpcApplicationEngine(environment, configure)
    }
}

class GRpcApplicationEngine(environment: ApplicationEngineEnvironment, val configure: GRpcApplicationEngine.Configuration.() -> Unit = {}) : BaseApplicationEngine(environment) {

    class Configuration : BaseApplicationEngine.Configuration() {
        var port: Int = 6565

        var serverConfigurer: ServerBuilder<*>.() -> Unit = {}
//        ServerBootstrap.() -> Unit = {}
    }

    private val configuration = GRpcApplicationEngine.Configuration().apply(configure)
    private var server: Server? = null

    override fun start(wait: Boolean): ApplicationEngine {
        server = ServerBuilder
                .forPort(configuration.port)
                .apply(configuration.serverConfigurer)
                .build()

        server!!.start()

        if (wait) {
            server!!.awaitTermination()
//            server.shutdown()
        }

        return this
    }

    override fun stop(gracePeriod: Long, timeout: Long, timeUnit: TimeUnit) {
        environment.monitor.raise(ApplicationStopPreparing, environment)

        server.awaitTermination(gracePeriod, )i

        if (server != null) {
            server!!.shutdownNow()
        }
    }
}