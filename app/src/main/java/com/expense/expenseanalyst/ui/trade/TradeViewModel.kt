package com.expense.expenseanalyst.ui.trade

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.expense.expenseanalyst.database.ExpenseDatabase
import com.expense.expenseanalyst.database.entity.Trade
import com.expense.expenseanalyst.database.repository.TradeRepository
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