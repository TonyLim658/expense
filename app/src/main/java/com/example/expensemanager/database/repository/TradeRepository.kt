package com.example.expensemanager.database.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.expensemanager.database.dao.TradeDao
import com.example.expensemanager.database.entity.Tag
import com.example.expensemanager.database.entity.TagWithAmount
import com.example.expensemanager.database.entity.Trade

class TradeRepository (private val tradeDao: TradeDao) {

    val allTrades: LiveData<List<Trade>> = tradeDao.getAll()

    suspend fun insert(trade: Trade) {
        tradeDao.insert(trade)
    }

    suspend fun insert(trade: Trade, tag: Tag) {
        val tradeId = tradeDao.insert(trade)
        tradeDao.joinTradeAndTag(tradeId, tag.id)
        Log.d("TradeRepo : ", trade.label + " joined with " + tag.label)
    }

    suspend fun insertWithTags(trade: Trade, tags: ArrayList<Tag>) {
        val tradeId = tradeDao.insert(trade)
        tags.forEach { tag ->
            tradeDao.joinTradeAndTag(tradeId, tag.id)
            Log.d("TradeRepo : ", trade.label + "(" + tradeId + ")" +
                    " joined with " + tag.label)
        }
    }

    suspend fun deleteById(tradeId: Long) {
        tradeDao.deleteById(tradeId)
    }

    fun getAllTradesByType(isIncome: Boolean): LiveData<List<Trade>> {
        return tradeDao.getTradesByType(isIncome)
    }

    fun getTotalAmountOfType(is_income: Boolean): Double {
        val trades = getAllTradesByType(is_income).value
        var total = 0.0
        if (trades != null) {
            for (trade in trades) {
                total += trade.amount
            }
        }
        return total
    }
}