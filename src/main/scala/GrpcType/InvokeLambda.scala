package GrpcType
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.Uri.Query
import akka.http.scaladsl.model.{HttpRequest, HttpResponse, Uri}
import scala.concurrent.duration.DurationInt
import scala.language.postfixOps
import scala.concurrent.{Await, ExecutionContext, Future}
import com.pateltirth.protos.log.{Request, Response}
import com.typesafe.config.{Config, ConfigFactory}
import org.slf4j.{Logger, LoggerFactory}
class InvokeLambda(val time:String , val delta: String, val storage: String, val keyname: String, req: Request) {
  // https://doc.akka.io/docs/akka-grpc/current/server/walkthrough.html#implementing-the-service
  // https://doc.akka.io/docs/akka-http/current/client-side/request-and-response.html
  // https://doc.akka.io/docs/akka-http/current/client-side/request-level.html

  // Akka boot up code
  implicit val sys: ActorSystem = ActorSystem()
  implicit val ec: ExecutionContext = sys.dispatcher

  val logger: Logger = LoggerFactory.getLogger(this.getClass)
  val config: Config = ConfigFactory.load()
  def invokeLam():String = {

    val apiGatewayGrpc: String = config.getString("configuration.apigatewaygrpc")
    val url = Uri(apiGatewayGrpc)
//    pass the user provided time and delta as query parameters to api
    val uriWithQuery = url.withQuery(Query("time" ->time, "delta"->delta))
    // get request with time and delta as parameters
    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = uriWithQuery))
  // https://stackoverflow.com/questions/45646602/whats-the-best-practice-to-send-a-simply-http-request-and-get-its-response-with
    val responseBody : Future[String] =
      responseFuture
        .map(_.entity)
        .flatMap(_.toStrict(10 seconds))
        .map(_.data.utf8String)
    logger.info("waiting for results")
    Await.result(responseBody, 10 seconds)

  }

}
