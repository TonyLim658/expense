package com.example.expensemanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.expensemanager.database.dao.TagDao
import com.example.expensemanager.database.dao.TradeDao
import com.example.expensemanager.database.entity.Tag
import com.example.expensemanager.database.entity.Trade
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Tag::class, Trade::class], version = 4)
@TypeConverters(Converters::class)
abstract class ExpenseDatabase : RoomDatabase() {

    abstract fun tagDao(): TagDao
    abstract fun tradeDao(): TradeDao

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
                            database.tradeDao()
                        )
                    }
                }
            }
        }

        suspend fun populateDatabase(tagDao: TagDao, tradeDao: TradeDao) {
            tagDao.deleteAll()
            tradeDao.deleteAll()

            var tag = Tag(0, "Other", true)
            tagDao.insert(tag)
            tag = Tag(0, "Food", false)
            tagDao.insert(tag)
            tag = Tag(0, "Rent", false)
            tagDao.insert(tag)
            tag = Tag(0, "Salary", true)
            tagDao.insert(tag)
        }
    }

}