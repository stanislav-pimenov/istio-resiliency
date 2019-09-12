package wiremock

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class WireMockThreadPoolTest extends Simulation {

  val httpProtocol = http.baseUrl("http://192.168.99.100:31380")

  val scn1 = scenario("Wiremock")
    .exec(http("thread pool test")
      .get("/gimme/200")
      .header("Host","wiremock.gateway")      
      .check(status.is(200))
    )

  //val allScenarios = scn1.exec(scn2)

  var baseRps = 50

  setUp(scn1.inject(constantUsersPerSec(500) during (8 minutes))
    .protocols(httpProtocol)
    .throttle(
      reachRps(baseRps) in (10 seconds),
      holdFor(2 minute)
//      jumpToRps(baseRps * 4),
//      holdFor(1 minute),
//      jumpToRps(baseRps * 6),
//      holdFor(1 minute),
//      jumpToRps(baseRps * 10),
//      holdFor(1 minute)
    )
  )
}