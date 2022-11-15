import jdk.incubator.concurrent.StructuredTaskScope
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.Duration
import java.time.Duration.*
import java.util.concurrent.Future
// --enable-preview --add-exports java.base/jdk.internal.vm=ALL-UNNAMED --add-modules jdk.incubator.concurrent
data class Weather(val agency: String, val weather: String)

class WeatherScope : StructuredTaskScope<Weather>() {
  override fun handleComplete(future: Future<Weather>?) {
    println(future!!.state())


  }
}

class StructuredTest {
  fun readWeather(agency: String, slp: Duration): Weather {
    Thread.sleep(slp)
    return Weather(agency, "33")
  }

  @Test
  fun structured() {
    val scope = StructuredTaskScope.ShutdownOnSuccess<Weather>()
    val shorterPeriod = ofMillis(1200) // ms
    scope.use {
      val futureA = scope.fork { readWeather("a", shorterPeriod.plus(ofMillis(300))) }
      val futureB = scope.fork { readWeather("shorter", shorterPeriod) }
      val resultFuture = scope.join();
      assertEquals("shorter",resultFuture.result().agency)
    }
  }
}
