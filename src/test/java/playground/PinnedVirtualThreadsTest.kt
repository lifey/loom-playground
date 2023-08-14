package playground

import org.junit.jupiter.api.Test
// try with -Djdk.virtualThreadScheduler.maxPoolSize=1 it will show the thread pinning
class PinnedVirtualThreadsTest {
  @Test
  fun pinnedVirtualThreads() {
    println(System.getProperty("jdk.virtualThreadScheduler.maxPoolSize"))
    val multiplier = 1L

    val startTime = System.currentTimeMillis()

    for (i in 1..2) {
      val t = Thread.ofVirtual().name("virtual-", i.toLong()).unstarted(

        {
          while (true) {
            recall(10, {
              synchronized(Object()) {
                Thread.sleep(2000 * multiplier)
                println("${System.currentTimeMillis() - startTime} thread $i ${Thread.currentThread()}")
              }
            })
          }
        }
      )
      t.start()
    }



    Thread.sleep(100000)

  }
}

