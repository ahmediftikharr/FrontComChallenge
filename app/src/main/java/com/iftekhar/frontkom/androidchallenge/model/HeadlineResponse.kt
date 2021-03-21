package com.iftekhar.frontkom.androidchallenge.model

data class HeadlineResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)