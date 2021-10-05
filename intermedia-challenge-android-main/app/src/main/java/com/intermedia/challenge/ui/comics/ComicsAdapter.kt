package com.intermedia.challenge.ui.comics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.intermedia.challenge.R
import com.intermedia.challenge.data.models.Appearance
import com.intermedia.challenge.databinding.ViewComicItemBinding

class ComicsAdapter : com.intermedia.challenge.ui.base.BaseAdapter<Appearance, ComicsAdapter.ComicsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicsViewHolder =
        ComicsViewHolder(
            ViewComicItemBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.view_comic_item,
                    parent,
                    false
                )
            ),
        )

    override fun onBindViewHolder(holder: ComicsViewHolder, position: Int) {
        holder.bind(list[position])
    }

    class ComicsViewHolder(
        private val binding: ViewComicItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Appearance) = with(itemView) {
            binding.comic = item
        }
    }
}