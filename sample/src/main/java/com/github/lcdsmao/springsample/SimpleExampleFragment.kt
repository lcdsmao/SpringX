package com.github.lcdsmao.springsample

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.github.lcdsmao.spring.spring
import com.github.lcdsmao.springsample.databinding.FragmentSimpleExampleBinding

class SimpleExampleFragment : Fragment(R.layout.fragment_simple_example) {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    val binding = FragmentSimpleExampleBinding.bind(view)
    val animation = binding.imageIcon.spring()
      .defaultDampingRatio(0.7f)
      .defaultStiffness(5f)

    binding.buttonLeft.setOnClickListener {
      animation
        .rotation(30f)
        .scaleX(0.5f)
        .scaleY(0.5f)
        .y(binding.placeHolderLeft.y)
        .x(binding.placeHolderLeft.x)
        .start()
    }
    binding.buttonRight.setOnClickListener {
      animation
        .rotation(-30f)
        .scaleX(2f)
        .scaleY(2f)
        .y(binding.placeHolderRight.y)
        .x(binding.placeHolderRight.x)
        .start()
    }
    binding.buttonReset.setOnClickListener {
      animation
        .rotation(0f)
        .scaleX(1f)
        .scaleY(1f)
        .y(binding.imageIcon.top.toFloat()) {
          dampingRatio = 0.8f
          stiffness = 1000f
        }
        .x(binding.imageIcon.left.toFloat()) {
          dampingRatio = 0.5f
          stiffness = 500f
        }
        .start()
    }
  }
}
