package com.example.comics.comic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.comics.databinding.ItemComicListBinding
import javax.inject.Inject

class ComicAdapter @Inject constructor(private val callback: ComicClickListener) :
    PagingDataAdapter<ComicModel, ComicAdapter.ComicViewHolder>(
        DiffCallback
    ) {

    class ComicViewHolder(val binding: ItemComicListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ComicViewHolder(ItemComicListBinding.inflate(layoutInflater, parent, false))
            .also {
                it.binding.clickListener = callback
            }
    }

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        with(holder.binding) {
            comicModel = getItem(position)
            executePendingBindings()
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<ComicModel>() {

        override fun areItemsTheSame(oldItem: ComicModel, newItem: ComicModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ComicModel, newItem: ComicModel): Boolean {
            return oldItem == newItem
        }

    }

}

class ComicClickListener @Inject constructor(
    val block: @JvmSuppressWildcards (comicId: Int) -> Unit
) {
    fun onClick(comicId: Int) = block(comicId)
}