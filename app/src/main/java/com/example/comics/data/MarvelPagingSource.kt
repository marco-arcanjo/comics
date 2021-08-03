package com.example.comics.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.comics.comic.ComicModel
import com.example.comics.comic.MarvelService
import com.example.comics.comic.asComicModel
import retrofit2.HttpException
import java.io.IOException

private const val SERVICE_REQUEST_OFFSET = 0

class MarvelPagingSource(
    private val service: MarvelService,
    private val filter: Map<String, String>
) : PagingSource<Int, ComicModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ComicModel> {

        val offset = params.key ?: SERVICE_REQUEST_OFFSET

        return try {

            val query = filter + Pair("offset", offset.toString())

            val response = service.getComics(query)
            val total = response.data.total
            val count = response.data.count
            val comics = response.data.asComicModel()
            val prevKey = if (offset == SERVICE_REQUEST_OFFSET) null else offset - count
            val nextKey = if (count == 0 || offset + count == total) null else offset + count

            LoadResult.Page(comics, prevKey, nextKey)
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, ComicModel>): Int? {
        return null
    }
}