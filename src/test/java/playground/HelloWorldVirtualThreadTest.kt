package playground

import org.junit.jupiter.api.Test

class HelloWorldVirtualThreadTest {
  @Test
  fun helloWorld() {

    Thread.ofVirtual()
      .name("virtual-", 1)
      .start {
        Thread.sleep(2000)
        println("Hello World:"+ Thread.currentThread())
      }.join()
  }


}

