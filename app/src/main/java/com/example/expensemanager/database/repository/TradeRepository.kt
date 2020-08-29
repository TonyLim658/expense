package com.example.expensemanager.database.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.expensemanager.database.dao.TradeDao
import com.example.expensemanager.database.entity.Tag
import com.example.expensemanager.database.entity.Trade

class TradeRepository (private val tradeDao: TradeDao) {

    val allTrades: LiveData<List<Trade>> = tradeDao.getAll()

    suspend fun insert(trade: Trade) {
        tradeDao.insert(trade)
    }

    suspend fun insert(trade: Trade, tag: Tag) {
        val tradeId = tradeDao.insert(trade)
        tradeDao.joinTradeAndTag(tradeId, tag.id)
    }

    suspend fun deleteById(tradeId: Long) {
        tradeDao.deleteById(tradeId)
    }

    fun getAllTradesByType(isIncome: Boolean): LiveData<List<Trade>> {
        return tradeDao.getTradesByType(isIncome)
    }
}