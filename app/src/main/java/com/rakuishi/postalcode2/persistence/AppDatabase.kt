package com.rakuishi.postalcode2.persistence

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.rakuishi.postalcode2.Constant
import com.rakuishi.postalcode2.util.UnzipDb

@Database(entities = [(PostalCode::class)], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun postalCodeDao(): PostalCodeDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context): AppDatabase {
            // Unzip existing database
            UnzipDb.unzipIfNecessary(context, Constant.ZIPPED_DATABASE_NAME, Constant.DATABASE_NAME)

            return Room.databaseBuilder(context.applicationContext,
                    AppDatabase::class.java, Constant.DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build()
        }
    }
}