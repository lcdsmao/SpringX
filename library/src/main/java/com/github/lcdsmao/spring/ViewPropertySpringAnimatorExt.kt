package com.github.lcdsmao.spring

import android.view.View

fun View.spring(): ViewPropertySpringAnimator {
  var springAnimator = getTag(R.id.view_property_spring_animator_key) as? ViewPropertySpringAnimator
  if (springAnimator == null) {
    springAnimator = ViewPropertySpringAnimator(this)
    setTag(R.id.view_property_spring_animator_key, springAnimator)
  }
  return springAnimator
}
