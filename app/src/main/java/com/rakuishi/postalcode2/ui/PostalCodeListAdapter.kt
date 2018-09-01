package com.rakuishi.postalcode2.ui

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.rakuishi.postalcode2.Constant.Companion.ViewType
import com.rakuishi.postalcode2.persistence.PostalCode

class PostalCodeListAdapter(private val viewType: ViewType,
                            private val postalCodes: List<PostalCode>)
    : RecyclerView.Adapter<TwoLineViewHolder>() {

    var onItemClick: ((PostalCode) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TwoLineViewHolder {
        return TwoLineViewHolder.createViewHolder(parent)
    }

    override fun onBindViewHolder(holder: TwoLineViewHolder, position: Int) {
        val postalCode = postalCodes[position]
        holder.render(viewType, postalCode)
        holder.onItemClick = { onItemClick?.invoke(postalCode) }
    }

    override fun getItemCount(): Int = postalCodes.size
}