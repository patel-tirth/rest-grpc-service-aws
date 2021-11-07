import com.typesafe.config.{Config, ConfigFactory}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.PrivateMethodTester
import org.scalatest.matchers.should.Matchers

class scalaTest extends AnyFlatSpec  with Matchers with PrivateMethodTester {
  val config: Config = ConfigFactory.load()
  behavior of "config params"
  it should "check the storage bucket in" in {

    val storage = config.getString("configuration.storage")
    storage shouldBe "outputlogs-cs-441"

  }

  it should "check the rest api url" in {

    val url = config.getString("configuration.apigatewayrest")
    url shouldBe "https://dpyy9anwh4.execute-api.us-east-1.amazonaws.com/test/rest"

  }

  it should "check the grpc api url" in {

    val url = config.getString("configuration.apigatewaygrpc")
    url shouldBe "https://dpyy9anwh4.execute-api.us-east-1.amazonaws.com/test/grpc"

  }

  it should "check the local host" in {

    val host = config.getString("configuration.host")
    host shouldBe "127.0.0.1"

  }

  it should "check the pattern" in {

    val pattern = config.getString("configuration.pattern")
    pattern shouldBe "([a-c][e-g][0-3]|[A-Z][5-9][f-w]){5,15}"

  }
}
