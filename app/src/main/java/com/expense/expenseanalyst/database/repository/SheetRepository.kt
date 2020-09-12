package com.expense.expenseanalyst.database.repository

import androidx.lifecycle.LiveData
import com.expense.expenseanalyst.database.dao.SheetDao
import com.expense.expenseanalyst.database.entity.Sheet

class SheetRepository (private val sheetDao: SheetDao) {

    suspend fun insert(sheet: Sheet) {
        sheetDao.insert(sheet)
    }

    fun getAllSheets(): LiveData<List<Sheet>> {
        return sheetDao.getAll()
    }

}