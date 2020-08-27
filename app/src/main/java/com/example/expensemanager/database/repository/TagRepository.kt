package com.example.expensemanager.database.repository

import androidx.lifecycle.LiveData
import com.example.expensemanager.database.dao.TagDao
import com.example.expensemanager.database.entity.Tag

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class TagRepository (private val tagDao: TagDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allTags: LiveData<List<Tag>> = tagDao.getAlphabetizedTags()

    suspend fun insert(tag: Tag) {
        tagDao.insert(tag)
    }

    suspend fun deleteById(tagId: Long) {
        tagDao.deleteById(tagId)
    }

    fun getTagByType(is_income: Boolean): LiveData<List<Tag>> {
        return tagDao.getTagByType(is_income)
    }
}