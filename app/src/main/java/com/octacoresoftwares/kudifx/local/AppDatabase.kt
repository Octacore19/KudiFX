package com.octacoresoftwares.kudifx.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.octacoresoftwares.kudifx.local.model.Latest
import com.octacoresoftwares.kudifx.local.model.Rates

@Database(entities = [Latest::class, Rates::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun ratesDao(): RatesDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "kudi-fx"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}