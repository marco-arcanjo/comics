package com.example.comics.comic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.comics.databinding.ItemComicLoadStateBinding

class ComicLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<ComicLoadStateAdapter.ComicLoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ComicLoadStateViewHolder {
        return ComicLoadStateViewHolder.from(parent, retry)
    }

    override fun onBindViewHolder(holder: ComicLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    class ComicLoadStateViewHolder private constructor(
        private val binding: ItemComicLoadStateBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup, retry: () -> Unit): ComicLoadStateViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemComicLoadStateBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
                return ComicLoadStateViewHolder(binding).also {
                    it.binding.retryButton.setOnClickListener { retry.invoke() }
                }
            }
        }

        fun bind(loadState: LoadState) {
            with(binding) {
                loadingBar.isVisible = loadState is LoadState.Loading
                textLoadStateError.isVisible = loadState is LoadState.Error
                retryButton.isVisible = loadState is LoadState.Error
            }
        }

    }

}