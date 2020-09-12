package com.expense.expenseanalyst.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tag")
data class Tag(@PrimaryKey(autoGenerate = true) val id: Long,
               @ColumnInfo(name = "label") val label: String,
               @ColumnInfo(name = "icon_path") val icon_path: String,
               @ColumnInfo(name = "is_income") val is_income: Boolean,
               @ColumnInfo(name = "sheet_id") val sheetId: Long
)
