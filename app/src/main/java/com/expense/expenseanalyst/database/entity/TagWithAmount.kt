package com.expense.expenseanalyst.database.entity

import androidx.room.*

data class TagWithAmount(val id: Long,
                         @ColumnInfo(name = "label") val label: String,
                         @ColumnInfo(name = "icon_path") val icon_path: String,
                         @ColumnInfo(name = "is_income") val is_income: Boolean,
                         @ColumnInfo(name = "sum_amount") val sum_amount: Double
)