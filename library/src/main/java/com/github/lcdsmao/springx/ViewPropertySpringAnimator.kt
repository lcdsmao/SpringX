package com.github.lcdsmao.springx

import android.view.View
import androidx.annotation.MainThread
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FloatPropertyCompat
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce

class ViewPropertySpringAnimator(
  private val view: View
) {

  interface AnimatorListener {
    fun onAnimationStart(animator: ViewPropertySpringAnimator) {}
    fun onAnimationCancel(animator: ViewPropertySpringAnimator) {}
    fun onAnimationEnd(animator: ViewPropertySpringAnimator) {}
  }

  private val pendingAnimations = mutableListOf<SpringAnimationHolder>()
  private val runningAnimations = mutableMapOf<FloatPropertyCompat<View>, SpringAnimation>()
  val isRunning: Boolean
    get() = runningAnimations.isNotEmpty()

  private var defaultDampingRatio: Float = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
  private var defaultStiffness: Float = SpringForce.STIFFNESS_MEDIUM
  private var animatorListener: AnimatorListener? = null

  fun defaultDampingRatio(value: Float) = apply {
    defaultDampingRatio = value
  }

  fun defaultStiffness(value: Float) = apply {
    defaultStiffness = value
  }

  fun x(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator =
    animateProperty(DynamicAnimation.X, finalValue, config)

  fun xBy(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator =
    animatePropertyBy(DynamicAnimation.X, finalValue, config)

  fun y(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator =
    animateProperty(DynamicAnimation.Y, finalValue, config)

  fun yBy(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator =
    animatePropertyBy(DynamicAnimation.Y, finalValue, config)

  fun z(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator =
    animateProperty(DynamicAnimation.Z, finalValue, config)

  fun zBy(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator =
    animatePropertyBy(DynamicAnimation.Z, finalValue, config)

  fun rotation(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator =
    animateProperty(DynamicAnimation.ROTATION, finalValue, config)

  fun rotationBy(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator =
    animatePropertyBy(DynamicAnimation.ROTATION, finalValue, config)

  fun rotationX(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator =
    animateProperty(DynamicAnimation.ROTATION_X, finalValue, config)

  fun rotationXBy(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator =
    animatePropertyBy(DynamicAnimation.ROTATION_X, finalValue, config)

  fun rotationY(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator =
    animateProperty(DynamicAnimation.ROTATION_Y, finalValue, config)

  fun rotationYBy(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator =
    animatePropertyBy(DynamicAnimation.ROTATION_Y, finalValue, config)

  fun translationX(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator =
    animateProperty(DynamicAnimation.TRANSLATION_X, finalValue, config)

  fun translationXBy(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator =
    animatePropertyBy(DynamicAnimation.TRANSLATION_X, finalValue, config)

  fun translationY(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator =
    animateProperty(DynamicAnimation.TRANSLATION_Y, finalValue, config)

  fun translationYBy(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator =
    animatePropertyBy(DynamicAnimation.TRANSLATION_Y, finalValue, config)

  fun translationZ(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator =
    animateProperty(DynamicAnimation.TRANSLATION_Z, finalValue, config)

  fun translationZBy(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator =
    animatePropertyBy(DynamicAnimation.TRANSLATION_Z, finalValue, config)

  fun scaleX(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator =
    animateProperty(DynamicAnimation.SCALE_X, finalValue, config)

  fun scaleXBy(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator =
    animatePropertyBy(DynamicAnimation.SCALE_X, finalValue, config)

  fun scaleY(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator =
    animateProperty(DynamicAnimation.SCALE_Y, finalValue, config)

  fun scaleYBy(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator =
    animatePropertyBy(DynamicAnimation.SCALE_Y, finalValue, config)

  fun alpha(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator =
    animateProperty(DynamicAnimation.ALPHA, finalValue, config)

  fun alphaBy(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator =
    animatePropertyBy(DynamicAnimation.ALPHA, finalValue, config)

  fun animateProperty(
    property: FloatPropertyCompat<View>,
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ) = apply {
    animateProperty(property, finalValue, 0f, config)
  }

  fun animatePropertyBy(
    property: FloatPropertyCompat<View>,
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ) = apply {
    animateProperty(property, finalValue, property.getValue(view), config)
  }

  private fun animateProperty(
    property: FloatPropertyCompat<View>,
    finalValue: Float,
    finalValueBias: Float,
    configBuilder: SpringAnimationConfig.() -> Unit = {}
  ) {
    var anim = runningAnimations[property]
    if (anim == null) {
      anim = SpringAnimation(view, property)
      anim.createEndListener(property)
      runningAnimations[property] = anim
    }
    val config = SpringAnimationConfig(finalValue).apply(configBuilder)
    config.defaultDampingRatio = defaultDampingRatio
    config.defaultStiffness = defaultStiffness
    config.finalValueBias = finalValueBias
    pendingAnimations += SpringAnimationHolder(anim, config)
  }

  private fun SpringAnimation.createEndListener(
    property: FloatPropertyCompat<View>
  ) {
    val listener = object : DynamicAnimation.OnAnimationEndListener {
      override fun onAnimationEnd(
        animation: DynamicAnimation<out DynamicAnimation<*>>?,
        canceled: Boolean,
        value: Float,
        velocity: Float
      ) {
        runningAnimations.remove(property)
        animation?.removeEndListener(this)

        if (runningAnimations.isEmpty() && !canceled) {
          animatorListener?.onAnimationEnd(this@ViewPropertySpringAnimator)
        }
      }
    }
    addEndListener(listener)
  }

  @MainThread
  fun start(): ViewPropertySpringAnimator = apply {
    val animations = pendingAnimations.toList()
    pendingAnimations.clear()
    animatorListener?.onAnimationStart(this)
    animations.forEach { it.start() }
  }

  @MainThread
  fun cancel() {
    pendingAnimations.clear()
    val animations = runningAnimations.values.toList()
    runningAnimations.clear()
    animations.forEach { it.cancel() }
    animatorListener?.onAnimationCancel(this)
  }

  @MainThread
  fun skipToEnd() {
    pendingAnimations.clear()
    val animations = runningAnimations.values.toList()
    animations.filter { it.canSkipToEnd() }
      .forEach { it.skipToEnd() }
  }

  fun setListener(
    onStart: (animator: ViewPropertySpringAnimator) -> Unit = {},
    onCancel: (animator: ViewPropertySpringAnimator) -> Unit = {},
    onEnd: (animator: ViewPropertySpringAnimator) -> Unit = {}
  ) = setListener(object : AnimatorListener {
    override fun onAnimationStart(animator: ViewPropertySpringAnimator) {
      onStart(animator)
    }

    override fun onAnimationCancel(animator: ViewPropertySpringAnimator) {
      onCancel(animator)
    }

    override fun onAnimationEnd(animator: ViewPropertySpringAnimator) {
      onEnd(animator)
    }
  })

  fun setListener(listener: AnimatorListener?) = apply {
    this.animatorListener = listener
  }

  fun removeUpdateListener(listener: DynamicAnimation.OnAnimationUpdateListener) {
    runningAnimations.forEach { (_, animation) ->
      animation.removeUpdateListener(listener)
    }
  }

  fun removeEndListener(listener: DynamicAnimation.OnAnimationEndListener) {
    runningAnimations.forEach { (_, animation) ->
      animation.removeEndListener(listener)
    }
  }
}
