package com.expense.expenseanalyst.ui.tags

import android.app.Application
import androidx.lifecycle.*
import com.expense.expenseanalyst.database.entity.Tag
import com.expense.expenseanalyst.database.repository.TagRepository
import com.expense.expenseanalyst.database.ExpenseDatabase
import com.expense.expenseanalyst.database.entity.Sheet
import com.expense.expenseanalyst.database.repository.SheetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddTagViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TagRepository
    private val sheetRepository: SheetRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.

    init {
        val tagsDao = ExpenseDatabase.getDatabase(application, viewModelScope).tagDao()
        val sheetsDao = ExpenseDatabase.getDatabase(application, viewModelScope).sheetDao()
        repository = TagRepository(tagsDao)
        sheetRepository = SheetRepository(sheetsDao)
    }

    fun getAllSheet(): LiveData<List<Sheet>> {
        return sheetRepository.getAllSheets()
    }

    fun insert(tag: Tag) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(tag)
    }

}