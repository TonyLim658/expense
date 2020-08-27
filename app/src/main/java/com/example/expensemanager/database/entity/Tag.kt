package com.example.expensemanager.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tag")
data class Tag(@PrimaryKey(autoGenerate = true) val id: Long,
               @ColumnInfo(name = "label") val label: String,
               @ColumnInfo(name = "is_income") val is_income: Boolean
)
