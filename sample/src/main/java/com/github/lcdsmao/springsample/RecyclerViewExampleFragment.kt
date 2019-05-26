package com.github.lcdsmao.springsample

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.dynamicanimation.animation.SpringForce
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.lcdsmao.springsample.databinding.FragmentRecyclerViewExampleBinding
import com.github.lcdsmao.springsample.databinding.ItemViewColorBlockBinding
import kotlin.random.Random

class RecyclerViewExampleFragment : Fragment(R.layout.fragment_recycler_view_example) {

  private lateinit var binding: FragmentRecyclerViewExampleBinding

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    binding = FragmentRecyclerViewExampleBinding.bind(view)
    binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 4)
    val adapter = MyAdapter()
    binding.recyclerView.adapter = adapter.also { it.submitList(COLORS) }
    val animator = SpringMoveItemAnimator().apply {
      dampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
      stiffness = 20f
    }
    binding.recyclerView.itemAnimator = animator
    binding.shuffle.setOnClickListener {
      adapter.submitList(COLORS.partShuffled())
    }
  }

  private fun <E> List<E>.partShuffled(): List<E> {
    val copy = toMutableList()
    val toBeShuffleIndex = (0 until copy.size).filter {
      Random.nextFloat() < 0.2f
    }
    val toBeShuffleElement = toBeShuffleIndex
      .zip(toBeShuffleIndex.shuffled())
      .map {
        it.first to copy[it.second]
      }
    toBeShuffleElement.forEach { (k, v) ->
      copy[k] = v
    }
    return copy
  }

  class MyAdapter : ListAdapter<Int, MyViewHolder>(INT_DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
      val inflater = LayoutInflater.from(parent.context)
      return MyViewHolder(inflater.inflate(R.layout.item_view_color_block, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
      holder.bind(getItem(position))
    }
  }

  class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemViewColorBlockBinding.bind(itemView)
    fun bind(color: Int) {
      binding.cardView.setCardBackgroundColor(color)
      binding.executePendingBindings()
    }
  }
}

private val COLORS = listOf(
  "#FFEBEE",
  "#FFCDD2",
  "#E57373",
  "#C62828",
  "#B71C1C",
  "#FF5252",
  "#FF1744",
  "#F8BBD0",
  "#EC407A",
  "#D81B60",
  "#C2185B",
  "#FF80AB",
  "#F50057",
  "#C51162",
  "#E1BEE7",
  "#BA68C8",
  "#9C27B0",
  "#7B1FA2",
  "#4A148C",
  "#E040FB"
).map {
  Color.parseColor(it)
}

private val INT_DIFF_CALLBACK = object : DiffUtil.ItemCallback<Int>() {
  override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
    return oldItem == newItem
  }

  override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
    return oldItem == newItem
  }
}

