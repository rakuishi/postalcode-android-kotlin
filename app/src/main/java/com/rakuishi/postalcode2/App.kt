package com.rakuishi.postalcode2

import android.app.Application
import com.rakuishi.postalcode2.persistence.AppDatabase
import com.rakuishi.postalcode2.persistence.PostalCodeDao
import timber.log.Timber

class App : Application() {

    lateinit var postalCodeDao: PostalCodeDao

    override fun onCreate() {
        super.onCreate()
        setupTimber()
        setupPostalCodeDao()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    private fun setupPostalCodeDao() {
        postalCodeDao = AppDatabase.getInstance(this).postalCodeDao()
    }
}