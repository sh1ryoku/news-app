package ru.shiryoku.news.presentation.screens.search.screen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.shiryoku.news.R
import ru.shiryoku.news.databinding.NewsCardBinding
import ru.shiryoku.news.domain.models.article.Article
import ru.shiryoku.news.presentation.screens.search.screen.NewsSearchFragment

class NewsSearchAdapter :
    PagingDataAdapter<Article, NewsSearchAdapter.NewsSearchHolder>(
        ArticleDiffItemCallback
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): NewsSearchHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.news_card,
            parent, false
        )
        return NewsSearchHolder(itemView)
    }

    override fun onBindViewHolder(holder: NewsSearchHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class NewsSearchHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = NewsCardBinding.bind(itemView)
        fun bind(article: Article?) {
            with(binding) {
                newsCardTitle.text = article?.title
                newsCardAuthorName.text = article?.author
                newsCardPublicationDate.text = article?.publishedAt
                Glide.with(itemView).load(article?.imageUrl).into(newsCardIV)
            }
        }
    }

    private object ArticleDiffItemCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.title == newItem.title && oldItem.url == newItem.url
        }
    }

}