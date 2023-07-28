package ru.shiryoku.news.presentation.screens.search.screen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.shiryoku.news.R
import ru.shiryoku.news.databinding.NewsCardBinding
import ru.shiryoku.news.domain.models.article.Article

class NewsSearchAdapter(private val onArticleClick: (Article) -> Unit) :
    ListAdapter<Article, NewsSearchAdapter.NewsSearchHolder>(ArticleDiffItemCallback) {

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
        getItem(position)?.let { article -> holder.bind(article) }
    }

    inner class NewsSearchHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = NewsCardBinding.bind(itemView)
        fun bind(article: Article) {
            with(binding) {
                newsCardTitle.text = article.title
                newsCardAuthorName.text = article.author
                newsCardPublicationDate.text = article.publishedAt
                Glide.with(itemView).load(article.imageUrl).into(newsCardIV)
                itemView.setOnClickListener {
                    onArticleClick(article)
                }
            }
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
