package com.github.lcdsmao.springx

import android.view.View
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FloatPropertyCompat
import androidx.dynamicanimation.animation.SpringForce
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.LooperMode

@RunWith(AndroidJUnit4::class)
@LooperMode(LooperMode.Mode.PAUSED)
class ViewPropertySpringAnimatorTest {

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
  fun testAnimateCommonProperty() = runUiTest {
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
      runOnMainSync {
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
  fun testAnimateByCommonProperty() = runUiTest {
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
      runOnMainSync {
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
  fun testAnimateCustomProperty() = runUiTest {
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
    runOnMainSync {
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
  fun testAnimateByCustomProperty() = runUiTest {
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
    runOnMainSync {
      anim.start()
      Truth.assertThat(anim.isRunning).isTrue()
      anim.skipToEnd()
    }
    verify(exactly = 1) { listener.onAnimationStart(anim) }
    verify(exactly = 1, timeout = 1000) { listener.onAnimationEnd(anim) }
    Truth.assertThat(anim.isRunning).isFalse()
    Truth.assertThat(property.getValue(animView)).isEqualTo(50f + 100f)
  }

  @Test
  fun testAnimatorReuse() = runUiTest {
    val anim1 = animView.spring().translationX(-100f)
    val listener = mockk<ViewPropertySpringAnimator.AnimatorListener<View>>(relaxed = true)
    anim1.setListener(listener)
    val anim2 = animView.spring().translationX(100f)
    Truth.assertThat(anim1).isSameAs(anim2)

    runOnMainSync { anim2.start() }
    runOnMainSync { anim1.skipToEnd() }
    verify(exactly = 1, timeout = 1000) { listener.onAnimationEnd(anim1) }
    Truth.assertThat(animView.translationX).isEqualTo(100f)
  }

  @Test
  @Ignore("Fix the implementation of [ViewPropertySpringAnimator] to let this test pass")
  fun testDampingRatio() = runUiTest {
    val onEnd = mockk<(Int) -> Unit>(relaxed = true)
    val anim = animView.spring()
      .defaultDampingRatio(SpringForce.DAMPING_RATIO_NO_BOUNCY)
      .rotation(100f) {
        dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
        onEnd { _, _, _, _ -> onEnd.invoke(2) }
      }
      .translationY(100f) {
        dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
        onEnd { _, _, _, _ -> onEnd.invoke(1) }
      }
      .translationX(100f) {
        onEnd { _, _, _, _ -> onEnd.invoke(0) }
      }
      .setListener(onEnd = { onEnd.invoke(3) })
    runOnMainSync { anim.start() }
    verify(exactly = 4, timeout = 1000L) { onEnd.invoke(any()) }
    verifySequence {
      onEnd.invoke(0)
      onEnd.invoke(1)
      onEnd.invoke(2)
      onEnd.invoke(3)
    }
  }

  @Test
  @Ignore("Fix the implementation of [ViewPropertySpringAnimator] to let this test pass")
  fun testStiffness() = runUiTest {
    val onEnd = mockk<(Int) -> Unit>(relaxed = true)
    val anim = animView.spring()
      .defaultStiffness(SpringForce.STIFFNESS_HIGH)
      .rotation(100f) {
        stiffness = SpringForce.STIFFNESS_MEDIUM
        onEnd { _, _, _, _ -> onEnd.invoke(2) }
      }
      .translationY(100f) {
        stiffness = SpringForce.STIFFNESS_LOW
        onEnd { _, _, _, _ -> onEnd.invoke(1) }
      }
      .translationX(100f) {
        onEnd { _, _, _, _ -> onEnd.invoke(0) }
      }
      .setListener(onEnd = { onEnd.invoke(3) })
    runOnMainSync { anim.start() }
    verify(exactly = 4, timeout = 1000L) { onEnd.invoke(any()) }
    verifySequence {
      onEnd.invoke(0)
      onEnd.invoke(1)
      onEnd.invoke(2)
      onEnd.invoke(3)
    }
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
