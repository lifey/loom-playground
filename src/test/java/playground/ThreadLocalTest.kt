package playground

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.util.concurrent.atomic.AtomicReference

class ThreadLocalTest {


  @Test
  fun testVirtualThreadLocal() {
    val tl= ThreadLocal<String>()
    val s = "someString"
    tl.set(s)
    val extracted = AtomicReference<String>()
    Thread.ofVirtual().name("virtual-thread").start {
      extracted.set(tl.get())
    }.join()
    assertNull(extracted.get())
  }

  @Test
  fun testInheriableThreadLocal() {
    val tl= InheritableThreadLocal<String>()
    val s = "someString"
    tl.set(s)
    val extracted = AtomicReference<String>()
    Thread.ofVirtual().name("virtual-thread").start {
      extracted.set(tl.get())
    }.join()
    assertEquals(extracted.get(),s)
  }

  @Test
  fun testInheriableThreadLocalNoInterit() {
    val tl= InheritableThreadLocal<String>()
    val s = "someString"
    tl.set(s)
    val extracted = AtomicReference<String>()
    Thread.ofVirtual().name("virtual-thread").inheritInheritableThreadLocals(false).start {
      extracted.set(tl.get())
    }.join()
    assertNull(extracted.get())
  }


  @Test
  fun testPlatformThreadLocal() {
    val tl= ThreadLocal<String>()
    val s = "someString"
    tl.set(s)
    val extracted = AtomicReference<String>()
    Thread.ofPlatform().name("virtual-thread").start {
      extracted.set(tl.get())
    }.join()
    assertNull(extracted.get())
  }


  @Test
  fun testPlatformInheriableThreadLocal() {
    val tl= InheritableThreadLocal<String>()
    val s = "someString"
    tl.set(s)
    val extracted = AtomicReference<String>()
    Thread.ofPlatform().name("virtual-thread").start {
      extracted.set(tl.get())
    }.join()
    assertEquals(extracted.get(),s)
  }

  @Test
  fun testInheriablePlatformThreadLocalNoInterit() {
    val tl= InheritableThreadLocal<String>()
    val s = "someString"
    tl.set(s)
    val extracted = AtomicReference<String>()
    Thread.ofPlatform().name("virtual-thread").inheritInheritableThreadLocals(false).start {
      extracted.set(tl.get())
    }.join()
    assertNull(extracted.get())
  }

}
