package com.boa.data.mapper

import com.boa.data.datasource.remote.response.NewsResponse
import com.boa.domain.base.BaseMapper
import com.boa.domain.model.News
import com.boa.domain.util.containsSomething

class NewsResponseToModelMapper : BaseMapper<NewsResponse, News>() {
    override fun map(input: NewsResponse): News = News(
        input.objectID ?: "0L",
        if (input.story_title.containsSomething()) {
            input.story_title
        } else {
            input.title
        } ?: "",
        input.story_url ?: "",
        input.author ?: "",
        if (input.created_at_i < 1000000000000L) {
            input.created_at_i * 1000L
        } else {
            input.created_at_i
        },
        false
    )
}