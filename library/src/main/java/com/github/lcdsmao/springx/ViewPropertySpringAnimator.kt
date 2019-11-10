package com.github.lcdsmao.springx

import android.view.View
import androidx.annotation.MainThread
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FloatPropertyCompat
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce

class ViewPropertySpringAnimator<T : View>(
  private val view: T
) {

  interface AnimatorListener<T : View> {
    fun onAnimationStart(animator: ViewPropertySpringAnimator<T>) {}
    fun onAnimationCancel(animator: ViewPropertySpringAnimator<T>) {}
    fun onAnimationEnd(animator: ViewPropertySpringAnimator<T>) {}
  }

  private val pendingAnimations = mutableListOf<SpringAnimationHolder>()
  private val runningAnimations = mutableMapOf<FloatPropertyCompat<in T>, SpringAnimation>()
  val isRunning: Boolean
    get() = runningAnimations.isNotEmpty()

  private var defaultDampingRatio: Float = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
  private var defaultStiffness: Float = SpringForce.STIFFNESS_MEDIUM
  private var animatorListener: AnimatorListener<T>? = null

  fun defaultDampingRatio(value: Float) = apply {
    defaultDampingRatio = value
  }

  fun defaultStiffness(value: Float) = apply {
    defaultStiffness = value
  }

  fun x(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animateProperty(DynamicAnimation.X, finalValue, config)

  fun xBy(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animatePropertyBy(DynamicAnimation.X, finalValue, config)

  fun y(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animateProperty(DynamicAnimation.Y, finalValue, config)

  fun yBy(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animatePropertyBy(DynamicAnimation.Y, finalValue, config)

  fun z(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animateProperty(DynamicAnimation.Z, finalValue, config)

  fun zBy(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animatePropertyBy(DynamicAnimation.Z, finalValue, config)

  fun rotation(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animateProperty(DynamicAnimation.ROTATION, finalValue, config)

  fun rotationBy(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animatePropertyBy(DynamicAnimation.ROTATION, finalValue, config)

  fun rotationX(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animateProperty(DynamicAnimation.ROTATION_X, finalValue, config)

  fun rotationXBy(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animatePropertyBy(DynamicAnimation.ROTATION_X, finalValue, config)

  fun rotationY(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animateProperty(DynamicAnimation.ROTATION_Y, finalValue, config)

  fun rotationYBy(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animatePropertyBy(DynamicAnimation.ROTATION_Y, finalValue, config)

  fun translationX(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animateProperty(DynamicAnimation.TRANSLATION_X, finalValue, config)

  fun translationXBy(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animatePropertyBy(DynamicAnimation.TRANSLATION_X, finalValue, config)

  fun translationY(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animateProperty(DynamicAnimation.TRANSLATION_Y, finalValue, config)

  fun translationYBy(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animatePropertyBy(DynamicAnimation.TRANSLATION_Y, finalValue, config)

  fun translationZ(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animateProperty(DynamicAnimation.TRANSLATION_Z, finalValue, config)

  fun translationZBy(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animatePropertyBy(DynamicAnimation.TRANSLATION_Z, finalValue, config)

  fun scaleX(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animateProperty(DynamicAnimation.SCALE_X, finalValue, config)

  fun scaleXBy(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animatePropertyBy(DynamicAnimation.SCALE_X, finalValue, config)

  fun scaleY(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animateProperty(DynamicAnimation.SCALE_Y, finalValue, config)

  fun scaleYBy(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animatePropertyBy(DynamicAnimation.SCALE_Y, finalValue, config)

  fun alpha(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animateProperty(DynamicAnimation.ALPHA, finalValue, config)

  fun alphaBy(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animatePropertyBy(DynamicAnimation.ALPHA, finalValue, config)

  fun animateProperty(
    finalValue: Float,
    setter: T.(Float) -> Unit,
    getter: T.() -> Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animateProperty(createCustomProperty(setter, getter), finalValue, config)

  fun animatePropertyBy(
    setter: T.(Float) -> Unit,
    getter: T.() -> Float,
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animatePropertyBy(createCustomProperty(setter, getter), finalValue, config)

  fun animateProperty(
    property: FloatPropertyCompat<in T>,
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ) = apply {
    animateProperty(property, finalValue, 0f, config)
  }

  fun animatePropertyBy(
    property: FloatPropertyCompat<in T>,
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ) = apply {
    animateProperty(property, finalValue, property.getValue(view), config)
  }

  private fun animateProperty(
    property: FloatPropertyCompat<in T>,
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
    property: FloatPropertyCompat<in T>
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
  fun start(): ViewPropertySpringAnimator<T> = apply {
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
    onStart: (animator: ViewPropertySpringAnimator<T>) -> Unit = {},
    onCancel: (animator: ViewPropertySpringAnimator<T>) -> Unit = {},
    onEnd: (animator: ViewPropertySpringAnimator<T>) -> Unit = {}
  ) = setListener(object : AnimatorListener<T> {
    override fun onAnimationStart(animator: ViewPropertySpringAnimator<T>) {
      onStart(animator)
    }

    override fun onAnimationCancel(animator: ViewPropertySpringAnimator<T>) {
      onCancel(animator)
    }

    override fun onAnimationEnd(animator: ViewPropertySpringAnimator<T>) {
      onEnd(animator)
    }
  })

  fun setListener(listener: AnimatorListener<T>?) = apply {
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

  private fun createCustomProperty(
    setter: T.(Float) -> Unit,
    getter: T.() -> Float
  ) = object : FloatPropertyCompat<T>("CustomProperty") {
    override fun getValue(view: T): Float {
      return getter.invoke(view)
    }

    override fun setValue(view: T, value: Float) {
      setter.invoke(view, value)
    }
  }
}
