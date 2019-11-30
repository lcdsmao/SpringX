package com.github.lcdsmao.springx

import android.os.Handler
import android.os.Looper
import org.robolectric.shadows.ShadowLooper
import java.util.concurrent.CountDownLatch
import kotlin.concurrent.thread

@Suppress("unused")
class UnitUiTestScope : UiTestScope {

  private val mainHandler = Handler(Looper.getMainLooper())

  override fun runOnMainSync(block: () -> Unit) {
    val latch = CountDownLatch(1)
    mainHandler.post {
      try {
        block.invoke()
      } finally {
        latch.countDown()
      }
    }
    latch.await()
  }

  override fun waitForIdleSync() {
    val latch = CountDownLatch(1)
    mainHandler.post {
      ShadowLooper.idleMainLooper()
      latch.countDown()
    }
    latch.await()
  }

  class Runner : UiTestScope.Runner {

    override fun runUiTest(block: () -> Unit) {
      val testThread = thread(name = "Unit UI Test Thread") {
        block.invoke()
      }
      while (testThread.isAlive) {
        ShadowLooper.idleMainLooper()
      }
    }
  }
}
