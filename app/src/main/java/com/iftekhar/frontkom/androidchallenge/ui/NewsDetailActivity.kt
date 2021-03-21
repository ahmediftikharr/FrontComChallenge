package com.iftekhar.frontkom.androidchallenge.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat.IntentBuilder
import com.bumptech.glide.Glide
import com.iftekhar.frontkom.androidchallenge.R
import com.iftekhar.frontkom.androidchallenge.databinding.ActivityNewsDetailBinding
import com.iftekhar.frontkom.androidchallenge.model.Article
import java.lang.IllegalArgumentException

class NewsDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityNewsDetailBinding

    companion object {
        var article: Article? = null

        fun start(context: Context, news: Article) {
            val starter = Intent(context, NewsDetailActivity::class.java)
            article = news
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (article == null) throw IllegalArgumentException("Pleae set News article")
        supportActionBar?.setTitle(article!!.title)
        updateUi()
    }

    private fun updateUi() {
        binding.subtitle.text = "by ${article!!.author}"
        if (Build.VERSION.SDK_INT >= 24) {
            if (article!!.content.isNullOrEmpty()) {
                binding.text.text = article!!.description
            } else {
                binding.text.text = Html.fromHtml(article!!.content, 0)
            }

        } else {
            if (article!!.content.isNullOrEmpty()) {
                binding.text.text = article!!.description
            } else {
                binding.text.text = Html.fromHtml(article!!.content)
            }
        }
        Glide.with(this).load(article!!.urlToImage)
            .placeholder(R.drawable.placeholder).into(binding.image)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.news_details_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId

        if (itemId == R.id.action_share) {
            val intentBuilder = IntentBuilder.from(this).setType("text/plain")
                .setChooserTitle("Share")
            intentBuilder.setText(article!!.url).startChooser()
        } else if (itemId == R.id.action_web) {
            val browserIntent = Intent("android.intent.action.VIEW")
            browserIntent.data = Uri.parse(article!!.url)
            startActivity(browserIntent)
        }
        return super.onOptionsItemSelected(item)
    }
}