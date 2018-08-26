package com.rakuishi.postalcode2.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import com.rakuishi.postalcode2.Constant.Companion.ViewType
import com.rakuishi.postalcode2.R
import com.rakuishi.postalcode2.persistence.PostalCode
import kotlinx.android.synthetic.main.activity_postal_code.*

class PostalCodeActivity : AppCompatActivity(), CityOrStreetFragment.Callback {

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

    private lateinit var viewType: ViewType
    private lateinit var postalCode: PostalCode
    private var titles: MutableList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupParams()

        setContentView(R.layout.activity_postal_code)
        setSupportActionBar(toolbar)

        replaceFragment(viewType, postalCode)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
            titles.removeAt(titles.lastIndex)
            updateToolbar()
            return
        }

        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setupParams() {
        viewType = intent.getSerializableExtra(KEY_VIEW_TYPE) as ViewType
        postalCode = intent.getSerializableExtra(KEY_POSTAL_CODE) as PostalCode
    }

    private fun replaceFragment(viewType: ViewType, postalCode: PostalCode) {
        val fragment = when (viewType) {
            ViewType.PREFECTURE, ViewType.DETAIL -> throw IllegalStateException("This viewType: $viewType is not supported.")
            ViewType.CITY -> CityOrStreetFragment.createInstance(viewType, postalCode.prefectureId)
            ViewType.STREET -> CityOrStreetFragment.createInstance(viewType, postalCode.cityId)
        }

        val title = when (viewType) {
            ViewType.PREFECTURE, ViewType.DETAIL -> throw IllegalStateException("This viewType: $viewType is not supported.")
            ViewType.CITY -> postalCode.prefecture
            ViewType.STREET -> postalCode.city
        }

        supportFragmentManager
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit()

        titles.add(title)
        updateToolbar()
    }

    private fun updateToolbar() {
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            it.title = titles.last()
        }
    }

    // region CityOrStreetFragment.Callback

    override fun onItemClick(viewType: ViewType, postalCode: PostalCode) {
        replaceFragment(viewType, postalCode)
    }

    // endregion
}