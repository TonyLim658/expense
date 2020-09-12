package com.expense.expenseanalyst.ui.tags

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.expense.expenseanalyst.database.entity.Tag
import com.expense.expenseanalyst.database.repository.TagRepository
import com.expense.expenseanalyst.database.ExpenseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TagViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TagRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allTags: LiveData<List<Tag>>

    init {
        val tagsDao = ExpenseDatabase.getDatabase(application, viewModelScope).tagDao()
        repository = TagRepository(tagsDao)
        allTags = repository.allTags
    }

    fun insert(tag: Tag) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(tag)
    }

    fun deleteById(tagId: Long) = viewModelScope.launch(Dispatchers.IO) {
        Log.d("TagViewModel : ", "Delete tag $tagId")
        repository.deleteById(tagId)
    }
}