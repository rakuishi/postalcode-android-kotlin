package com.rakuishi.postalcode2.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.rakuishi.postalcode2.R
import com.rakuishi.postalcode2.persistence.PostalCode
import kotlinx.android.synthetic.main.activity_postal_code_detail.*

class PostalCodeDetailActivity : AppCompatActivity() {

    companion object {
        private const val KEY_POSTAL_CODE = "postal_code"

        fun start(context: Context, postalCode: PostalCode) {
            context.startActivity(Intent(context, PostalCodeDetailActivity::class.java).also {
                it.putExtra(KEY_POSTAL_CODE, postalCode)
            })
        }
    }

    private lateinit var postalCode: PostalCode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupParams()

        setContentView(R.layout.activity_postal_code_detail)
        setupActionBar()
        setupView()
    }

    private fun setupParams() {
        postalCode = intent.getSerializableExtra(KEY_POSTAL_CODE) as PostalCode
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            it.title = postalCode.fullName
        }
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun setupView() {
        postalCodeView.setPrimaryText(postalCode.hyphenedCode)
        addressView.setPrimaryText(postalCode.fullName)
        pronunciationView.setPrimaryText(postalCode.fullYomi)
    }
}