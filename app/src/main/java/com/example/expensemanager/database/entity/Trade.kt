package com.example.expensemanager.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "Trade",
    foreignKeys = [ForeignKey(
        entity = Tag::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("tag_id"),
        onDelete = ForeignKey.NO_ACTION
    )]
)
data class Trade(@PrimaryKey(autoGenerate = true) val id: Long,
                 @ColumnInfo(name = "label") val label: String,
                 @ColumnInfo(name = "amount") val amount: Double,
                 @ColumnInfo(name = "tag_id") val tagId: Long,
                 @ColumnInfo(name = "date") val date: Date
)
