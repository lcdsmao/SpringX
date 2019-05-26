package com.github.lcdsmao.springsample

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.lcdsmao.spring.spring
import com.github.lcdsmao.springsample.databinding.FragmentDragExampleBinding

class DragExampleFragment : Fragment(R.layout.fragment_drag_example) {

  private val viewModel by viewModels<DragSampleViewModel>()
  private lateinit var binding: FragmentDragExampleBinding
  private val onTouchListener = object : View.OnTouchListener {

    private var isDragging: Boolean = false
    private val hitRect = Rect()

    override fun onTouch(v: View, event: MotionEvent): Boolean {
      val circle1 = binding.imageCircle1
      val circle2 = binding.imageCircle2
      val circle3 = binding.imageCircle3
      when (event.actionMasked) {
        MotionEvent.ACTION_DOWN -> {
          circle1.getHitRect(hitRect)
          if (hitRect.contains(event.x.toInt(), event.y.toInt())) {
            isDragging = true
          }
        }
        MotionEvent.ACTION_MOVE -> {
          if (isDragging) {
            val x = event.x - circle1.width / 2
            val y = event.y - circle1.width / 2
            val isDraggingRight = x > circle1.x
            circle1.x = x
            circle1.y = y

            circle2.spring()
              .defaultDampingRatio(viewModel.dampingRatio.value!!)
              .defaultStiffness(viewModel.stiffness.value!!)
              .translationX(circle1.translationX) {
                onUpdate { _, value, _ ->
                  circle3.spring()
                    .defaultDampingRatio(viewModel.dampingRatio.value!!)
                    .defaultStiffness(viewModel.stiffness.value!!)
                    .translationX(value)
                    .start()
                }
              }
              .translationY(circle1.translationY) {
                onUpdate { _, value, _ ->
                  circle3.spring()
                    .defaultDampingRatio(viewModel.dampingRatio.value!!)
                    .defaultStiffness(viewModel.stiffness.value!!)
                    .translationY(value)
                    .start()
                }
              }
              .rotation(0f) {
                startValue = if (isDraggingRight) {
                  100f
                } else {
                  -100f
                }
                minValue = -120f
                maxValue = 120f
              }
              .start()
          }
        }
        MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
          v.performClick()
          isDragging = false
        }
      }
      return true
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    binding = FragmentDragExampleBinding.bind(view)
    binding.lifecycleOwner = viewLifecycleOwner
    binding.viewModel = viewModel
    binding.root.setOnTouchListener(onTouchListener)
  }
}
