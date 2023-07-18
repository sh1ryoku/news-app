package ru.shiryoku.news.presentation.screens.search.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.shiryoku.news.domain.models.RequestResult
import ru.shiryoku.news.domain.models.article.Article
import ru.shiryoku.news.domain.repository.NewsRepository
import ru.shiryoku.news.extension.livedata.asLiveData

private const val pageSize = 10

class NewsSearchViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    init {
        searchNews(query = "sport")
    }

    private val _news = MutableLiveData<List<Article>>()

    val news = _news.asLiveData()

    fun searchNews(query: String) {
        viewModelScope.launch {
            val result = newsRepository.searchNews(query = query, page = 1, pageSize = pageSize) // TODO тут нужно добавить пагинацию при пролистывании
            if (result is RequestResult.Success) {
                val articles = result.data
                _news.value = articles
                return@launch
            }
            if (result is RequestResult.Error) {
                //handle error
            }
        }
    }
}