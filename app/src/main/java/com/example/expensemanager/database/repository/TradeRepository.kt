package com.example.expensemanager.database.repository

import androidx.lifecycle.LiveData
import com.example.expensemanager.database.dao.TradeDao
import com.example.expensemanager.database.entity.Trade
import com.example.expensemanager.database.entity.TradeWithTag

class TradeRepository (private val tradeDao: TradeDao) {

    val allTrades: LiveData<List<Trade>> = tradeDao.getAll()
    val allTradesWithTag: LiveData<List<TradeWithTag>> = tradeDao.getTradesWithTag()

    suspend fun insert(trade: Trade) {
        tradeDao.insert(trade)
    }

    suspend fun deleteById(tradeId: Long) {
        tradeDao.deleteById(tradeId)
    }

    fun getAllTradesWithTagByType(isIncome: Boolean): LiveData<List<TradeWithTag>> {
        return tradeDao.getTradesWithTagByType(isIncome)
    }
}