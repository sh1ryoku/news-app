package ru.shiryoku.news.presentation.screens.search.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.shiryoku.news.databinding.FragmentNewsSearchBinding
import ru.shiryoku.news.extension.edittext.textChanges
import ru.shiryoku.news.presentation.screens.search.screen.adapter.NewsSearchAdapter
import ru.shiryoku.news.presentation.screens.search.viewmodel.NewsSearchViewModel

const val SEARCH_DEBOUNCE = 300L

class NewsSearchFragment : Fragment() {

    private val viewModel by viewModel<NewsSearchViewModel>()
    private lateinit var binding: FragmentNewsSearchBinding
    private val adapter by lazy() {
        NewsSearchAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsSearchBinding.inflate(layoutInflater)

        setUpUi()
        setUpObserve()
        return binding.root
    }

    @OptIn(FlowPreview::class)
    private fun setUpUi() {
        binding.rvSearch.adapter = adapter
        binding.searchBar.textChanges().debounce(SEARCH_DEBOUNCE)
            .onEach { text ->
                if (text.isNullOrEmpty()) return@onEach
                viewModel.setCurrentQuery(query = text.toString())
            }
            .launchIn(lifecycleScope)
    }

    private fun setUpObserve() {
        viewModel.news.observe(viewLifecycleOwner) { news ->
            lifecycleScope.launch {
                adapter.submitData(news)
            }
        }
    }
}