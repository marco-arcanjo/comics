package com.example.comics.comic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.comics.databinding.FragmentComicDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComicDetailFragment : Fragment() {

    // val args by navArgs<ComicDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val viewModel: ComicDetailViewModel by viewModels()
        val binding = FragmentComicDetailBinding.inflate(inflater, container, false)

        viewModel.comic.observe(viewLifecycleOwner) { comicModel ->
            binding.comicModel = comicModel
        }

        return binding.root
    }

}