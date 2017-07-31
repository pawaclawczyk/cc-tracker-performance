import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import scala.concurrent.duration._
import scala.io.StdIn

class ExampleSimulation extends Simulation {

  println("Base URL:")
  val baseUrl: String = StdIn.readLine() match {
    case "" => "http://127.0.0.1:9000"
    case url => url
  }

  println("Duration in seconds:")
  val duration: Int = StdIn.readLine() match {
    case "" => 10
    case s => s.toInt
  }

  println("Users per second:")
  val usersPerSecond: Int = StdIn.readLine() match {
    case "" => 10
    case s => s.toInt
  }

  val httpConf: HttpProtocolBuilder = http
      .baseURL(baseUrl)
      .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
      .doNotTrackHeader("1")
      .acceptLanguageHeader("en-US,en;q=0.5")
      .acceptEncodingHeader("gzip, deflate")
      .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")

  val scn: ScenarioBuilder = scenario("Basic")
      .exec(http("request").get("/pixel.gif"))

  setUp(scn.inject(
    constantUsersPerSec(usersPerSecond) during(duration seconds)
  ))
    .protocols(httpConf)
    .maxDuration(duration + 5 seconds)
    .pauses(disabledPauses)
}
