package playground

import playground.recall
import org.junit.jupiter.api.Test
import java.util.concurrent.atomic.AtomicInteger

class VirtualThreadTest {
  @Test
  fun createVirtualThreads() {

    println(Runtime.getRuntime().availableProcessors())

    val started = AtomicInteger(0);
    val done = AtomicInteger(0);
    val total = 100000

    val Time = System.currentTimeMillis()
    try {

      for (i in 1..100000) {
        val t = Thread.ofVirtual().name("virtual-", i.toLong()).unstarted(

          {
            recall(10, {
              if (i % 10000 == 0)
                println("hello sample $i" + Thread.currentThread())
              started.incrementAndGet();
              Thread.sleep(2000);
              done.incrementAndGet()
            })
          }
        )
        t.start()
      }
    } catch (t: Throwable) {
      println("error after ${started.get()} threads and  ${System.currentTimeMillis() - Time}ms")
    }


    for (i in 1..80) {
      Thread.sleep(1000)
      println("gap ${System.currentTimeMillis() - Time} started ${started.get()} ${done.get()}")
      if (done.get() == total) break
    }

    // val scope = ContinuationScope();


  }
}

