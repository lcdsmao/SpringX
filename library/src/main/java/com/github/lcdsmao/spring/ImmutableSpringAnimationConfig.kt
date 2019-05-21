package com.github.lcdsmao.spring

import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce

internal class ImmutableSpringAnimationConfig(
  private val finalValue: Float,
  private val startValue: Float,
  private val startValueIsSet: Boolean,
  private val startVelocity: Float,
  private val startVelocityIsSet: Boolean,
  private val maxValue: Float,
  private val minValue: Float,
  private val dampingRatio: Float,
  private val stiffness: Float,
  private val updateListener: DynamicAnimation.OnAnimationUpdateListener?,
  private val endListener: DynamicAnimation.OnAnimationEndListener?
) {

  companion object {
    operator fun invoke(
      config: SpringAnimationConfig,
      finalValueBias: Float,
      defaultDampingRatio: Float,
      defaultStiffness: Float
    ) = ImmutableSpringAnimationConfig(
      finalValue = config.finalValue + finalValueBias,
      startValue = config.startValue,
      startValueIsSet = config.startValueIsSet,
      startVelocity = config.startVelocity,
      startVelocityIsSet = config.startVelocityIsSet,
      maxValue = config.maxValue,
      minValue = config.minValue,
      dampingRatio = if (config.dampingRatioIsSet) config.dampingRatio else defaultDampingRatio,
      stiffness = if (config.stiffnessIsSet) config.stiffness else defaultStiffness,
      updateListener = config.updateListener,
      endListener = config.endListener
    )
  }

  fun applyTo(animation: SpringAnimation) {
    animation.setMinValue(minValue)
    animation.setMaxValue(maxValue)
    if (startValueIsSet) {
      animation.setStartValue(startValue)
    }
    if (startVelocityIsSet) {
      animation.setStartVelocity(startVelocity)
    }
    if (animation.spring == null) {
      animation.spring = SpringForce()
    }
    animation.spring.dampingRatio = dampingRatio
    animation.spring.stiffness = stiffness
    animation.spring.finalPosition = finalValue

    if (!animation.isRunning && updateListener != null) {
      animation.addUpdateListener(updateListener)
    }

    if (!animation.isRunning && endListener != null) {
      animation.addEndListener(endListener)
    }
  }
}
