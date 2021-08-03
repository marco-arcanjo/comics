package com.example.comics.comic

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ComicViewModel @Inject constructor(
    repository: ComicRepository
) : ViewModel() {

    val comics: LiveData<PagingData<ComicModel>> =
        repository.getComics(comicsFilter()).cachedIn(viewModelScope)

    private fun comicsFilter(): Map<String, String> {
        val timestamp = System.currentTimeMillis().toString()
        return mapOf(
            "format" to "comic",
            "formatType" to "comic",
            "noVariants" to "true",
            "dateDescriptor" to "thisWeek",
            "orderBy" to "title",
            "limit" to "12",
            "apikey" to AuthorizationUtils.PUBLIC_KEY,
            "ts" to timestamp,
            "hash" to AuthorizationUtils.getHashParameter(timestamp)
        )
    }
}