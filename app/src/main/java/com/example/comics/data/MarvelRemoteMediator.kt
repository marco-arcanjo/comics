package com.example.comics.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.comics.comic.ComicModel
import com.example.comics.comic.MarvelService
import com.example.comics.comic.asComicModel
import retrofit2.HttpException
import java.io.IOException

private const val SERVICE_REQUEST_OFFSET = 0

@OptIn(ExperimentalPagingApi::class)
class MarvelRemoteMediator(
    private val filter: Map<String, String>,
    private val database: ComicDatabase,
    private val service: MarvelService
) : RemoteMediator<Int, ComicModel>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ComicModel>
    ): MediatorResult {

        if (loadType == LoadType.REFRESH) {
            database.withTransaction {
                database.comicDao.deleteComics()
            }
        } else if (loadType == LoadType.PREPEND) {
            return MediatorResult.Success(endOfPaginationReached = true)
        }

        return try {

            val offset = when (loadType) {
                LoadType.REFRESH -> SERVICE_REQUEST_OFFSET
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> database.comicDao.getLastOffset()
            }

            val query = filter + Pair("offset", offset.toString())

            val response = service.getComics(query)
            val total = response.data.total
            val count = response.data.count
            val comics = response.data.asComicModel()

            database.withTransaction {
                with(database) {
                    comicDao.insertComics(comics)
                }
            }

            val endOfPaginationReached = count == 0 || offset + count == total

            MediatorResult.Success(endOfPaginationReached)
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }
}