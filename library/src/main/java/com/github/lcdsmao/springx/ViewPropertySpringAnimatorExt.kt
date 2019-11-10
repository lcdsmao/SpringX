package com.github.lcdsmao.springx

import android.view.View

@Suppress("UNCHECKED_CAST")
fun <T : View> T.spring(): ViewPropertySpringAnimator<T> {
  var springAnimator = getTag(R.id.view_property_spring_animator_key) as? ViewPropertySpringAnimator<T>
  if (springAnimator == null) {
    springAnimator = ViewPropertySpringAnimator(this)
    setTag(R.id.view_property_spring_animator_key, springAnimator)
  }
  return springAnimator
}
