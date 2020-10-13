package com.boa.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NewsEntity(
    @PrimaryKey
    var objectID: Long = 0L,
    var title: String = "",
    var url: String = "",
    var author: String = "",
    var createdAt: Long = System.currentTimeMillis(),
    var isDeleted: Int = 0
)