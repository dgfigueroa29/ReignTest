package com.boa.data.mapper

import com.boa.data.entity.NewsEntity
import com.boa.domain.base.BaseMapper
import com.boa.domain.model.News

class NewsEntityToModelMapper : BaseMapper<NewsEntity, News>() {
    override fun map(input: NewsEntity): News = News(
        input.objectID,
        input.title,
        input.url,
        input.author,
        input.createdAt,
        input.isDeleted
    )
}