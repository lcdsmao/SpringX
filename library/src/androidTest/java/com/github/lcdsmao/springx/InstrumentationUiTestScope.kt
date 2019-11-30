package com.github.lcdsmao.springx

import androidx.test.platform.app.InstrumentationRegistry

@Suppress("unused")
class InstrumentationUiTestScope : UiTestScope {

  override fun runOnMainSync(block: () -> Unit) {
    InstrumentationRegistry.getInstrumentation().runOnMainSync {
      block.invoke()
    }
  }

  override fun waitForIdleSync() {
    InstrumentationRegistry.getInstrumentation().waitForIdleSync()
  }

  class Runner : UiTestScope.Runner {

    override fun runUiTest(block: () -> Unit) {
      block.invoke()
    }
  }
}
