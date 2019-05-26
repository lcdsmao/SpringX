package com.github.lcdsmao.springsample

import android.widget.SeekBar
import androidx.databinding.BindingAdapter

@BindingAdapter("onProgressChanged")
fun SeekBar.setProgressChangedListener(listener: OnProgressChangedListener) {
  setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
      listener.onProgressChanged(progress)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }
  })
}

@BindingAdapter("progress")
fun SeekBar.setProgressIfNotEqual(progress: Int) {
  if (this.progress != progress) {
    this.progress = progress
  }
}

interface OnProgressChangedListener {
  fun onProgressChanged(progress: Int)
}
