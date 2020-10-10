package com.boa.domain.model

data class News(
    var objectID: String = "",
    var title: String = "",
    var url: String = "",
    var author: String = "",
    var createdAt: Long = System.currentTimeMillis(),
    var isDeleted: Boolean = false
)