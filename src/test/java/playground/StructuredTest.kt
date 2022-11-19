package playground

import jdk.incubator.concurrent.StructuredTaskScope
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.Duration.*
import java.time.Instant

// --enable-preview --add-modules jdk.incubator.concurrent --add-exports java.base/jdk.internal.vm=ALL-UNNAMED


class StructuredTest {

  @Test
  fun structuredShutdownOnSuccess() {
    val multiplier = 1L
    val shorterPeriod = ofMillis(1200 * multiplier) // ms
    val serverA = BiddingServer("shorter-service", shorterPeriod)
    val serverB = BiddingServer("longer-service", shorterPeriod.plus(ofMillis(300 * multiplier)))

    val userRClient = UserResolverClient()
    val country = userRClient.findUserByEmail("roger.rabbit@carrotmail.com").country

    val scope = StructuredTaskScope.ShutdownOnSuccess<BiddingResponse>()
    scope.use {
      scope.fork { serverA.getBid(country) }
      scope.fork { serverB.getBid(country) }
      val firstSuccessfulFuture = scope.joinUntil(Instant.now().plusMillis(3000))
      assertEquals("shorter-service", firstSuccessfulFuture.result().agency)
    }
  }

  @Test
  fun structuredShutdownOnFailure() {
    val multiplier = 1L
    val shorterPeriod = ofMillis(1200 * multiplier) // ms
    val serverA = BiddingServer("shorter-service", shorterPeriod)
    val serverB = BiddingServer("longer-service", shorterPeriod.plus(ofMillis(300 * multiplier)))

    val country = UserResolverClient().findUserByEmail("roger.rabbit@carrotmail.com").country

    val scope = StructuredTaskScope.ShutdownOnFailure()
    scope.use {
      val resultA = scope.fork { serverA.getBid(country) }
      val resultB = scope.fork { serverB.getBid(country) }
      scope.join().throwIfFailed()
      val min = Math.min(resultA.get().price,resultB.get().price)
      assertEquals(33, (resultA.get().price + resultB.get().price) / 2)
    }
  }
}
