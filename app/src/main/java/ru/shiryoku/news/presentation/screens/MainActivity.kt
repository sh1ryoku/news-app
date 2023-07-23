package ru.shiryoku.news.presentation.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.shiryoku.news.databinding.ActivitySearchNewsBinding
import ru.shiryoku.news.presentation.screens.search.viewmodel.NewsSearchViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchNewsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}