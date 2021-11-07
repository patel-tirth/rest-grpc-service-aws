package GrpcType
// code outline : https://github.com/xuwei-k/grpc-scala-sample/blob/master/grpc-scala/src/main/scala/io/grpc/examples/helloworld/HelloWorldClient.scala
import com.typesafe.config.{Config, ConfigFactory}
import io.grpc.{ManagedChannel, ManagedChannelBuilder}
import com.pateltirth.protos.log.{LogFinderGrpc, Request, Response}
import org.slf4j.{Logger, LoggerFactory}

import java.util.concurrent.TimeUnit
  class ClientGrpc (val time:String, val delta:String){
  val config: Config = ConfigFactory.load()
  val storage: String = config.getString("configuration.storage")
  val host: String = config.getString("configuration.host")
  val port = "3000"
  val channel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build
  val blockingStub = LogFinderGrpc.blockingStub(channel)
    val logger: Logger = LoggerFactory.getLogger(this.getClass)

  def shutdown(): Unit = {
    channel.shutdown.awaitTermination(5, TimeUnit.SECONDS)
  }

  def searchLogs(): Unit = {
    logger.info("Client connected")
    val request = Request(time, delta)

    val response = blockingStub.checkLogs(request)

    logger.info(response.output)
  }
}
