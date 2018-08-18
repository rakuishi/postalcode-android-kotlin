package com.rakuishi.postalcode2.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.rakuishi.postalcode2.Constant.Companion.ViewType
import com.rakuishi.postalcode2.R
import com.rakuishi.postalcode2.persistence.PostalCode

class PostalCodeListAdapter(private val viewType: ViewType,
                            private val postalCodes: List<PostalCode>)
    : RecyclerView.Adapter<PostalCodeListAdapter.ViewHolder>() {

    var onItemClick: ((PostalCode) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_list_item_two_line, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val postalCode = postalCodes[position]
        holder.primaryTextView.text = postalCode.prefecture
        holder.secondaryTextView.visibility = View.GONE
        holder.itemView.setOnClickListener { onItemClick?.invoke(postalCode) }
    }

    override fun getItemCount(): Int = postalCodes.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val primaryTextView: TextView = view.findViewById(R.id.primaryTextView)
        val secondaryTextView: TextView = view.findViewById(R.id.secondaryTextView)
    }
}