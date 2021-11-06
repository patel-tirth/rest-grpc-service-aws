package RestType

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.Uri.Query
import akka.http.scaladsl.model.{HttpRequest, HttpResponse, Uri}
import com.typesafe.config.{Config, ConfigFactory}
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.language.postfixOps

class RestApi(val time: String, val delta: String){
  val logger: Logger = LoggerFactory.getLogger(this.getClass)
  val config: Config = ConfigFactory.load()
  // using akka http similarly to grpc
  implicit val sys: ActorSystem = ActorSystem()
  implicit val ec: ExecutionContext = sys.dispatcher

  def invokeLambda(): Unit = {
    val apiGatewayGrpc: String = config.getString("configuration.apigatewayrest")
    val pattern : String = config.getString("configuration.pattern")
    val url = Uri(apiGatewayGrpc)
    //    pass the user provided time and delta as query parameters to api
    val uriWithQuery = url.withQuery(Query("time" ->time, "delta"->delta,"pattern"->pattern))
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

    logger.info(Await.result(responseBody, 10 seconds))

  }
}
