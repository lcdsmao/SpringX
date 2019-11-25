package com.github.lcdsmao.springx

import org.robolectric.annotation.LooperMode
import org.robolectric.shadows.ShadowLooper

@Suppress("unused")
class UnitUiTest : UiTest {
  override fun runOnMainThread(block: () -> Unit) {
    ShadowLooper.assertLooperMode(LooperMode.Mode.PAUSED)
    block.invoke()
    ShadowLooper.idleMainLooper()
  }
}
