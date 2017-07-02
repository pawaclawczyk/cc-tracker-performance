import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class ExampleSimulation extends Simulation {
  val httpConf = http
      .baseURL("http://computer-database.gatling.io")
      .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
      .doNotTrackHeader("1")
      .acceptLanguageHeader("en-US,en;q=0.5")
      .acceptEncodingHeader("gzip, deflate")
      .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")

  val scn = scenario("Basic")
      .exec(http("request").get("/"))
      .pause(5)

  setUp(scn.inject(atOnceUsers(1)))
      .protocols(httpConf)
}
