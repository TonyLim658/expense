package com.example.expensemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.expensemanager.database.entity.Tag

@Dao
interface TagDao {
    @Query("SELECT * from Tag ORDER BY label ASC")
    fun getAlphabetizedTags(): LiveData<List<Tag>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(tag: Tag)

    @Query("DELETE FROM Tag")
    suspend fun deleteAll()

    @Query("DELETE FROM Tag WHERE id = :tagId")
    suspend fun deleteById(tagId: Long)

    @Query("SELECT * FROM Tag WHERE is_income = :is_income")
    fun getTagByType(is_income: Boolean): LiveData<List<Tag>>
}