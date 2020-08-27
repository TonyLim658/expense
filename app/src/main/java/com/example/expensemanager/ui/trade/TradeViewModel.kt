package com.example.expensemanager.ui.trade

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.expensemanager.database.ExpenseDatabase
import com.example.expensemanager.database.entity.Trade
import com.example.expensemanager.database.entity.TradeWithTag
import com.example.expensemanager.database.repository.TradeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TradeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TradeRepository
    val allTrades: LiveData<List<Trade>>
    val allTradesWithTag: LiveData<List<TradeWithTag>>

    init {
        val tradesDao = ExpenseDatabase.getDatabase(application, viewModelScope).tradeDao()
        repository = TradeRepository(tradesDao)
        allTrades = repository.allTrades
        allTradesWithTag = repository.allTradesWithTag
    }

    fun getTradesWithTagByType(isIncome: Boolean): LiveData<List<TradeWithTag>> {
        return repository.getAllTradesWithTagByType(isIncome)
    }

    fun insert(trade: Trade) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(trade)
    }

    fun deleteById(tradeId: Long) = viewModelScope.launch(Dispatchers.IO) {
        Log.d("TradeViewModel : ", "Delete trade $tradeId")
        repository.deleteById(tradeId)
    }
}