package com.github.lcdsmao.springx

import org.robolectric.shadows.ShadowLooper

object UnitUiTest : UiTest {
  override fun runOnMainThread(block: () -> Unit) {
    block.invoke()
    ShadowLooper.idleMainLooper()
  }
}
