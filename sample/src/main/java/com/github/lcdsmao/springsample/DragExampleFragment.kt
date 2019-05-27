package com.github.lcdsmao.springsample

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.lcdsmao.springx.spring
import com.github.lcdsmao.springsample.databinding.FragmentDragExampleBinding

class DragExampleFragment : Fragment(R.layout.fragment_drag_example) {

  private val viewModel by viewModels<DragSampleViewModel>()
  private lateinit var binding: FragmentDragExampleBinding
  private val onTouchListener = object : View.OnTouchListener {

    private var isDragging: Boolean = false
    private val hitRect = Rect()

    override fun onTouch(v: View, event: MotionEvent): Boolean {
      val image1 = binding.image1
      val image2 = binding.image2
      val image3 = binding.image3
      when (event.actionMasked) {
        MotionEvent.ACTION_DOWN -> {
          image1.getHitRect(hitRect)
          if (hitRect.contains(event.x.toInt(), event.y.toInt())) {
            isDragging = true
          }
        }
        MotionEvent.ACTION_MOVE -> {
          if (isDragging) {
            val x = event.x - image1.width / 2
            val y = event.y - image1.width / 2
            val isDraggingRight = x > image1.x
            image1.x = x
            image1.y = y

            image2.spring()
              .defaultDampingRatio(viewModel.dampingRatio.value!!)
              .defaultStiffness(viewModel.stiffness.value!!)
              .translationX(image1.translationX) {
                onUpdate { _, value, _ ->
                  image3.spring()
                    .defaultDampingRatio(viewModel.dampingRatio.value!!)
                    .defaultStiffness(viewModel.stiffness.value!!)
                    .translationX(value)
                    .start()
                }
              }
              .translationY(image1.translationY) {
                onUpdate { _, value, _ ->
                  image3.spring()
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
