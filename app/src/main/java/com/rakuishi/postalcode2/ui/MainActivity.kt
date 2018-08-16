package com.rakuishi.postalcode2.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.rakuishi.postalcode2.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var bottomNavigationBehavior: MainBottomNavigationBehavior

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationBehavior =
                MainBottomNavigationBehavior(navigation, supportFragmentManager, R.id.container)
    }
}
