package com.example.comics.comic

import com.example.comics.BuildConfig
import org.apache.commons.codec.digest.DigestUtils
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface MarvelService {
    @GET("/v1/public/comics")
    suspend fun getComics(@QueryMap filters: Map<String, String>): ComicDataWrapper
}

object AuthorizationUtils {

    const val PUBLIC_KEY = BuildConfig.PUBLIC_KEY
    private const val PRIVATE_KEY = BuildConfig.PRIVATE_KEY

    fun getHashParameter(ts: String): String {
        return DigestUtils.md5Hex(ts + PRIVATE_KEY + PUBLIC_KEY)
    }

}



