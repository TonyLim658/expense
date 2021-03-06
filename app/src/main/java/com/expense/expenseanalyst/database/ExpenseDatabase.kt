package com.expense.expenseanalyst.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.expense.expenseanalyst.database.dao.SheetDao
import com.expense.expenseanalyst.database.dao.TagDao
import com.expense.expenseanalyst.database.dao.TradeDao
import com.expense.expenseanalyst.database.entity.Sheet
import com.expense.expenseanalyst.database.entity.Tag
import com.expense.expenseanalyst.database.entity.Trade
import com.expense.expenseanalyst.database.entity.TradeTagJunction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Tag::class, Trade::class, TradeTagJunction::class, Sheet::class], version = 7)
@TypeConverters(Converters::class)
abstract class ExpenseDatabase : RoomDatabase() {

    abstract fun tagDao(): TagDao
    abstract fun tradeDao(): TradeDao
    abstract fun sheetDao(): SheetDao

    companion object {
        @Volatile
        private var INSTANCE: ExpenseDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): ExpenseDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE
                ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExpenseDatabase::class.java,
                    "expense_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .addCallback(
                        TradeDatabaseCallback(
                            scope
                        )
                    )
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class TradeDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(
                            database.tagDao(),
                            database.tradeDao(),
                            database.sheetDao()
                        )
                    }
                }
            }
        }

        suspend fun populateDatabase(tagDao: TagDao, tradeDao: TradeDao, sheetDao: SheetDao) {
            tagDao.deleteAll()
            tradeDao.deleteAll()
            var sheet = Sheet(0, "")
            val sheetId = sheetDao.insert(sheet)
            var tag = Tag(0, "Other", "", true, sheetId)
            tagDao.insert(tag)
            tag = Tag(0, "Other", "", false, sheetId)
            tagDao.insert(tag)
            tag = Tag(0, "Food", "", false, sheetId)
            tagDao.insert(tag)
            tag = Tag(0, "Rent", "", false, sheetId)
            tagDao.insert(tag)
            tag = Tag(0, "Salary", "", true, sheetId)
            tagDao.insert(tag)
        }
    }

}