package com.github.lcdsmao.springx

import android.view.View
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FloatPropertyCompat
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.google.common.truth.Truth
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

abstract class ViewPropertySpringAnimatorTest : UiTest {

  @get:Rule
  val activityRule = ActivityScenarioRule<AnimationActivity>(AnimationActivity::class.java)

  private lateinit var animView: View

  @Before
  fun setup() {
    activityRule.scenario.onActivity {
      animView = it.findViewById(R.id.anim_view)
    }
  }

  @Test
  fun testAnimateCommonProperty() {
    val cases = listOf(
      DynamicAnimation.X to 100f,
      DynamicAnimation.Y to -100f,
      DynamicAnimation.Z to 20f,
      DynamicAnimation.TRANSLATION_X to -200f,
      DynamicAnimation.TRANSLATION_Y to 200f,
      DynamicAnimation.TRANSLATION_Z to -20f,
      DynamicAnimation.ROTATION to 45f,
      DynamicAnimation.ROTATION_X to 30f,
      DynamicAnimation.ROTATION_Y to 60f,
      DynamicAnimation.SCALE_X to 4f,
      DynamicAnimation.SCALE_Y to 8f,
      DynamicAnimation.ALPHA to 0.5f
    )

    cases.forEach { (p, v) ->
      val listener = mockk<ViewPropertySpringAnimator.AnimatorListener<View>>(relaxed = true)
      val anim = ViewPropertySpringAnimator(animView).animate(p, v, false)
        .setListener(listener)
      runOnMainThread {
        anim.start()
        Truth.assertThat(anim.isRunning).isTrue()
        anim.skipToEnd()
      }
      verify(exactly = 1) { listener.onAnimationStart(anim) }
      verify(exactly = 1, timeout = 1000) { listener.onAnimationEnd(anim) }
      Truth.assertThat(anim.isRunning).isFalse()
      Truth.assertThat(p.getValue(animView)).isEqualTo(v)
    }
  }

  @Test
  fun testAnimateByCommonProperty() {
    val cases = listOf(
      DynamicAnimation.X to 100f,
      DynamicAnimation.Y to -100f,
      DynamicAnimation.Z to 20f,
      DynamicAnimation.TRANSLATION_X to -200f,
      DynamicAnimation.TRANSLATION_Y to 200f,
      DynamicAnimation.TRANSLATION_Z to -20f,
      DynamicAnimation.ROTATION to 45f,
      DynamicAnimation.ROTATION_X to 30f,
      DynamicAnimation.ROTATION_Y to 60f,
      DynamicAnimation.SCALE_X to 4f,
      DynamicAnimation.SCALE_Y to 8f,
      DynamicAnimation.ALPHA to -0.5f
    )

    cases.forEach { (p, v) ->
      val oldValue = p.getValue(animView)
      val listener = mockk<ViewPropertySpringAnimator.AnimatorListener<View>>(relaxed = true)
      val anim = ViewPropertySpringAnimator(animView).animate(p, v, true)
        .setListener(listener)
      runOnMainThread {
        anim.start()
        Truth.assertThat(anim.isRunning).isTrue()
        anim.skipToEnd()
      }
      verify(exactly = 1) { listener.onAnimationStart(anim) }
      verify(exactly = 1, timeout = 1000) { listener.onAnimationEnd(anim) }
      Truth.assertThat(anim.isRunning).isFalse()
      Truth.assertThat(p.getValue(animView)).isEqualTo(oldValue + v)
    }
  }

  @Test
  fun testAnimateCustomProperty() {
    val property = object : FloatPropertyCompat<View>("Custom") {

      private var value = 50f

      override fun getValue(view: View): Float {
        return value
      }

      override fun setValue(view: View, value: Float) {
        this.value = value
      }
    }
    val listener = mockk<ViewPropertySpringAnimator.AnimatorListener<View>>(relaxed = true)
    val anim = ViewPropertySpringAnimator(animView).animateProperty(property, 100f)
      .setListener(listener)
    runOnMainThread {
      anim.start()
      Truth.assertThat(anim.isRunning).isTrue()
      anim.skipToEnd()
    }
    verify(exactly = 1) { listener.onAnimationStart(anim) }
    verify(exactly = 1, timeout = 1000) { listener.onAnimationEnd(anim) }
    Truth.assertThat(anim.isRunning).isFalse()
    Truth.assertThat(property.getValue(animView)).isEqualTo(100f)
  }

  @Test
  fun testAnimateByCustomProperty() {
    val property = object : FloatPropertyCompat<View>("Custom") {

      private var value = 50f

      override fun getValue(view: View): Float {
        return value
      }

      override fun setValue(view: View, value: Float) {
        this.value = value
      }
    }
    val listener = mockk<ViewPropertySpringAnimator.AnimatorListener<View>>(relaxed = true)
    val anim = ViewPropertySpringAnimator(animView).animatePropertyBy(property, 100f)
      .setListener(listener)
    runOnMainThread {
      anim.start()
      Truth.assertThat(anim.isRunning).isTrue()
      anim.skipToEnd()
    }
    verify(exactly = 1) { listener.onAnimationStart(anim) }
    verify(exactly = 1, timeout = 1000) { listener.onAnimationEnd(anim) }
    Truth.assertThat(anim.isRunning).isFalse()
    Truth.assertThat(property.getValue(animView)).isEqualTo(50f + 100f)
  }

  private fun <T : View> ViewPropertySpringAnimator<T>.animate(
    property: DynamicAnimation.ViewProperty,
    value: Float,
    relative: Boolean
  ) = apply {
    when (property) {
      DynamicAnimation.X -> if (relative) xBy(value) else x(value)
      DynamicAnimation.Y -> if (relative) yBy(value) else y(value)
      DynamicAnimation.Z -> if (relative) zBy(value) else z(value)
      DynamicAnimation.TRANSLATION_X -> if (relative) translationXBy(value) else translationX(value)
      DynamicAnimation.TRANSLATION_Y -> if (relative) translationYBy(value) else translationY(value)
      DynamicAnimation.TRANSLATION_Z -> if (relative) translationZBy(value) else translationZ(value)
      DynamicAnimation.ROTATION -> if (relative) rotationBy(value) else rotation(value)
      DynamicAnimation.ROTATION_X -> if (relative) rotationXBy(value) else rotationX(value)
      DynamicAnimation.ROTATION_Y -> if (relative) rotationYBy(value) else rotationY(value)
      DynamicAnimation.SCALE_X -> if (relative) scaleXBy(value) else scaleX(value)
      DynamicAnimation.SCALE_Y -> if (relative) scaleYBy(value) else scaleY(value)
      DynamicAnimation.ALPHA -> if (relative) alphaBy(value) else alpha(value)
    }
  }
}
