package ru.shiryoku.news.presentation.screens.detail_page.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import ru.shiryoku.news.databinding.FragmentDetailPageBinding
import ru.shiryoku.news.domain.models.article.Article
import ru.shiryoku.news.presentation.screens.search.screen.NewsSearchFragment.Companion.ARTICLE_KEY

class DetailPageFragment : Fragment() {

    private lateinit var binding: FragmentDetailPageBinding
    private var currentNews: Article? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailPageBinding.inflate(layoutInflater)
        val args = arguments
        currentNews = args?.getSerializable(ARTICLE_KEY) as? Article
        setUpUi()
        return binding.root
    }

    private fun setUpUi() {
        binding.blurView.setBlur(context, binding.blurView)
        binding.newsDetailCardPublicationDate.text = currentNews?.publishedAt
        binding.newsDetailCardTitle.text = currentNews?.title
        binding.newsDetailCardAuthor.text = currentNews?.author
        binding.newsContentTV.text = currentNews?.content
        context?.let { Glide.with(it).load(currentNews?.imageUrl).into(binding.newsIV) }
    }
}