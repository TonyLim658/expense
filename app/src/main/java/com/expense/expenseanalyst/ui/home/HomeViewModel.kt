package com.expense.expenseanalyst.ui.home

import android.app.Application
import androidx.lifecycle.*
import com.expense.expenseanalyst.database.ExpenseDatabase
import com.expense.expenseanalyst.database.entity.TagWithAmount
import com.expense.expenseanalyst.database.entity.Trade
import com.expense.expenseanalyst.database.repository.TagRepository
import com.expense.expenseanalyst.database.repository.TradeRepository

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