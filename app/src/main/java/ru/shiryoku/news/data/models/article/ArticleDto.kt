package ru.shiryoku.news.data.models.article

import com.google.gson.annotations.SerializedName
import ru.shiryoku.news.domain.models.article.Article

data class ArticleDto(
    @SerializedName("source") val source: ArticleSourceDto,
    @SerializedName("author") val author: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("urlToImage") val imageUrl: String?,
    @SerializedName("publishedAt") val publishedAt: String?,
    @SerializedName("content") val content: String?
) {
    fun toDomain(): Article {
        return Article(
            source = source.toDomain(),
            author = author ?: "",
            title = title ?: "",
            description = description ?: "",
            url = url ?: "",
            imageUrl = imageUrl ?: "",
            publishedAt = publishedAt ?: "",
            content = content ?: "",
        )
    }
}
