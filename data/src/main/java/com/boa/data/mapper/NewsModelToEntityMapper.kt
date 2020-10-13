package com.boa.data.mapper

import com.boa.data.entity.NewsEntity
import com.boa.domain.base.BaseMapper
import com.boa.domain.model.News

class NewsModelToEntityMapper : BaseMapper<News, NewsEntity>() {
    override fun map(input: News): NewsEntity = NewsEntity(
        input.objectID,
        input.title,
        input.url,
        input.author,
        input.createdAt,
        input.isDeleted
    )
}