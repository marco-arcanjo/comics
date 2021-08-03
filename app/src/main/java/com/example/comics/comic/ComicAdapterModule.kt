package com.example.comics.comic

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@InstallIn(FragmentComponent::class)
@Module
object ComicAdapterModule {

    @Provides
    fun provideComicOnClick(fragment: Fragment): (comicId: Int) -> Unit {
        return { comicId ->
            NavHostFragment.findNavController(fragment)
                .navigate(ComicFragmentDirections.actionComicFragmentToComicDetailFragment(comicId))
        }
    }
}