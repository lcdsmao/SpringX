package com.github.lcdsmao.springx

import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce

class SpringAnimationConfig internal constructor(private var finalValue: Float) {

  companion object {
    val NOT_SET = Float.MAX_VALUE
  }

  private var startValueIsSet: Boolean = false
  var startValue: Float = NOT_SET
    set(value) {
      startValueIsSet = true
      field = value
    }

  private var startVelocityIsSet: Boolean = false
  var startVelocity: Float = NOT_SET
    set(value) {
      startVelocityIsSet = true
      field = value
    }

  var maxValue: Float = Float.MAX_VALUE
  var minValue: Float = -maxValue

  private var dampingRatioIsSet: Boolean = false
  internal var defaultDampingRatio: Float = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
  var dampingRatio: Float = defaultDampingRatio
    get() {
      return if (dampingRatioIsSet) {
        field
      } else {
        defaultDampingRatio
      }
    }
    set(value) {
      dampingRatioIsSet = true
      field = value
    }

  private var stiffnessIsSet: Boolean = false
  internal var defaultStiffness: Float = SpringForce.STIFFNESS_MEDIUM
  var stiffness: Float = defaultStiffness
    get() {
      return if (stiffnessIsSet) {
        field
      } else {
        defaultStiffness
      }
    }
    set(value) {
      stiffnessIsSet = true
      field = value
    }

  private var updateListener: DynamicAnimation.OnAnimationUpdateListener? = null
  private var endListener: DynamicAnimation.OnAnimationEndListener? = null

  var skipToEndIfRunning: Boolean = false
  var cancelIfRunning: Boolean = false

  fun onUpdate(
    function: (spring: SpringAnimation, value: Float, velocity: Float) -> Unit
  ) {
    updateListener = DynamicAnimation.OnAnimationUpdateListener { animation, value, velocity ->
      val spring = animation as SpringAnimation
      function(spring, value, velocity)
    }
  }

  fun onEnd(
    function: (spring: SpringAnimation, canceled: Boolean, value: Float, velocity: Float) -> Unit
  ) {
    endListener = DynamicAnimation.OnAnimationEndListener { animation, canceled, value, velocity ->
      val spring = animation as SpringAnimation
      function(spring, canceled, value, velocity)
    }
  }

  internal fun applyTo(animation: SpringAnimation) {
    when {
      skipToEndIfRunning -> animation.skipToEnd()
      cancelIfRunning -> animation.cancel()
    }
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
