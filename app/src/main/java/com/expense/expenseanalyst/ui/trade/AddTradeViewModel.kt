package com.expense.expenseanalyst.ui.trade

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.expense.expenseanalyst.database.entity.Tag
import com.expense.expenseanalyst.database.repository.TagRepository
import com.expense.expenseanalyst.database.ExpenseDatabase
import com.expense.expenseanalyst.database.entity.Trade
import com.expense.expenseanalyst.database.repository.TradeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddTradeViewModel(application: Application) : AndroidViewModel(application) {

    private val tagRepository: TagRepository
    private val tradeRepository: TradeRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.

    init {
        val tradesDao = ExpenseDatabase.getDatabase(application, viewModelScope).tradeDao()
        tradeRepository = TradeRepository(tradesDao)
        val tagsDao = ExpenseDatabase.getDatabase(application, viewModelScope).tagDao()
        tagRepository = TagRepository(tagsDao)
    }

    fun getTagsByType(is_income: Boolean): LiveData<List<Tag>> {
        Log.d("AddTradeViewModel : ", "Get tag by income is $is_income")
        return tagRepository.getTagByType(is_income)
    }

    fun insert(trade: Trade, tag: Tag) = viewModelScope.launch(Dispatchers.IO) {
        tradeRepository.insert(trade, tag)
    }

    fun insertWithTags(trade: Trade, tags: ArrayList<Tag>) = viewModelScope.launch(Dispatchers.IO) {
        tradeRepository.insertWithTags(trade, tags)
    }
}