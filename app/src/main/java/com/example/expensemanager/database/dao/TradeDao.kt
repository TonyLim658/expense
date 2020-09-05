package com.example.expensemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.expensemanager.database.entity.Trade

@Dao
interface TradeDao {
    @Query("SELECT * from Trade")
    fun getAll(): LiveData<List<Trade>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(trade: Trade): Long

    @Query("INSERT INTO Trade_Tag VALUES(:tradeId, :tagId)")
    suspend fun joinTradeAndTag(tradeId: Long, tagId: Long)

    @Query("DELETE FROM Trade")
    suspend fun deleteAll()

    @Query("DELETE FROM Trade WHERE id = :tradeId")
    suspend fun deleteById(tradeId: Long)

    @Query("SELECT id, amount, date, label FROM Trade " +
            "INNER JOIN Trade_Tag ON Trade.id = Trade_Tag.trade_id " +
            "WHERE Trade_Tag.tag_id " +
            "IN(SELECT id FROM Tag WHERE is_income = :is_income) " +
            "GROUP BY id " +
            "ORDER BY Trade.date ASC ")
    fun getTradesByType(is_income: Boolean): LiveData<List<Trade>>
}