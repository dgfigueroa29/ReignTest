package com.boa.data.mapper

import com.boa.data.datasource.remote.response.NewsResponse
import com.boa.domain.base.BaseMapper
import com.boa.domain.model.News
import com.boa.domain.util.containsSomething

class NewsResponseToModelMapper : BaseMapper<NewsResponse, News>() {
    override fun map(input: NewsResponse): News = News(
        input.objectID ?: "", if (input.story_title.containsSomething()) {
            input.story_title
        } else {
            input.title
        } ?: "", input.story_url ?: "", input.author ?: "", input.created_at_i
    )
}