package com.example.expensemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.expensemanager.database.entity.Tag
import com.example.expensemanager.database.entity.Trade
import com.example.expensemanager.database.entity.TradeWithTag

@Dao
interface TradeDao {
    @Query("SELECT * from Trade")
    fun getAll(): LiveData<List<Trade>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(tag: Trade)

    @Query("DELETE FROM Trade")
    suspend fun deleteAll()

    @Query("DELETE FROM Trade WHERE id = :tradeId")
    suspend fun deleteById(tradeId: Long)

    @Query("SELECT * FROM Trade WHERE tag_id " +
            "IN(SELECT id FROM Tag WHERE is_income = :is_income)")
    fun getTradesByType(is_income: Boolean): LiveData<List<Trade>>

    @Query("SELECT Trade.id, Trade.amount, Trade.tag_id, Trade.date," +
            "Tag.label as tag_label, Tag.is_income from Trade " +
            "INNER JOIN Tag ON Trade.tag_id = Tag.id ")
    fun getTradesWithTag(): LiveData<List<TradeWithTag>>

    @Query("SELECT Trade.id, Trade.amount, Trade.tag_id, Trade.date," +
            "Tag.label as tag_label, Tag.is_income from Trade " +
            "INNER JOIN Tag ON Trade.tag_id = Tag.id WHERE tag_id " +
            "IN(SELECT id FROM Tag WHERE is_income = :is_income)")
    fun getTradesWithTagByType(is_income: Boolean): LiveData<List<TradeWithTag>>
}