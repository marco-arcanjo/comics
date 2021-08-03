package com.example.comics.comic

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.comics.data.ComicDatabase
import com.example.comics.data.MarvelRemoteMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ComicRepository @Inject constructor(
    private val service: MarvelService,
    private val database: ComicDatabase
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getComics(filter: Map<String, String>): LiveData<PagingData<ComicModel>> {

        return Pager(
            config = PagingConfig(
                pageSize = 12,
                enablePlaceholders = false
            ),
            remoteMediator = MarvelRemoteMediator(filter, database, service),
            pagingSourceFactory = { database.comicDao.getComics() }

            // pagingSourceFactory = { MarvelPagingSource(service, filter) }
        ).liveData
    }

    suspend fun getComicById(comicId: Int): ComicModel {
        return withContext(Dispatchers.IO) {
            database.comicDao.getComicById(comicId)
        }
    }
}