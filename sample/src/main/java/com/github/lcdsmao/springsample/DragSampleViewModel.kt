package com.github.lcdsmao.springsample

import androidx.dynamicanimation.animation.SpringForce
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

private const val STIFFNESS_MAX = 10_000f
private const val STIFFNESS_MIN = 100f

class DragSampleViewModel : ViewModel() {

  private val _dampingRatio = MutableLiveData<Float>().apply {
    value = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
  }
  val dampingRatio: LiveData<Float>
    get() = _dampingRatio
  val dampingProgress: LiveData<Int>
    get() = Transformations.map(dampingRatio) {
      (it * 100).toInt()
    }

  private val _stiffness = MutableLiveData<Float>().apply {
    value = SpringForce.STIFFNESS_MEDIUM
  }
  val stiffness: LiveData<Float>
    get() = _stiffness
  val stiffnessProgress: LiveData<Int>
    get() = Transformations.map(stiffness) {
      ((it - STIFFNESS_MIN) / (STIFFNESS_MAX - STIFFNESS_MIN) * 100).toInt()
    }

  fun setDampingRatio(value: Float) {
    _dampingRatio.value = value
  }

  fun setStiffness(value: Float) {
    _stiffness.value = value
  }

  fun setDampingProgress(progress: Int) {
    setDampingRatio(progress / 100f)
  }

  fun setStiffnessProgress(progress: Int) {
    setStiffness(progress.toFloat() / 100 * (STIFFNESS_MAX - STIFFNESS_MIN) + STIFFNESS_MIN)
  }
}
