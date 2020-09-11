package com.example.expensemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.expensemanager.database.entity.Sheet

@Dao
interface SheetDao {
    @Query("SELECT * from Sheet")
    fun getAll(): LiveData<List<Sheet>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(sheet: Sheet): Long

}