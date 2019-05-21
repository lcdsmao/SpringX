package com.github.lcdsmao.springsample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.dynamicanimation.animation.SpringForce
import androidx.fragment.app.Fragment
import com.github.lcdsmao.spring.spring
import com.github.lcdsmao.springsample.databinding.FragmentSimpleSampleBinding

class SimpleSampleFragment : Fragment() {

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val binding = FragmentSimpleSampleBinding.inflate(inflater, container, false)

    binding.buttonLeft.setOnClickListener {
      binding.imageIcon.spring(
        SpringForce.DAMPING_RATIO_HIGH_BOUNCY,
        SpringForce.STIFFNESS_LOW
      ).rotation(30f)
        .scaleX(2f)
        .scaleY(2f)
        .y(binding.placeHolderLeft.y) {
          dampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
        }
        .x(binding.placeHolderLeft.x)
        .start()
    }
    binding.buttonRight.setOnClickListener {
      binding.imageIcon.spring(
        SpringForce.DAMPING_RATIO_LOW_BOUNCY,
        SpringForce.STIFFNESS_HIGH
      ).rotation(-30f)
        .scaleX(1f)
        .scaleY(2f)
        .y(binding.placeHolderRight.y)
        .x(binding.placeHolderRight.x)
        .start()
    }
    binding.buttonReset.setOnClickListener {
      binding.imageIcon.spring()
        .rotation(0f)
        .scaleX(1f)
        .scaleY(1f)
        .y(binding.imageIcon.top.toFloat())
        .x(binding.imageIcon.left.toFloat())
        .start()
    }

    return binding.root
  }
}
