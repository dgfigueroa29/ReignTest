package com.boa.data.datasource.remote.response

data class NewsResponse(
    var objectID: String? = "",
    var title: String? = "",
    var story_title: String? = "",
    var author: String? = "",
    var story_url: String? = "",
    var created_at_i: Long = System.currentTimeMillis()
)