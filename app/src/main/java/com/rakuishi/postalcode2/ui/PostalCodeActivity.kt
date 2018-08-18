package com.rakuishi.postalcode2.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import com.rakuishi.postalcode2.Constant.Companion.ViewType
import com.rakuishi.postalcode2.R
import com.rakuishi.postalcode2.persistence.PostalCode
import kotlinx.android.synthetic.main.activity_postal_code.*

class PostalCodeActivity : AppCompatActivity() {

    companion object {
        private const val KEY_VIEW_TYPE = "view_type"
        private const val KEY_POSTAL_CODE = "postal_code"

        fun start(context: Context, viewType: ViewType, postalCode: PostalCode) {
            context.startActivity(Intent(context, PostalCodeActivity::class.java).also {
                it.putExtra(KEY_VIEW_TYPE, viewType)
                it.putExtra(KEY_POSTAL_CODE, postalCode)
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_postal_code)
        setSupportActionBar(toolbar)

        setupContainer()
    }

    override fun onBackPressed() {
        // TODO: Android 標準の戻る動作に popBackStack が含まれているため、その挙動に任せる
        if (supportFragmentManager.fragments.size > 1) {
            supportFragmentManager.popBackStack()
            updateToolbar()
            return
        }

        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setupContainer() {
        val viewType = intent.getSerializableExtra(KEY_VIEW_TYPE) as ViewType
        val postalCode = intent.getSerializableExtra(KEY_POSTAL_CODE) as PostalCode
        replaceFragment(when (viewType) {
            ViewType.PREFECTURE, ViewType.DETAIL -> throw IllegalStateException("This viewType: $viewType is not supported.")
            ViewType.CITY -> CityOrStreetFragment.createInstance(viewType, postalCode.prefectureId)
            ViewType.STREET -> CityOrStreetFragment.createInstance(viewType, postalCode.cityId)
        })
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit()

        updateToolbar()
    }

    private fun updateToolbar() {
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }
    }
}