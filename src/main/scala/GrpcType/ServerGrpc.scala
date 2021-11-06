package GrpcType
import com.pateltirth.protos.log.LogFinderGrpc
import com.typesafe.config.ConfigFactory
import com.pateltirth.protos.log.{Request,Response}
import scala.concurrent.{ExecutionContext, Future}
import io.grpc.{Server, ServerBuilder}
import GrpcType.InvokeLambda
// code outline:  https://github.com/xuwei-k/grpc-scala-sample/blob/master/grpc-scala/src/main/scala/io/grpc/examples/helloworld/HelloWorldServer.scala
class ServerGrpc (executionContext: ExecutionContext){ self =>
  private[this] var server: Server = null

  def stop(): Any =  if (server != null) server.shutdown()


  val port = "50051"
  private def start(): Unit = {
    server = ServerBuilder.forPort(port.toInt).addService(LogFinderGrpc.bindService(new LogFinderImpl, executionContext)).build.start
//    HelloWorldServer.logger.info("Server started, listening on " + HelloWorldServer.port)
    sys.addShutdownHook {
      System.err.println("*** shutting down gRPC server since JVM is shutting down")
      self.stop()
      System.err.println("*** server shut down")
    }
  }

  private class LogFinderImpl extends LogFinderGrpc.LogFinder {
    override def checkLogs(req: Request):Future[Response] = {

      val invokeLambda = new InvokeLambda(req.time, req.delta, req.storage, req.keyname)
      val reply = Response(invokeLambda.invoke())
      Future.successful(reply)
    }
  }
}