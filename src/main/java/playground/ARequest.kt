package playground

import java.time.Duration
import java.util.concurrent.Callable
import java.util.concurrent.Executors

class ARequest {
  val executor= Executors.newFixedThreadPool(10)


  fun handleRequestAny() {

    val shorterPeriod = Duration.ofMillis(1200) // ms
    val clientA = WeatherServer("a",shorterPeriod)
    val clientB = WeatherServer("shorter",shorterPeriod.plus(Duration.ofMillis(300)))
    val userEmail = "roger.rabbit@carrotmail.com"
    val userCity = UserResolverClient().findUserByEmail(userEmail).city
    val startTime = System.currentTimeMillis()
    val temperature  = executor.invokeAny (listOf(
      Callable { clientA.readWeather(userCity) } ,
      Callable { clientB.readWeather(userCity) })).temperature

    println("temperature is + ${temperature}")
    println(System.currentTimeMillis()-startTime)
    //println(futureA.get().)
    //futureB.get()

  }

  fun handleRequestAll() {

    val shorterPeriod = Duration.ofMillis(1200) // ms
    val clientA = WeatherServer("a",shorterPeriod)
    val clientB = WeatherServer("shorter",shorterPeriod.plus(Duration.ofMillis(800)))
    val userCity = UserResolverClient().findUserByEmail("roger.rabbit@carrotmail.com").city
    val startTime = System.currentTimeMillis()
    val futureList  = executor.invokeAll (listOf(
      Callable { clientA.readWeather(userCity) } ,
      Callable { clientB.readWeather(userCity) }))
    println("temperature is + ${futureList.get(0).get().temperature}")
    println(System.currentTimeMillis()-startTime)
    //println(futureA.get().)
    //futureB.get()

  }
}

fun main() {
  val a = ARequest()
  a.handleRequestAny()
}
