package playground

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger


class ExecutorTest {
  @Test
  fun vthreadExecutorOneTaskTest() {
    val multiplier = 100L

    val executor = Executors.newVirtualThreadPerTaskExecutor()
    val numTasks = 3
    executor.use {

        for (i in 1..numTasks) {
          executor.submit {

              Thread.sleep(2000*multiplier)
          }
        }
    }
  }

  @Test
  fun vthreadExecutorTest() {
    println(Runtime.getRuntime().availableProcessors())
    val multiplier = 100L

    val started = AtomicInteger(0)
    val done = AtomicInteger(0)


    val start = System.currentTimeMillis()
    val executor = Executors.newVirtualThreadPerTaskExecutor()
    val numTasks = 100_000
    executor.use {

      try {
        for (i in 1..numTasks) {
          executor.submit {
            recall(10) {
              started.incrementAndGet()

              Thread.sleep(2000*multiplier)
              done.incrementAndGet()
            }
          }
        }
      } catch (t: Throwable) {
        println("error after ${started.get()} threads and  ${System.currentTimeMillis() - start}ms")
      }
    }
    println("duration: ${System.currentTimeMillis() - start} started: ${started.get()} done: ${done.get()}")


    assertEquals(started.get(),done.get())


  }
}
