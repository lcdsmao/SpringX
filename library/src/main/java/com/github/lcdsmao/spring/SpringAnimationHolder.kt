package com.github.lcdsmao.spring

import androidx.dynamicanimation.animation.SpringAnimation

internal data class SpringAnimationHolder(
  private val animation: SpringAnimation,
  private val config: ImmutableSpringAnimationConfig
) {

  fun start() {
    config.applyTo(animation)
    animation.start()
  }

  fun cancel() {
    animation.cancel()
  }
}
