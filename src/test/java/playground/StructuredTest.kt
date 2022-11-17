package playground

import jdk.incubator.concurrent.StructuredTaskScope
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.Duration.*

// --enable-preview --add-modules jdk.incubator.concurrent --add-exports java.base/jdk.internal.vm=ALL-UNNAMED



class StructuredTest {

  @Test
  fun structured() {
    Thread.ofVirtual().name("hola-playground").start {
      val multiplier = 1L
      val shorterPeriod = ofMillis(1200 * multiplier) // ms
      val serverA = WeatherServer("shorter-service", shorterPeriod)
      val serverB = WeatherServer("longer-service", shorterPeriod.plus(ofMillis(300 * multiplier)))
      val scope = StructuredTaskScope.ShutdownOnSuccess<WeatherResponse>()
      val city = UserResolverClient().findUserByEmail("roger.rabbit@carrotmail.com").city
      scope.use {
        scope.fork { serverA.readWeather(city) }
        scope.fork { serverB.readWeather(city) }
        val resultFuture = scope.join()
        assertEquals("shorter-service", resultFuture.result().agency)
      }
    }.join()
  }
}
