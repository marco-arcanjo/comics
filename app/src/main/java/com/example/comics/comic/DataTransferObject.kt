package com.example.comics.comic

data class ComicDataWrapper(
    val copyright: String,
    val attributionText: String,
    val attributionHtml: String,
    val data: ComicDataContainer
)

data class ComicDataContainer(
    val offset: Int,
    val total: Int,
    val count: Int,
    val results: List<Comic>
)

data class Comic(
    val id: Int,
    val title: String,
    val issueNumber: Int,
    val thumbnail: Image
)

data class Image(
    val path: String,
    val extension: String
)

fun ComicDataContainer.asComicModel() : List<ComicModel> {
    return results.map { comic ->
        ComicModel(
            id = comic.id,
            title = comic.title,
            issueNumber = comic.issueNumber,
            thumbnail = "${comic.thumbnail.path}/portrait_medium.${comic.thumbnail.extension}"
        )
    }
}