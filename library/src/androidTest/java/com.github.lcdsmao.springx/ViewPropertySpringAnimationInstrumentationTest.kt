package com.github.lcdsmao.springx

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ViewPropertySpringAnimationInstrumentationTest : ViewPropertySpringAnimatorTest() {
  override fun runOnMainThread(block: () -> Unit) = InstrumentationUiTest.runOnMainThread(block)
}
