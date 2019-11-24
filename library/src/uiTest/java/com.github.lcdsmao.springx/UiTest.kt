package com.github.lcdsmao.springx

interface UiTest {
  fun runOnMainThread(block: () -> Unit)
}
