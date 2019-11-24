package com.github.lcdsmao.springx

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import org.robolectric.annotation.LooperMode

@RunWith(AndroidJUnit4::class)
@LooperMode(LooperMode.Mode.PAUSED)
class ViewPropertySpringAnimationUnitTest : ViewPropertySpringAnimatorTest() {
  override fun runOnMainThread(block: () -> Unit) = UnitUiTest.runOnMainThread(block)
}
