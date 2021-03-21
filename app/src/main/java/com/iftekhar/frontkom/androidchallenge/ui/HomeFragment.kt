package com.iftekhar.frontkom.androidchallenge.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.iftekhar.frontkom.androidchallenge.R
import com.iftekhar.frontkom.androidchallenge.adapter.NewsAdapter
import com.iftekhar.frontkom.androidchallenge.databinding.FragmentHomeBinding
import com.iftekhar.frontkom.androidchallenge.model.Webservice
import kotlinx.coroutines.launch
import java.util.*

class HomeFragment : Fragment(R.layout.fragment_home) {
    lateinit var binding: FragmentHomeBinding
    lateinit var adapter: NewsAdapter
    val language = Locale.getDefault().language
    val apiKey = "e9f236f020e5427aa5f6b1ff104e955e"
    val pageSize = 50
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        initResyclerView()
        binding.swipeRefreshLayout.setOnRefreshListener { loadData() }
        loadData()
    }

    private fun loadData() = lifecycleScope.launch {
        binding.swipeRefreshLayout.isRefreshing = true
        kotlin.runCatching {
            Webservice.newsApi.getHeadlines(language, apiKey, pageSize)
        }.onSuccess {
            binding.swipeRefreshLayout.isRefreshing = false
            adapter.update(it.articles)
        }.onFailure {
            binding.swipeRefreshLayout.isRefreshing = false
            showSnack(it.message)
        }

    }

    private fun initResyclerView() {
        adapter = NewsAdapter()
        val gridLayoutManager =
            GridLayoutManager(requireContext(), resources.getInteger(R.integer.news_column))
        binding.recyclerViewNews.setLayoutManager(gridLayoutManager)
        binding.recyclerViewNews.adapter = adapter
        adapter.setOnItemClickListener {
            NewsDetailActivity.start(requireContext(), it)
        }
    }

    fun showSnack(message: String?) {
        Snackbar.make(
            requireActivity().findViewById(android.R.id.content),
            message ?: "Unknown error try again",
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction("Retry", { loadData() }).show()
    }
}