package playground

import java.time.Duration
import java.util.concurrent.Callable
import java.util.concurrent.Executors

class ARequest {
  val executor= Executors.newFixedThreadPool(10)


  fun handleRequestAny() {

    val shorterPeriod = Duration.ofMillis(1200) // ms
    val clientA = BiddingServer("a",shorterPeriod)
    val clientB = BiddingServer("shorter",shorterPeriod.plus(Duration.ofMillis(300)))
    val userEmail = "roger.rabbit@carrotmail.com"
    val userClient = UserResolverClient()
    val userCountry = userClient.findUserByEmail(userEmail).country
    val startTime = System.currentTimeMillis()
    val price  = executor.invokeAny (listOf(
      Callable { clientA.getBid(userCountry) } ,
      Callable { clientB.getBid(userCountry) }))
      .price

    println("price is + ${price}")
    println(System.currentTimeMillis()-startTime)
    //println(futureA.get().)
    //futureB.get()

  }

  fun handleRequestAll() {

    val shorterPeriod = Duration.ofMillis(1200) // ms
    val clientA = BiddingServer("a",shorterPeriod)
    val clientB = BiddingServer("shorter",shorterPeriod.plus(Duration.ofMillis(800)))
    val userClient = UserResolverClient()
    val userCountry = userClient.findUserByEmail("roger.rabbit@carrotmail.com").country
    val startTime = System.currentTimeMillis()
    val futureList  = executor.invokeAll (listOf(
      Callable { clientA.getBid(userCountry) } ,
      Callable { clientB.getBid(userCountry) }))
    println("price is + ${futureList.get(0).get().price}")
    println(System.currentTimeMillis()-startTime)
    //println(futureA.get().)
    //futureB.get()

  }
}

fun main() {
  val a = ARequest()
  a.handleRequestAny()
}
