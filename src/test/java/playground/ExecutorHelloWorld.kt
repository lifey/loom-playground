package playground

import org.junit.jupiter.api.Test
import java.util.concurrent.Executors


class ExecutorHelloWorld {
  @Test
  fun helloWorld() {
    val executor = Executors.newVirtualThreadPerTaskExecutor()

    executor.use {
      executor.submit {
        Thread.sleep(2000)
        println("Hello World: " + Thread.currentThread())
      }
    }
  }
}
