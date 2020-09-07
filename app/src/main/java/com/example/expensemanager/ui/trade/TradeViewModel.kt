package com.example.expensemanager.ui.trade

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.expensemanager.database.ExpenseDatabase
import com.example.expensemanager.database.entity.Trade
import com.example.expensemanager.database.repository.TradeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TradeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TradeRepository

    init {
        val tradesDao = ExpenseDatabase.getDatabase(application, viewModelScope).tradeDao()
        repository = TradeRepository(tradesDao)
    }

    fun getTradesByType(isIncome: Boolean): LiveData<List<Trade>> {
        return repository.getAllTradesByType(isIncome)
    }

    fun deleteById(tradeId: Long) = viewModelScope.launch(Dispatchers.IO) {
        Log.d("TradeViewModel : ", "Delete trade $tradeId")
        repository.deleteById(tradeId)
    }
}