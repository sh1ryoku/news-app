package ru.shiryoku.news.presentation.screens.search.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import ru.shiryoku.news.domain.repository.NewsRepository
import ru.shiryoku.news.paging.PagingSource

private const val pageSize = 10

class NewsSearchViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {
    private val currentQuery = MutableLiveData<String>()

    val news = currentQuery.switchMap { query ->
        Pager(PagingConfig(pageSize)) {
            PagingSource(newsRepository, query)
        }.liveData
            .cachedIn(viewModelScope)
    }

    fun setCurrentQuery(query: String) {
        currentQuery.postValue(query)
    }
}