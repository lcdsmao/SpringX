package com.github.lcdsmao.spring

import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringForce

class SpringAnimationConfig(var finalValue: Float) {

  companion object {
    val NOT_SET = Float.MAX_VALUE
  }

  internal var startValueIsSet: Boolean = false
    private set
  var startValue: Float = NOT_SET
    set(value) {
      startValueIsSet = true
      field = value
    }

  internal var startVelocityIsSet: Boolean = false
    private set
  var startVelocity: Float = NOT_SET
    set(value) {
      startVelocityIsSet = true
      field = value
    }

  var maxValue: Float = Float.MAX_VALUE
  var minValue: Float = -maxValue

  internal var dampingRatioIsSet: Boolean = false
    private set
  var dampingRatio: Float = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
    set(value) {
      dampingRatioIsSet = true
      field = value
    }

  internal var stiffnessIsSet: Boolean = false
    private set
  var stiffness: Float = SpringForce.STIFFNESS_MEDIUM
    set(value) {
      stiffnessIsSet = true
      field = value
    }

  internal var updateListener: DynamicAnimation.OnAnimationUpdateListener? = null
    private set
  internal var endListener: DynamicAnimation.OnAnimationEndListener? = null
    private set

  fun doOnUpdate(
    function: (animation: DynamicAnimation<*>, value: Float, velocity: Float) -> Unit
  ) {
    updateListener = DynamicAnimation.OnAnimationUpdateListener(function)
  }

  fun doOnEnd(
    function: (animation: DynamicAnimation<*>, canceled: Boolean, value: Float, velocity: Float) -> Unit
  ) {
    endListener = DynamicAnimation.OnAnimationEndListener(function)
  }
}
