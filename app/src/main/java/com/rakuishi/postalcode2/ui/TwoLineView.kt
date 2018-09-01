package com.rakuishi.postalcode2.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.rakuishi.postalcode2.R

class TwoLineView @JvmOverloads constructor(context: Context,
                                            attrs: AttributeSet? = null,
                                            defStyle: Int = 0,
                                            defStyleRes: Int = 0)
    : RelativeLayout(context, attrs, defStyle, defStyleRes) {

    private val primaryTextView: TextView
    private val secondaryTextView: TextView

    init {
        View.inflate(context, R.layout.view_two_line, this)
        primaryTextView = findViewById(R.id.primaryTextView)
        secondaryTextView = findViewById(R.id.secondaryTextView)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.TwoLineView)
            typedArray.getText(R.styleable.TwoLineView_android_text)
                    ?.let { primaryTextView.text = it }
            typedArray.getText(R.styleable.TwoLineView_secondaryText)
                    ?.let { secondaryTextView.text = it }
            typedArray.recycle()
        }
    }

    fun setPrimaryText(text: String) {
        primaryTextView.text = text
    }

    fun setSecondaryText(text: String) {
        secondaryTextView.text = text
    }
}