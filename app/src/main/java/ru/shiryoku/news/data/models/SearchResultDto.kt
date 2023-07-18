package ru.shiryoku.news.data.models

import com.google.gson.annotations.SerializedName
import ru.shiryoku.news.data.models.article.ArticleDto
import ru.shiryoku.news.domain.models.search_result.SearchResult

data class SearchResultDto(
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("articles") val articles: List<ArticleDto>
) {
    fun toDomain(): SearchResult {
        return SearchResult(
            status = status,
            totalResults = totalResults,
            articles = articles.map { article -> article.toDomain() }
        )
    }
}