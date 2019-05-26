package com.github.lcdsmao.springsample

import android.view.View
import androidx.dynamicanimation.animation.SpringForce
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.github.lcdsmao.springx.ViewPropertySpringAnimator
import com.github.lcdsmao.springx.spring

class SpringMoveItemAnimator : SimpleItemAnimator() {

  var dampingRatio: Float = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
  var stiffness: Float = SpringForce.STIFFNESS_MEDIUM

  private val pendingAnimations = mutableListOf<RecyclerView.ViewHolder>()
  private val runningAnimations = mutableListOf<RecyclerView.ViewHolder>()

  override fun runPendingAnimations() {
    val animations = pendingAnimations.toList()
    pendingAnimations.clear()
    runningAnimations.addAll(animations)
    animations.forEach {
      it.itemView.spring().start()
    }
  }

  override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
    dispatchAddFinished(holder)
    return false
  }

  override fun animateMove(
    holder: RecyclerView.ViewHolder?,
    fromX: Int,
    fromY: Int,
    toX: Int,
    toY: Int
  ): Boolean {
    if (holder == null) {
      dispatchAddFinished(holder)
      return false
    }
    val currentX = fromX + holder.itemView.translationX
    val currentY = fromY + holder.itemView.translationY
    val deltaX = toX - currentX
    val deltaY = toY - currentY
    val animation = holder.itemView.spring()
    animation.cancel()
    holder.itemView.translationX = -deltaX
    holder.itemView.translationY = -deltaY
    animation
      .reset()
      .alpha(1f)
      .translationX(0f) {
        startVelocity = holder.itemView.translationXVelocity
        onEnd { _, _, _, velocity ->
          holder.itemView.translationXVelocity = velocity
        }
      }
      .translationY(0f) {
        startValue = -deltaY
        onEnd { _, _, _, velocity ->
          holder.itemView.translationYVelocity = velocity
        }
      }
      .setListener(
        onStart = {
          dispatchMoveStarting(holder)
        },
        onCancel = {
          it.setListener(null)
          dispatchMoveFinished(holder)
          runningAnimations.remove(holder)
        },
        onEnd = {
          it.setListener(null)
          dispatchMoveFinished(holder)
          runningAnimations.remove(holder)
        }
      )
    pendingAnimations.add(holder)
    return true
  }

  override fun animateChange(
    oldHolder: RecyclerView.ViewHolder?,
    newHolder: RecyclerView.ViewHolder?,
    fromLeft: Int,
    fromTop: Int,
    toLeft: Int,
    toTop: Int
  ): Boolean {
    if (oldHolder == newHolder) {
      return animateMove(oldHolder, fromLeft, fromTop, toLeft, toTop)
    }

    dispatchChangeFinished(oldHolder, true)
    dispatchChangeFinished(newHolder, false)
    return false
  }

  override fun animateRemove(holder: RecyclerView.ViewHolder?): Boolean {
    dispatchRemoveFinished(holder)
    return false
  }

  override fun isRunning(): Boolean {
    return runningAnimations.isNotEmpty()
  }

  override fun endAnimation(item: RecyclerView.ViewHolder) {
    item.itemView.spring().skipToEnd()
    runningAnimations.remove(item)
    if (!isRunning) {
      dispatchAnimationsFinished()
    }
  }

  override fun endAnimations() {
    pendingAnimations.clear()
    val animations = runningAnimations.toList()
    runningAnimations.clear()
    animations.forEach { it.itemView.spring().skipToEnd() }
    dispatchAnimationsFinished()
  }

  private fun ViewPropertySpringAnimator.reset() = apply {
    defaultDampingRatio(dampingRatio)
    defaultStiffness(stiffness)
  }

  private var View.translationXVelocity: Float
    set(value) {
      setTag(R.id.velocity_translate_x, value)
    }
    get() {
      return getTag(R.id.velocity_translate_x) as? Float ?: 0f
    }

  private var View.translationYVelocity: Float
    set(value) {
      setTag(R.id.velocity_translate_y, value)
    }
    get() {
      return getTag(R.id.velocity_translate_y) as? Float ?: 0f
    }
}
