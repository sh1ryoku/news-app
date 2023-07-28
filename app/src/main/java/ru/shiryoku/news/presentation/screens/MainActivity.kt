package ru.shiryoku.news.presentation.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.shiryoku.news.databinding.ActivitySearchNewsBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchNewsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}
