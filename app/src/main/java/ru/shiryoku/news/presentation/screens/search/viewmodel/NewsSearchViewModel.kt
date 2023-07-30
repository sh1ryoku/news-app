package ru.shiryoku.news.presentation.screens.search.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.shiryoku.news.domain.models.RequestResult
import ru.shiryoku.news.domain.models.article.Article
import ru.shiryoku.news.domain.repository.NewsRepository
import ru.shiryoku.news.extension.livedata.asLiveData

private const val pageSize = 20

class NewsSearchViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {
    private val category = "general"

    private var page = 1

    private var isLastPage = false

    private var _isLoading = MutableLiveData(false)

    val isLoading: LiveData<Boolean>
        get() = _isLoading.asLiveData()

    private val _articles = MutableLiveData<List<Article>>()

    val articles = _articles.asLiveData()

    fun resetSearch() {
        _articles.value = emptyList()
        page = 1
    }

    fun searchTopHeadlines(){
        viewModelScope.launch {
            _isLoading.value = true
            when (val requestResult =
                newsRepository.searchTopHeadlines(category = category, page = page, pageSize = pageSize)) {
                is RequestResult.Success -> {
                    handleArticlesPage(requestResult.data)
                }

                is RequestResult.Error -> {
                    Log.e(
                        "error",
                        requestResult.exception.message ?: "Error occurred while fetching news"
                    )
                }
            }
            _isLoading.value = false
        }
    }
    fun searchNews(query: String) {
        if (isLastPage) return
        viewModelScope.launch {
            _isLoading.value = true
            when (val requestResult =
                newsRepository.searchNews(query, page = page, pageSize = pageSize)) {
                is RequestResult.Success -> {
                    handleArticlesPage(requestResult.data)
                }

                is RequestResult.Error -> {
                    Log.e(
                        "error",
                        requestResult.exception.message ?: "Error occurred while fetching news"
                    )
                }
            }
            _isLoading.value = false

        }
    }

    private fun handleArticlesPage(articles: List<Article>) {
        if (articles.isEmpty()) {
            isLastPage = true
            return
        }

        isLastPage = false
        val oldList = _articles.value ?: emptyList()
        _articles.value = oldList + articles
        page++
    }
}