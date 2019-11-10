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
  private val animatorMap = mutableMapOf<FloatPropertyCompat<in T>, SpringAnimation>()
  val isRunning: Boolean
    get() = animatorMap.values.any { it.isRunning }

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
    value: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animateProperty(DynamicAnimation.X, value, config)

  fun xBy(
    value: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animatePropertyBy(DynamicAnimation.X, value, config)

  fun y(
    value: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animateProperty(DynamicAnimation.Y, value, config)

  fun yBy(
    value: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animatePropertyBy(DynamicAnimation.Y, value, config)

  fun z(
    value: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animateProperty(DynamicAnimation.Z, value, config)

  fun zBy(
    value: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animatePropertyBy(DynamicAnimation.Z, value, config)

  fun rotation(
    value: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animateProperty(DynamicAnimation.ROTATION, value, config)

  fun rotationBy(
    value: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animatePropertyBy(DynamicAnimation.ROTATION, value, config)

  fun rotationX(
    value: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animateProperty(DynamicAnimation.ROTATION_X, value, config)

  fun rotationXBy(
    value: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animatePropertyBy(DynamicAnimation.ROTATION_X, value, config)

  fun rotationY(
    value: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animateProperty(DynamicAnimation.ROTATION_Y, value, config)

  fun rotationYBy(
    value: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animatePropertyBy(DynamicAnimation.ROTATION_Y, value, config)

  fun translationX(
    value: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animateProperty(DynamicAnimation.TRANSLATION_X, value, config)

  fun translationXBy(
    value: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animatePropertyBy(DynamicAnimation.TRANSLATION_X, value, config)

  fun translationY(
    value: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animateProperty(DynamicAnimation.TRANSLATION_Y, value, config)

  fun translationYBy(
    value: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animatePropertyBy(DynamicAnimation.TRANSLATION_Y, value, config)

  fun translationZ(
    value: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animateProperty(DynamicAnimation.TRANSLATION_Z, value, config)

  fun translationZBy(
    value: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animatePropertyBy(DynamicAnimation.TRANSLATION_Z, value, config)

  fun scaleX(
    value: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animateProperty(DynamicAnimation.SCALE_X, value, config)

  fun scaleXBy(
    value: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animatePropertyBy(DynamicAnimation.SCALE_X, value, config)

  fun scaleY(
    value: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animateProperty(DynamicAnimation.SCALE_Y, value, config)

  fun scaleYBy(
    value: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animatePropertyBy(DynamicAnimation.SCALE_Y, value, config)

  fun alpha(
    value: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animateProperty(DynamicAnimation.ALPHA, value, config)

  fun alphaBy(
    value: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animatePropertyBy(DynamicAnimation.ALPHA, value, config)

  fun animateProperty(
    value: Float,
    setter: T.(Float) -> Unit,
    getter: T.() -> Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animateProperty(createCustomProperty(setter, getter), value, config)

  fun animatePropertyBy(
    setter: T.(Float) -> Unit,
    getter: T.() -> Float,
    value: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ): ViewPropertySpringAnimator<T> =
    animatePropertyBy(createCustomProperty(setter, getter), value, config)

  fun animateProperty(
    property: FloatPropertyCompat<in T>,
    value: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ) = apply {
    animatePropertyInternal(property, value, config)
  }

  fun animatePropertyBy(
    property: FloatPropertyCompat<in T>,
    value: Float,
    config: SpringAnimationConfig.() -> Unit = {}
  ) = apply {
    animatePropertyInternal(property, value + property.getValue(view), config)
  }

  private fun animatePropertyInternal(
    property: FloatPropertyCompat<in T>,
    finalValue: Float,
    configBuilder: SpringAnimationConfig.() -> Unit = {}
  ) {
    var anim = animatorMap[property]
    if (anim == null) {
      anim = SpringAnimation(view, property)
      anim.createEndListener(property)
      animatorMap[property] = anim
    }
    val config = SpringAnimationConfig(finalValue).apply(configBuilder)
    config.defaultDampingRatio = defaultDampingRatio
    config.defaultStiffness = defaultStiffness
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
        animatorMap.remove(property)
        animation?.removeEndListener(this)

        if (animatorMap.isEmpty() && !canceled) {
          animatorListener?.onAnimationEnd(this@ViewPropertySpringAnimator)
        }
      }
    }
    addEndListener(listener)
  }

  @MainThread
  fun start(): ViewPropertySpringAnimator<T> = apply {
    if (pendingAnimations.isEmpty()) return@apply
    val animations = pendingAnimations.toList()
    pendingAnimations.clear()
    animatorListener?.onAnimationStart(this)
    animations.forEach { it.start() }
  }

  @MainThread
  fun cancel() {
    pendingAnimations.clear()
    val animations = animatorMap.values.toList()
    animatorMap.clear()
    animations.forEach { it.cancel() }
    animatorListener?.onAnimationCancel(this)
  }

  @MainThread
  fun skipToEnd() {
    pendingAnimations.clear()
    val animations = animatorMap.values.toList()
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
    animatorMap.forEach { (_, animation) ->
      animation.removeUpdateListener(listener)
    }
  }

  fun removeEndListener(listener: DynamicAnimation.OnAnimationEndListener) {
    animatorMap.forEach { (_, animation) ->
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
