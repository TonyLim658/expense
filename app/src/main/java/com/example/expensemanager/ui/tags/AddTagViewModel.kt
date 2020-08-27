package com.example.expensemanager.ui.tags

import android.app.Application
import androidx.lifecycle.*
import com.example.expensemanager.database.entity.Tag
import com.example.expensemanager.database.repository.TagRepository
import com.example.expensemanager.database.ExpenseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddTagViewModel(application: Application) : AndroidViewModel(application) {
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

}