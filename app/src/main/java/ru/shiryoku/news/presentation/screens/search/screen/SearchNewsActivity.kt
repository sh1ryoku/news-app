package ru.shiryoku.news.presentation.screens.search.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.shiryoku.news.databinding.ActivitySearchNewsBinding
import ru.shiryoku.news.presentation.screens.search.viewmodel.NewsSearchViewModel

class SearchNewsActivity : AppCompatActivity() {
    private val viewModel by viewModel<NewsSearchViewModel>()
    private lateinit var binding: ActivitySearchNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchNewsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setUpUi()
        setUpObserve()
    }

    private fun setUpUi() {
        binding.refreshButton.setOnClickListener {
            viewModel.searchNews(query = "sport")
        }
    }

    private fun setUpObserve() {
        println("initialized sub")
        viewModel.news.observe(this) { news ->
            println(news.size)
        } // тут можно уже отображать полученный список новостей
    }
}