package com.boa.data.datasource.local.db.dao

import androidx.room.*
import com.boa.data.entity.NewsEntity

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: NewsEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(entity: NewsEntity)

    @Delete
    fun delete(entity: NewsEntity)

    @Query("SELECT * FROM NewsEntity")
    fun getAll(): List<NewsEntity>

    @Query("SELECT * FROM NewsEntity WHERE objectID = :objectId")
    fun getById(objectId: Long): NewsEntity
}