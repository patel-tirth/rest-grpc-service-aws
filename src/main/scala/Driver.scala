import scala.util.Try
import org.slf4j.{Logger, LoggerFactory}
import com.typesafe.config.ConfigFactory

import scala.io.StdIn
import scala.util.{Failure, Success, Try}
import java.util.Date
import GrpcType.{ClientGrpc, ServerGrpc}
import RestType.RestApi

import java.text.SimpleDateFormat
import scala.concurrent.{ExecutionContext, Future}
object Driver {
  val format = new java.text.SimpleDateFormat("HH:mm:ss.SSS")

  def checkDateFormat(str: String): Boolean =
    Try[Date](format.parse(str)) match {
      case Success(testDate) => true
      case Failure(exception) => false
    }

  def main(args: Array[String]): Unit = {
    val logger = LoggerFactory.getLogger(this.getClass)

    if(args.length == 1){
      val clientType = args(0)
      if(clientType== "grpc"){
        println("Please enter a time in format: HH:mm:ss.SSS")
        val time = StdIn.readLine()
        println("Please enter a time delta range")
        val delta = StdIn.readLine()


        val server = new ServerGrpc(ExecutionContext.global)
        server.start()
        val client = new ClientGrpc(time,delta)
        logger.info("Trying to start client..")
        client.searchLogs()
//        client.shutdown()

      } else if (clientType == "rest"){
        println("Please enter a time in format: HH:mm:ss.SSS")
        val time = StdIn.readLine()
        println("Please enter a time delta range")
        val delta = StdIn.readLine()
        val rest  = new RestApi(time, delta)
        rest.invokeLambda()
      }
    }
  }
}
