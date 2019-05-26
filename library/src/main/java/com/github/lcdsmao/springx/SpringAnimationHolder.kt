package com.github.lcdsmao.springx

import androidx.dynamicanimation.animation.SpringAnimation

internal data class SpringAnimationHolder(
  private val animation: SpringAnimation,
  private val config: SpringAnimationConfig
) {

  fun start() {
    config.applyTo(animation)
    animation.start()
  }
}
