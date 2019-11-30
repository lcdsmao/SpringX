package com.github.lcdsmao.springx

interface UiTest {
  fun runOnMainThread(block: () -> Unit)
}

fun runOnMainThread(block: () -> Unit) {
  uiTestDelegate.runOnMainThread(block)
}

private val uiTestDelegate = getUiTestDelegate()

private fun getUiTestDelegate(): UiTest {
  val className = if (System.getProperty("java.runtime.name")!!.toLowerCase().contains("android")) {
    "com.github.lcdsmao.springx.InstrumentationUiTest"
  } else {
    "com.github.lcdsmao.springx.UnitUiTest"
  }
  return Class.forName(className).newInstance() as UiTest
}
