package GrpcType
import akka.actor.ActorSystem
import akka.http.scaladsl.model.{ HttpRequest, HttpResponse }
import akka.http.scaladsl.Http
import scala.concurrent.{Await, ExecutionContext, Future}
import scalaj.http.Http
import com.pateltirth.protos.log.Request
class InvokeLambda(val time:String , val delta: String, val storage: String, val keyname: String) {
  // https://doc.akka.io/docs/akka-grpc/current/server/walkthrough.html#implementing-the-service
  // Akka boot up code
  implicit val sys: ActorSystem = ActorSystem()
  implicit val ec: ExecutionContext = sys.dispatcher
  def invoke() ={
    val url = "api gateway url here"


//    val request = Http(url)
//      .headers(Map(
//        "Content-Type" -> "application/grpc+proto",
//        "Accept" -> "application/grpc+proto"
//      ))
//
    // using akka

  }
}
