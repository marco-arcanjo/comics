package com.example.comics.comic

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComicDetailViewModel @Inject constructor(
    repository: ComicRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _comic = MutableLiveData<ComicModel>()
    val comic: LiveData<ComicModel>
        get() = _comic

    init {
        viewModelScope.launch {
            _comic.value = repository.getComicById(savedStateHandle.get("comicId")!!)
        }
    }
}