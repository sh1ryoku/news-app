package ru.shiryoku.news.domain.repository

import ru.shiryoku.news.data.providers.NewsProvider
import ru.shiryoku.news.domain.models.RequestResult
import ru.shiryoku.news.domain.models.article.Article

class NewsRepository(
    private val newsProvider: NewsProvider,
) {
    suspend fun searchNews(query: String, page: Int, pageSize: Int): RequestResult<List<Article>> {
        try {
            val response = newsProvider.searchNews(
                query = query,
                page = page,
                pageSize = pageSize,
            )
            val searchResult = response.toDomain()

            if (searchResult.status != "ok") {
                return RequestResult.Error(Exception("Ошибка при запросе"))
            }

            return RequestResult.Success(searchResult.articles)
        } catch (error: Exception) {
            return RequestResult.Error(error)
        }
    }

    suspend fun searchTopHeadlines(category: String, page: Int, pageSize: Int): RequestResult<List<Article>> {
        try {
            val response = newsProvider.searchTopHeadlines(
                category = category,
                page = page,
                pageSize = pageSize
            )

            val searchResult = response.toDomain()

            if (searchResult.status != "ok"){
                return RequestResult.Error(Exception("Ошибка при запросе"))
            }

            return RequestResult.Success(searchResult.articles)
        } catch (error: Exception){
            return RequestResult.Error(error)
        }
    }
}