package com.rakuishi.postalcode2.persistence

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.rakuishi.postalcode2.Constant
import com.rakuishi.postalcode2.util.UnzipDbUtil

@Database(entities = [(PostalCode::class)], version = 1, exportSchema = false)
abstract class PostalCodesDatabase : RoomDatabase() {

    abstract fun postalCodeDao(): PostalCodeDao

    companion object {

        @Volatile
        private var INSTANCE: PostalCodesDatabase? = null

        fun getInstance(context: Context): PostalCodesDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context): PostalCodesDatabase {
            UnzipDbUtil.unzipIfNecessary(context, Constant.ZIPPED_DATABASE_NAME, Constant.DATABASE_NAME)
            return Room.databaseBuilder(context.applicationContext,
                    PostalCodesDatabase::class.java, Constant.DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build()
        }
    }
}