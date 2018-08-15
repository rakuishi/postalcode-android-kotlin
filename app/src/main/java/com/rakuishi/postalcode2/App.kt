package com.rakuishi.postalcode2

import android.app.Application
import com.rakuishi.postalcode2.persistence.PostalCodeDao
import com.rakuishi.postalcode2.persistence.PostalCodesDatabase
import timber.log.Timber

class App : Application() {

    private var postalCodeDao: PostalCodeDao? = null

    override fun onCreate() {
        super.onCreate()
        setupTimber()
        setupPostalCodeDao()

        Timber.d("prefecture size: ${postalCodeDao!!.findPrefectures().size}")
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    private fun setupPostalCodeDao() {
        postalCodeDao = PostalCodesDatabase.getInstance(this).postalCodeDao()
    }
}