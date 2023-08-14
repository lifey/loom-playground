package playground

import org.junit.jupiter.api.Test


class StackTraceTest {
  private fun inner() {
    val e = Exception()
    println("hi")
    throw e
  }

  private fun inner_runner() {
    recall(
      3,
      ::inner
    )
  }

  private fun outer() {

    Thread.ofVirtual().name("stackfragmets-inner").start(::inner_runner).join()
  }

  private fun outer_runner() {
    recall(3, ::outer)
  }

  @Test
  fun testFragments() {

    recall(2) {
      Thread.ofVirtual().name("stackfragmets").start(
        ::outer_runner
      ).join()
    }
    println("joined")
    Thread.sleep(5000)
  }

}
