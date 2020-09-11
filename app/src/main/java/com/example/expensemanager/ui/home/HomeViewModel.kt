package com.example.expensemanager.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.expensemanager.database.ExpenseDatabase
import com.example.expensemanager.database.entity.Sheet
import com.example.expensemanager.database.entity.TagWithAmount
import com.example.expensemanager.database.entity.Trade
import com.example.expensemanager.database.repository.SheetRepository
import com.example.expensemanager.database.repository.TagRepository
import com.example.expensemanager.database.repository.TradeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TagRepository
    private val tradeRepository: TradeRepository

    init {
        val tagsDao = ExpenseDatabase.getDatabase(application, viewModelScope).tagDao()
        val tradesDao = ExpenseDatabase.getDatabase(application, viewModelScope).tradeDao()
        repository = TagRepository(tagsDao)
        tradeRepository = TradeRepository(tradesDao)
    }

    fun getTagWithSumAmount(is_income: Boolean): LiveData<List<TagWithAmount>> {
        return repository.getTagWithSumAmount(is_income)
    }

    fun getTradesByType(isIncome: Boolean): LiveData<List<Trade>> {
        return tradeRepository.getAllTradesByType(isIncome)
    }
}