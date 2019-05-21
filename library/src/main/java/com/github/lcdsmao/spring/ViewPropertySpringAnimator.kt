package com.github.lcdsmao.spring

import android.view.View
import androidx.annotation.MainThread
import androidx.core.view.ViewCompat
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce

class ViewPropertySpringAnimator(
  private val view: View
) {

  private val animationMap = HashMap<DynamicAnimation.ViewProperty, SpringAnimation>()
  private val pendingAnimations = mutableListOf<SpringAnimationHolder>()

  var defaultDampingRatio: Float = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
  var defaultStiffness: Float = SpringForce.STIFFNESS_MEDIUM

  fun defaultDampingRatio(dampingRatio: Float) = apply { defaultDampingRatio = dampingRatio }
  fun defaultStiffness(stiffness: Float) = apply { defaultStiffness = stiffness }

  fun x(finalValue: Float): ViewPropertySpringAnimator = apply {
    x(SpringAnimationConfig(finalValue))
  }

  fun x(finalValue: Float, config: SpringAnimationConfig.() -> Unit):
    ViewPropertySpringAnimator = apply {
    x(SpringAnimationConfig(finalValue).apply(config))
  }

  fun x(config: SpringAnimationConfig): ViewPropertySpringAnimator = apply {
    animateProperty(DynamicAnimation.X, config)
  }

  fun xBy(finalValue: Float): ViewPropertySpringAnimator = apply {
    xBy(SpringAnimationConfig(finalValue))
  }

  fun xBy(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit
  ): ViewPropertySpringAnimator = apply {
    xBy(SpringAnimationConfig(finalValue).apply(config))
  }

  fun xBy(config: SpringAnimationConfig): ViewPropertySpringAnimator = apply {
    animatePropertyBy(DynamicAnimation.X, config)
  }

  fun y(finalValue: Float): ViewPropertySpringAnimator = apply {
    y(SpringAnimationConfig(finalValue))
  }

  fun y(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit
  ): ViewPropertySpringAnimator = apply {
    y(SpringAnimationConfig(finalValue).apply(config))
  }

  fun y(config: SpringAnimationConfig): ViewPropertySpringAnimator = apply {
    animateProperty(DynamicAnimation.Y, config)
  }

  fun yBy(finalValue: Float): ViewPropertySpringAnimator = apply {
    yBy(SpringAnimationConfig(finalValue))
  }

  fun yBy(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit
  ): ViewPropertySpringAnimator = apply {
    yBy(SpringAnimationConfig(finalValue).apply(config))
  }

  fun yBy(config: SpringAnimationConfig): ViewPropertySpringAnimator = apply {
    animatePropertyBy(DynamicAnimation.Y, config)
  }

  fun z(finalValue: Float): ViewPropertySpringAnimator = apply {
    z(SpringAnimationConfig(finalValue))
  }

  fun z(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit
  ): ViewPropertySpringAnimator = apply {
    z(SpringAnimationConfig(finalValue).apply(config))
  }

  fun z(config: SpringAnimationConfig): ViewPropertySpringAnimator = apply {
    animateProperty(DynamicAnimation.Z, config)
  }

  fun zBy(finalValue: Float): ViewPropertySpringAnimator = apply {
    zBy(SpringAnimationConfig(finalValue))
  }

  fun zBy(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit
  ): ViewPropertySpringAnimator = apply {
    zBy(SpringAnimationConfig(finalValue).apply(config))
  }

  fun zBy(config: SpringAnimationConfig): ViewPropertySpringAnimator = apply {
    animatePropertyBy(DynamicAnimation.Z, config)
  }

  fun rotation(finalValue: Float): ViewPropertySpringAnimator = apply {
    rotation(SpringAnimationConfig(finalValue))
  }

  fun rotation(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit
  ): ViewPropertySpringAnimator = apply {
    rotation(SpringAnimationConfig(finalValue).apply(config))
  }

  fun rotation(config: SpringAnimationConfig): ViewPropertySpringAnimator = apply {
    animateProperty(DynamicAnimation.ROTATION, config)
  }

  fun rotationBy(finalValue: Float): ViewPropertySpringAnimator = apply {
    rotationBy(SpringAnimationConfig(finalValue))
  }

  fun rotationBy(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit
  ): ViewPropertySpringAnimator = apply {
    rotationBy(SpringAnimationConfig(finalValue).apply(config))
  }

  fun rotationBy(config: SpringAnimationConfig): ViewPropertySpringAnimator = apply {
    animatePropertyBy(DynamicAnimation.ROTATION, config)
  }

  fun rotationX(finalValue: Float): ViewPropertySpringAnimator = apply {
    rotationX(SpringAnimationConfig(finalValue))
  }

  fun rotationX(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit
  ): ViewPropertySpringAnimator = apply {
    rotationX(SpringAnimationConfig(finalValue).apply(config))
  }

  fun rotationX(config: SpringAnimationConfig): ViewPropertySpringAnimator = apply {
    animateProperty(DynamicAnimation.ROTATION_X, config)
  }

  fun rotationXBy(finalValue: Float): ViewPropertySpringAnimator = apply {
    rotationXBy(SpringAnimationConfig(finalValue))
  }

  fun rotationXBy(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit
  ): ViewPropertySpringAnimator = apply {
    rotationXBy(SpringAnimationConfig(finalValue).apply(config))
  }

  fun rotationXBy(config: SpringAnimationConfig): ViewPropertySpringAnimator = apply {
    animatePropertyBy(DynamicAnimation.ROTATION_X, config)
  }

  fun rotationY(finalValue: Float): ViewPropertySpringAnimator = apply {
    rotationY(SpringAnimationConfig(finalValue))
  }

  fun rotationY(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit
  ): ViewPropertySpringAnimator = apply {
    rotationY(SpringAnimationConfig(finalValue).apply(config))
  }

  fun rotationY(config: SpringAnimationConfig): ViewPropertySpringAnimator = apply {
    animateProperty(DynamicAnimation.ROTATION_Y, config)
  }

  fun rotationYBy(finalValue: Float): ViewPropertySpringAnimator = apply {
    rotationYBy(SpringAnimationConfig(finalValue))
  }

  fun rotationYBy(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit
  ): ViewPropertySpringAnimator = apply {
    rotationYBy(SpringAnimationConfig(finalValue).apply(config))
  }

  fun rotationYBy(config: SpringAnimationConfig): ViewPropertySpringAnimator = apply {
    animatePropertyBy(DynamicAnimation.ROTATION_Y, config)
  }

  fun translationX(finalValue: Float): ViewPropertySpringAnimator = apply {
    translationX(SpringAnimationConfig(finalValue))
  }

  fun translationX(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit
  ): ViewPropertySpringAnimator = apply {
    translationX(SpringAnimationConfig(finalValue).apply(config))
  }

  fun translationX(config: SpringAnimationConfig): ViewPropertySpringAnimator = apply {
    animateProperty(DynamicAnimation.TRANSLATION_X, config)
  }

  fun translationXBy(finalValue: Float): ViewPropertySpringAnimator = apply {
    translationXBy(SpringAnimationConfig(finalValue))
  }

  fun translationXBy(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit
  ): ViewPropertySpringAnimator = apply {
    translationXBy(SpringAnimationConfig(finalValue).apply(config))
  }

  fun translationXBy(config: SpringAnimationConfig): ViewPropertySpringAnimator = apply {
    animatePropertyBy(DynamicAnimation.TRANSLATION_X, config)
  }

  fun translationY(finalValue: Float): ViewPropertySpringAnimator = apply {
    translationY(SpringAnimationConfig(finalValue))
  }

  fun translationY(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit
  ): ViewPropertySpringAnimator = apply {
    translationY(SpringAnimationConfig(finalValue).apply(config))
  }

  fun translationY(config: SpringAnimationConfig): ViewPropertySpringAnimator = apply {
    animateProperty(DynamicAnimation.TRANSLATION_Y, config)
  }

  fun translationYBy(finalValue: Float): ViewPropertySpringAnimator = apply {
    translationYBy(SpringAnimationConfig(finalValue))
  }

  fun translationYBy(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit
  ): ViewPropertySpringAnimator = apply {
    translationYBy(SpringAnimationConfig(finalValue).apply(config))
  }

  fun translationYBy(config: SpringAnimationConfig): ViewPropertySpringAnimator = apply {
    animatePropertyBy(DynamicAnimation.TRANSLATION_Y, config)
  }

  fun translationZ(finalValue: Float): ViewPropertySpringAnimator = apply {
    translationZ(SpringAnimationConfig(finalValue))
  }

  fun translationZ(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit
  ): ViewPropertySpringAnimator = apply {
    translationZ(SpringAnimationConfig(finalValue).apply(config))
  }

  fun translationZ(config: SpringAnimationConfig): ViewPropertySpringAnimator = apply {
    animateProperty(DynamicAnimation.TRANSLATION_Z, config)
  }

  fun translationZBy(finalValue: Float): ViewPropertySpringAnimator = apply {
    translationZBy(SpringAnimationConfig(finalValue))
  }

  fun translationZBy(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit
  ): ViewPropertySpringAnimator = apply {
    translationZBy(SpringAnimationConfig(finalValue).apply(config))
  }

  fun translationZBy(config: SpringAnimationConfig): ViewPropertySpringAnimator = apply {
    animatePropertyBy(DynamicAnimation.TRANSLATION_Z, config)
  }

  fun scaleX(finalValue: Float): ViewPropertySpringAnimator = apply {
    scaleX(SpringAnimationConfig(finalValue))
  }

  fun scaleX(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit
  ): ViewPropertySpringAnimator = apply {
    scaleX(SpringAnimationConfig(finalValue).apply(config))
  }

  fun scaleX(config: SpringAnimationConfig): ViewPropertySpringAnimator = apply {
    animateProperty(DynamicAnimation.SCALE_X, config)
  }

  fun scaleXBy(finalValue: Float): ViewPropertySpringAnimator = apply {
    scaleXBy(SpringAnimationConfig(finalValue))
  }

  fun scaleXBy(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit
  ): ViewPropertySpringAnimator = apply {
    scaleXBy(SpringAnimationConfig(finalValue).apply(config))
  }

  fun scaleXBy(config: SpringAnimationConfig): ViewPropertySpringAnimator = apply {
    animatePropertyBy(DynamicAnimation.SCALE_X, config)
  }

  fun scaleY(finalValue: Float): ViewPropertySpringAnimator = apply {
    scaleY(SpringAnimationConfig(finalValue))
  }

  fun scaleY(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit
  ): ViewPropertySpringAnimator = apply {
    scaleY(SpringAnimationConfig(finalValue).apply(config))
  }

  fun scaleY(config: SpringAnimationConfig): ViewPropertySpringAnimator = apply {
    animateProperty(DynamicAnimation.SCALE_Y, config)
  }

  fun scaleYBy(finalValue: Float): ViewPropertySpringAnimator = apply {
    scaleYBy(SpringAnimationConfig(finalValue))
  }

  fun scaleYBy(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit
  ): ViewPropertySpringAnimator = apply {
    scaleYBy(SpringAnimationConfig(finalValue).apply(config))
  }

  fun scaleYBy(config: SpringAnimationConfig): ViewPropertySpringAnimator = apply {
    animatePropertyBy(DynamicAnimation.SCALE_Y, config)
  }

  fun alpha(finalValue: Float): ViewPropertySpringAnimator = apply {
    alpha(SpringAnimationConfig(finalValue))
  }

  fun alpha(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit
  ): ViewPropertySpringAnimator = apply {
    alpha(SpringAnimationConfig(finalValue).apply(config))
  }

  fun alpha(config: SpringAnimationConfig): ViewPropertySpringAnimator = apply {
    animateProperty(DynamicAnimation.ALPHA, config)
  }

  fun alphaBy(finalValue: Float): ViewPropertySpringAnimator = apply {
    alphaBy(SpringAnimationConfig(finalValue))
  }

  fun alphaBy(
    finalValue: Float,
    config: SpringAnimationConfig.() -> Unit
  ): ViewPropertySpringAnimator = apply {
    alphaBy(SpringAnimationConfig(finalValue).apply(config))
  }

  fun alphaBy(config: SpringAnimationConfig): ViewPropertySpringAnimator = apply {
    animatePropertyBy(DynamicAnimation.ALPHA, config)
  }

  private fun animateProperty(
    property: DynamicAnimation.ViewProperty,
    config: SpringAnimationConfig
  ) {
    animateProperty(property, config, 0f)
  }

  private fun animatePropertyBy(
      property: DynamicAnimation.ViewProperty,
      config: SpringAnimationConfig
  ) {
    animateProperty(property, config, getViewValue(property))
  }

  private fun animateProperty(
    property: DynamicAnimation.ViewProperty,
    config: SpringAnimationConfig,
    finalValueBias: Float
  ) {
    var anim = animationMap[property]
    if (anim == null) {
      anim = SpringAnimation(view, property)
      anim.createEndListener(property)
      animationMap[property] = anim
    }
    val immutableConfig =
      ImmutableSpringAnimationConfig(config, finalValueBias, defaultDampingRatio, defaultStiffness)
    pendingAnimations.add(SpringAnimationHolder(anim, immutableConfig))
  }

  private fun getViewValue(property: DynamicAnimation.ViewProperty): Float {
    return when (property) {
      DynamicAnimation.TRANSLATION_X -> view.translationX
      DynamicAnimation.TRANSLATION_Y -> view.translationY
      DynamicAnimation.TRANSLATION_Z -> ViewCompat.getTranslationZ(view)
      DynamicAnimation.ROTATION -> view.rotation
      DynamicAnimation.ROTATION_X -> view.rotationX
      DynamicAnimation.ROTATION_Y -> view.rotationY
      DynamicAnimation.SCALE_X -> view.scaleX
      DynamicAnimation.SCALE_Y -> view.scaleY
      DynamicAnimation.X -> view.x
      DynamicAnimation.Y -> view.y
      DynamicAnimation.Z -> ViewCompat.getZ(view)
      DynamicAnimation.ALPHA -> view.alpha
      else -> 0f
    }
  }

  private fun SpringAnimation.createEndListener(
    property: DynamicAnimation.ViewProperty
  ) {
    val listener = object : DynamicAnimation.OnAnimationEndListener {
      override fun onAnimationEnd(
        animation: DynamicAnimation<out DynamicAnimation<*>>?,
        canceled: Boolean,
        value: Float,
        velocity: Float
      ) {
        animationMap.remove(property)
        animation?.removeEndListener(this)
      }
    }
    addEndListener(listener)
  }

  @MainThread
  fun start(): ViewPropertySpringAnimator = apply {
    val animations = ArrayList(pendingAnimations)
    pendingAnimations.clear()
    animations.forEach {
      it.start()
    }
  }

  @MainThread
  fun cancel() {
    pendingAnimations.clear()
    val animations = ArrayList(animationMap.values)
    animationMap.clear()
    animations.forEach {
      it.cancel()
    }
  }

  fun removeUpdateListener(listener: DynamicAnimation.OnAnimationUpdateListener) {
    animationMap.forEach { (_, animation) ->
      animation.removeUpdateListener(listener)
    }
  }

  fun removeEndListener(listener: DynamicAnimation.OnAnimationEndListener) {
    animationMap.forEach { (_, animation) ->
      animation.removeEndListener(listener)
    }
  }
}
