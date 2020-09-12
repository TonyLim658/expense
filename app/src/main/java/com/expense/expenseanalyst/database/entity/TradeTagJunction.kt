package com.expense.expenseanalyst.database.entity

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(
    tableName = "Trade_Tag",
    primaryKeys = ["trade_id", "tag_id"],
    indices = [
        Index(value = ["trade_id"]),
        Index(value = ["tag_id"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = Trade::class,
            parentColumns = ["id"],
            childColumns = ["trade_id"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = Tag::class,
            parentColumns = ["id"],
            childColumns = ["tag_id"],
            onDelete = CASCADE
        )
    ]
)
data class TradeTagJunction(
    @ColumnInfo(name = "trade_id") val tradeId: Long,
    @ColumnInfo(name = "tag_id") val tagId: Long
)