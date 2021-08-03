package com.example.comics.comic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.example.comics.databinding.FragmentComicBinding

import dagger.hilt.android.AndroidEntryPoint

import javax.inject.Inject

@AndroidEntryPoint
class ComicFragment : Fragment() {

    private val viewModel: ComicViewModel by viewModels()

    @Inject
    lateinit var comicAdapter: ComicAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val dataBinding = FragmentComicBinding.inflate(inflater)

        dataBinding.comicListAdapter.adapter = comicAdapter.withLoadStateHeaderAndFooter(
            header = ComicLoadStateAdapter(comicAdapter::retry),
            footer = ComicLoadStateAdapter(comicAdapter::retry)
        )

        dataBinding.retryButton.setOnClickListener { comicAdapter.retry() }

        comicAdapter.addLoadStateListener {

            with(dataBinding) {
                progressBar.isVisible = it.refresh is LoadState.Loading
                textLoadStateError.isVisible = it.refresh is LoadState.Error
                retryButton.isVisible = it.refresh is LoadState.Error
                comicListAdapter.isVisible =
                    it.refresh is LoadState.NotLoading && comicAdapter.itemCount > 0
            }

        }

        viewModel.comics.observe(viewLifecycleOwner) {
            comicAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        return dataBinding.root
    }

}