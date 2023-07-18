package ru.shiryoku.news.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.shiryoku.news.data.network_clients.BaseNetworkClient
import ru.shiryoku.news.data.providers.NewsProvider
import ru.shiryoku.news.domain.repository.NewsRepository
import ru.shiryoku.news.presentation.screens.search.viewmodel.NewsSearchViewModel

val dataModule = module {
    single { BaseNetworkClient().create(NewsProvider::class.java) }
    single { NewsRepository(newsProvider = get()) }
    viewModel { NewsSearchViewModel(newsRepository = get()) }
}