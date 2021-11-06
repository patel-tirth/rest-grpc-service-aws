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
  val port = "50051"
  val channel = ManagedChannelBuilder.forAddress(host, port.toInt).usePlaintext().build
  val blockingStub = LogFinderGrpc.blockingStub(channel)
    val logger: Logger = LoggerFactory.getLogger(this.getClass)

  def shutdown(): Unit = {
    channel.shutdown.awaitTermination(5, TimeUnit.SECONDS)
  }
  def searchLogs(): Unit = {
    logger.info("Client connected")
    val request = Request(time, delta, storage, "log")

    val response = blockingStub.checkLogs(request)

    if (response.output.toInt == -1){
      println("Log messages do not exists for given time and delta")
    } else if(response.output.toInt == 1){
      println("Log messages exists in the log file!")
    }
  }
}