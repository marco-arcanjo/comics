package com.example.comics.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.comics.comic.ComicModel

@Database(entities = [ComicModel::class], version = 1, exportSchema = false)
abstract class ComicDatabase : RoomDatabase() {
    abstract val comicDao: ComicDao
}