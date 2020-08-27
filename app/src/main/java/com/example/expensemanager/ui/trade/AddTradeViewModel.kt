package com.example.expensemanager.ui.trade

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.room.Dao
import com.example.expensemanager.database.entity.Tag
import com.example.expensemanager.database.repository.TagRepository
import com.example.expensemanager.database.ExpenseDatabase
import com.example.expensemanager.database.entity.Trade
import com.example.expensemanager.database.repository.TradeRepository
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
        Log.d("TradeViewModel : ", "Get tag by income is $is_income")
        return tagRepository.getTagByType(is_income)
    }

    fun insert(trade: Trade) = viewModelScope.launch(Dispatchers.IO) {
        tradeRepository.insert(trade)
    }
}