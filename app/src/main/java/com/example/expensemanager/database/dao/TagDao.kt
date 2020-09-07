package com.example.expensemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.expensemanager.database.entity.Tag
import com.example.expensemanager.database.entity.TagWithAmount

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

    @Query("SELECT tag.id, tag.label, icon_path, is_income, " +
            "   (" +
            "       SELECT SUM(amount) FROM trade " +
            "       INNER JOIN Trade_Tag ON trade.id = Trade_Tag.trade_id " +
            "       WHERE Trade_Tag.tag_id = tag.id " +
            "   ) as sum_amount " +
            "FROM tag " +
            "WHERE sum_amount > 0 AND tag.id " +
            "IN(SELECT id FROM Tag WHERE is_income = :is_income) " +
            "GROUP BY tag.id ")
    fun getTagWithSumAmount(is_income: Boolean): LiveData<List<TagWithAmount>>
}