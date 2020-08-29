package com.example.expensemanager.database.entity

import androidx.room.*
import java.sql.Date

data class TradeWithTag(val id: Long,
                        @ColumnInfo(name = "amount") val amount: Double,
                        @ColumnInfo(name = "trade_label") val tradeLabel: String,
                        @ColumnInfo(name = "tag_id") val tagId: Long,
                        @ColumnInfo(name = "tag_label") val tagLabel: String,
                        @ColumnInfo(name = "is_income") val is_income: Boolean,
                        @ColumnInfo(name = "date") val date: Date
)