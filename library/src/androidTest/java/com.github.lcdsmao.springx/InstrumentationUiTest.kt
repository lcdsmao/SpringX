package com.github.lcdsmao.springx

import androidx.test.platform.app.InstrumentationRegistry

@Suppress("unused")
class InstrumentationUiTest : UiTest {
  override fun runOnMainThread(block: () -> Unit) {
    InstrumentationRegistry.getInstrumentation().runOnMainSync { block.invoke() }
  }
}
