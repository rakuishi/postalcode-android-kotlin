package com.rakuishi.postalcode2.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.rakuishi.postalcode2.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var bottomNavigationViewPresenter: MainBottomNavigationViewPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationViewPresenter = MainBottomNavigationViewPresenter(navigation, supportFragmentManager, R.id.container)
    }
}
