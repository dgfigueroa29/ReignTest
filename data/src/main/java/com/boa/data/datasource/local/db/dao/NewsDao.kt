package com.boa.data.datasource.local.db.dao

import androidx.room.*
import com.boa.data.entity.NewsEntity

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: NewsEntity)

    @Update
    fun update(entity: NewsEntity)

    @Delete
    fun delete(entity: NewsEntity)

    @Query("SELECT * FROM NewsEntity WHERE isDeleted = 0")
    fun getAll(): List<NewsEntity>
}