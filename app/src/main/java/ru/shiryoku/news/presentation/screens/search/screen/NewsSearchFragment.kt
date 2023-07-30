package ru.shiryoku.news.presentation.screens.search.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.shiryoku.news.R
import ru.shiryoku.news.databinding.FragmentNewsSearchBinding
import ru.shiryoku.news.domain.models.article.Article
import ru.shiryoku.news.extension.edittext.textChanges
import ru.shiryoku.news.presentation.screens.search.screen.adapter.NewsSearchAdapter
import ru.shiryoku.news.presentation.screens.search.viewmodel.NewsSearchViewModel

const val SEARCH_DEBOUNCE = 300L

class NewsSearchFragment : Fragment() {

    private val viewModel by viewModel<NewsSearchViewModel>()
    private lateinit var binding: FragmentNewsSearchBinding
    private val adapter by lazy {
        NewsSearchAdapter(
            onArticleClick = { article -> onArticleClick(article = article) }
        )
    }
    private var navController: NavController? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsSearchBinding.inflate(layoutInflater)
        navController = findNavController(requireActivity(), R.id.nav_host_fragment)
        setUpUi()
        setUpObserve()
        viewModel.searchTopHeadlines()
        return binding.root
    }

    @OptIn(FlowPreview::class)
    private fun setUpUi() {
        binding.rvSearch.adapter = adapter
        binding.rvSearch.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                if (totalItemCount == 0) return

                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                val percentageScrolled = 100 * lastVisibleItemPosition / totalItemCount

                val isLoading = viewModel.isLoading.value

                if (isLoading == true || percentageScrolled < 70) return

                val query = binding.searchBar.text.toString()
                if (query.isEmpty()) viewModel.searchTopHeadlines()
                viewModel.searchNews(query)
            }
        }
        )
        binding.swipeRefresh.setOnRefreshListener {
            val query = binding.searchBar.text.toString()
            if (query.isEmpty()) {
                viewModel.searchTopHeadlines()
                return@setOnRefreshListener
            }
            viewModel.searchNews(query)
        }
    }

    @OptIn(FlowPreview::class)
    override fun onResume() {
        super.onResume()
        binding.searchBar.textChanges().debounce(SEARCH_DEBOUNCE)
            .onEach { text ->
                if (text.isNullOrEmpty()) viewModel.searchTopHeadlines()
                viewModel.resetSearch()
                viewModel.searchNews(text.toString())
            }
            .launchIn(lifecycleScope)
    }

    private fun setUpObserve() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.swipeRefresh.isRefreshing = isLoading
        }
        viewModel.articles.observe(viewLifecycleOwner) { articles ->
            lifecycleScope.launch {
                adapter.submitList(articles)
            }
        }
    }

    private fun onArticleClick(article: Article?) {
        val bundle = Bundle().apply {
            putSerializable(ARTICLE_KEY, article)
        }
        navController?.navigate(
            R.id.action_newsSearchFragment_to_detailPageFragment,
            args = bundle
        )
    }

    companion object {
        const val ARTICLE_KEY = "article key"
    }
}