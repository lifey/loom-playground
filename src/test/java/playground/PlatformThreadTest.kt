package playground

import org.junit.jupiter.api.Test
import playground.constructs.RuntimeInteraction
import java.nio.ByteBuffer
import java.util.concurrent.atomic.AtomicInteger

class PlatformThreadTest {
  @Test
  fun createVirtualThreads() {
    val multiplier = 1000L
    println(Runtime.getRuntime().availableProcessors())

    val started = AtomicInteger(0)
    val done = AtomicInteger(0)
    val total = 1000
    println("summary:--------------------------------------------------------")
    RuntimeInteraction.rumNMT(RuntimeInteraction.NMTCommands.SUMMARY)
    println("baseline:--------------------------------------------------------")
    RuntimeInteraction.rumNMT(RuntimeInteraction.NMTCommands.BASELINE)
    val startTime = System.currentTimeMillis()
    try {

      for (i in 1..total) {
        val t = Thread.ofPlatform().name("virtual-", i.toLong()).unstarted(

          {
            recall(10, {
              if (i % 10000 == 0)
                println("hello sample $i" + Thread.currentThread())
              started.incrementAndGet()
              Thread.sleep(2000 * multiplier)
              done.incrementAndGet()
            })
          }
        )
        t.start()
      }
    } catch (t: Throwable) {
      println("error after ${started.get()} threads and  ${System.currentTimeMillis() - startTime}ms")
    }
    val buffer: ByteBuffer = ByteBuffer.allocateDirect(1024*1024*304)
    val buffer2: ByteBuffer = ByteBuffer.allocateDirect(1024*1024*113)
    println("diff:--------------------------------------------------------")
    RuntimeInteraction.rumNMT(RuntimeInteraction.NMTCommands.SUMMARY_DIFF)
    println("summary:--------------------------------------------------------")
    RuntimeInteraction.rumNMT(RuntimeInteraction.NMTCommands.SUMMARY)
    println("detail:--------------------------------------------------------")
    RuntimeInteraction.rumNMT(RuntimeInteraction.NMTCommands.DETAIL)
    for (i in 1..80) {
      Thread.sleep(1000 * multiplier)
      println("gap ${System.currentTimeMillis() - startTime} started ${started.get()} ${done.get()}")
      if (done.get() == total) break
    }

    // val scope = ContinuationScope();


  }


}

