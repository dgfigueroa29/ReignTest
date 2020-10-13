package com.boa.data.mapper

import com.boa.data.datasource.remote.response.NewsResponse
import com.boa.data.entity.NewsEntity
import com.boa.domain.base.BaseMapper
import com.boa.domain.util.containsSomething

class NewsResponseToEntityMapper : BaseMapper<NewsResponse, NewsEntity>() {
    override fun map(input: NewsResponse): NewsEntity = NewsEntity(
        input.objectID ?: "",
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
        }
    )
}