package ru.shiryoku.news.domain.models.article

data class Article(
    val source: ArticleSource,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val imageUrl: String,
    val publishedAt: String,
    val content: String,
)