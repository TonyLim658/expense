package com.expense.expenseanalyst.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Sheet")
data class Sheet(@PrimaryKey(autoGenerate = true) val id: Long,
                 @ColumnInfo(name = "label") val label: String,
                 @ColumnInfo(name = "uuid") val uuid: String = UUID.randomUUID().toString()
)
