package com.example.comics.comic

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ComicModel(

    @PrimaryKey
    val id: Int,
    val title: String,
    val issueNumber: Int,
    val thumbnail: String
)