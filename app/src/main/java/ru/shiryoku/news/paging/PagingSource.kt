package ru.shiryoku.news.paging

import ru.shiryoku.news.domain.models.article.Article
import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import ru.shiryoku.news.domain.models.RequestResult
import ru.shiryoku.news.domain.repository.NewsRepository


class PagingSource(
    private val newsRepository: NewsRepository,
    private val query: String
) : PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        if (query.isEmpty()) {
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }

        val page: Int = params.key ?: 1
        val pageSize: Int = params.loadSize

        val response = newsRepository.searchNews(query, page, pageSize)
        return when (response) {
            is RequestResult.Success -> {
                val nextKey = if (response.data.isEmpty()) null else page + 1
                val prevKey = if (page == 1) null else page + 1
                LoadResult.Page(response.data, prevKey, nextKey)
            }

            is RequestResult.Error -> {
                LoadResult.Error(response.exception)
            }
        }
    }
}
