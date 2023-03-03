@file: JvmName("Main")

import de.ev.authorization.api.proto.AuthorizationServiceGrpc
import de.ev.authorization.api.proto.CrudResponse
import de.ev.authorization.api.proto.UpdateRequest
import io.grpc.ServerBuilder
import io.grpc.stub.StreamObserver
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty


fun main(args: Array<String>) {
//    embeddedServer(Netty, 8080, configure = {
//
//    }) {
//        routing {
//            get {
//                call.respondText("asdasdsd", ContentType.Text.Plain)
//            }
//        }
//    }.start(wait = true)
    embeddedServer(GRpc, configure = {
        port = 8888
        serverConfigurer = {
            addService(X())
        }
    }) {
        routing {

        }
    }.start()


//    val server = ServerBuilder
//            .forPort(8080)
//            .addService(X()).build().start().awaitTermination()
}

class X : AuthorizationServiceGrpc.AuthorizationServiceImplBase() {
    override fun updateEntity(responseObserver: StreamObserver<CrudResponse>?): StreamObserver<UpdateRequest> {
        return super.updateEntity(responseObserver)
    }
}
