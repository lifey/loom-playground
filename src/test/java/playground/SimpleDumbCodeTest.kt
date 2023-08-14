package playground

import org.junit.jupiter.api.Test
import java.time.Duration

class SimpleDumbCodeTest {
  @Test
  fun simpleLinear() {
    val multiplier = 1L
    val shorterPeriod = Duration.ofMillis(1200 * multiplier) // ms
    val serverA = BiddingServer("shorter-expensive-service", shorterPeriod)
    val serverB = BiddingServer("longer-cheaper-service", shorterPeriod.plus(Duration.ofMillis(300 * multiplier)))

    val userRClient = UserResolverClient()
    val userEmail = "roger.rabbit@carrotmail.com"
    val country = userRClient.findUserByEmail(userEmail).country

    val resultA = serverA.getBid(country)
    val resultB = serverB.getBid(country)

    val lowestBid = if (resultA.price < resultB.price)
      resultA
    else
      resultB


  }
}

