package com.rakuishi.postalcode2.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.rakuishi.postalcode2.Constant.Companion.ViewType
import com.rakuishi.postalcode2.R
import com.rakuishi.postalcode2.persistence.PostalCode

class TwoLineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        fun createViewHolder(parent: ViewGroup): TwoLineViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.view_two_line, parent, false)
            return TwoLineViewHolder(view)
        }
    }

    private val primaryTextView: TextView = itemView.findViewById(R.id.primaryTextView)
    private val secondaryTextView: TextView = itemView.findViewById(R.id.secondaryTextView)
    var onItemClick: (() -> Unit)? = null

    init {
        itemView.setOnClickListener { onItemClick?.invoke() }
    }

    fun render(viewType: ViewType, postalCode: PostalCode) {
        when (viewType) {
            ViewType.PREFECTURE -> {
                primaryTextView.text = postalCode.prefecture
                secondaryTextView.visibility = View.GONE
            }
            ViewType.CITY -> {
                primaryTextView.text = postalCode.city
                secondaryTextView.text = postalCode.cityYomi
            }
            ViewType.STREET -> {
                primaryTextView.text = postalCode.street
                secondaryTextView.text = postalCode.streetYomi
            }
            ViewType.DETAIL -> throw IllegalStateException("This viewType: $viewType is not supported.")
        }
    }
}