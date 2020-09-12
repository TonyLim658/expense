package com.expense.expenseanalyst.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "Trade")
data class Trade(@PrimaryKey(autoGenerate = true) val id: Long,
                 @ColumnInfo(name = "label") val label: String,
                 @ColumnInfo(name = "note") val note: String,
                 @ColumnInfo(name = "amount") val amount: Double,
                 @ColumnInfo(name = "date") val date: Date
)
