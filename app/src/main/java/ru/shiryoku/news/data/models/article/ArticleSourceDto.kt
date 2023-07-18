package ru.shiryoku.news.data.models.article

import com.google.gson.annotations.SerializedName
import ru.shiryoku.news.domain.models.article.ArticleSource

data class ArticleSourceDto(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String?
) {
    fun toDomain(): ArticleSource {
        return ArticleSource(id = id ?: "", name = name ?: "")
    }
}
