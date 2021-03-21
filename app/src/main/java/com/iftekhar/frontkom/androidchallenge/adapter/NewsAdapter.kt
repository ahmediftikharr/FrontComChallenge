package com.iftekhar.frontkom.androidchallenge.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iftekhar.frontkom.androidchallenge.R
import com.iftekhar.frontkom.androidchallenge.databinding.ArticleItemLayoutBinding
import com.iftekhar.frontkom.androidchallenge.model.Article
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter : RecyclerView.Adapter<NewsHolder>() {
    var models = listOf<Article>()

    private var itemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val binding =
            ArticleItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        holder.bind(models[position])
        holder.itemView.setOnClickListener {
            itemClickListener?.invoke(models[holder.adapterPosition])
        }
    }

    override fun getItemCount() = models.size
    fun update(list: List<Article>) {
        this.models = list
        notifyDataSetChanged()
    }
}

class NewsHolder(private val binddng: ArticleItemLayoutBinding) :
    RecyclerView.ViewHolder(binddng.root) {
    fun bind(model: Article) {
        binddng.title.text = model.title
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val simpleDateFormat = SimpleDateFormat("dd MMM HH:mm", Locale.getDefault())
        var parse: Date? = try {
            formatter.parse(model.publishedAt)
        } catch (e: Exception) {
            null
        }
        if (parse == null) {
            parse = Date()
        }
        binddng.subtitle.setText(simpleDateFormat.format(parse))
        Glide.with(binddng.root.context).load(model.urlToImage)
            .placeholder(R.drawable.placeholder).into(binddng.image)
    }
}