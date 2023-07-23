package ru.shiryoku.news.data.providers

import retrofit2.http.GET
import retrofit2.http.Query
import ru.shiryoku.news.data.models.SearchResultDto

interface NewsProvider {
    @GET("everything")
    suspend fun searchNews(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): SearchResultDto
}