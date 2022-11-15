package playground

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger


class ExecutorTest {
  @Test
  fun vthreadExecutorTeset() {
    println(Runtime.getRuntime().availableProcessors())

    val started = AtomicInteger(0);
    val done = AtomicInteger(0);


    val start = System.currentTimeMillis()
    val executor = Executors.newVirtualThreadPerTaskExecutor()
    val numTasks = 100000
    executor.use {

      try {
        for (i in 1..numTasks) {
          executor.submit {
            recall(100) {
              if (i % 10000 == 0)
                println("hello sample $i" + Thread.currentThread())
              started.incrementAndGet();
              Thread.sleep(2000); done.incrementAndGet()
            }
          }
        }
      } catch (t: Throwable) {
        println("error after ${started.get()} threads and  ${System.currentTimeMillis() - start}ms")
      }
    }


    for (i in 1..80) {
      Thread.sleep(100)
      println("gap ${System.currentTimeMillis() - start} started ${started.get()} ${done.get()}")
      if (numTasks == done.get()  ) break;
    }
    assertEquals(started.get(),done.get())


  }
}
