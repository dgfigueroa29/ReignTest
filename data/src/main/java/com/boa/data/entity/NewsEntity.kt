package com.boa.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NewsEntity(
    @PrimaryKey
    var objectID: String = "",
    var title: String = "",
    var url: String = "",
    var author: String = "",
    var createdAt: Long = System.currentTimeMillis(),
    var isDeleted: Boolean = false
)