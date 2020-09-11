package com.example.expensemanager.database.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.expensemanager.database.dao.SheetDao
import com.example.expensemanager.database.dao.TradeDao
import com.example.expensemanager.database.entity.Sheet
import com.example.expensemanager.database.entity.Tag
import com.example.expensemanager.database.entity.TagWithAmount
import com.example.expensemanager.database.entity.Trade

class SheetRepository (private val sheetDao: SheetDao) {

    suspend fun insert(sheet: Sheet) {
        sheetDao.insert(sheet)
    }

    fun getAllSheets(): LiveData<List<Sheet>> {
        return sheetDao.getAll()
    }

}