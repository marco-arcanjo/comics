package com.example.comics.data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.comics.comic.ComicModel

@Dao
interface ComicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComics(comic: List<ComicModel>)

    @Query("SELECT * FROM ComicModel ORDER BY title")
    fun getComics() : PagingSource<Int, ComicModel>

    @Query("SELECT COUNT(*) FROM ComicModel")
    suspend fun getLastOffset(): Int

    @Query("DELETE FROM ComicModel")
    suspend fun deleteComics()

    @Query("SELECT * FROM ComicModel WHERE id = :comicId")
    fun getComicById(comicId: Int): ComicModel
}