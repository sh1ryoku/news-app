package ru.shiryoku.news.domain.models.search_result

import ru.shiryoku.news.domain.models.article.Article

data class SearchResult(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)
