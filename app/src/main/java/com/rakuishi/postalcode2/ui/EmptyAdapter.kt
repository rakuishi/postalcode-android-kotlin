package com.rakuishi.postalcode2.ui

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.rakuishi.postalcode2.R

class EmptyAdapter(@StringRes private val stringResId: Int,
                   @DrawableRes private val drawableResId: Int)
    : RecyclerView.Adapter<EmptyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_empty, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.setText(stringResId)
        holder.imageView.setImageResource(drawableResId)
    }

    override fun getItemCount(): Int = 1

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView)
        val imageView: ImageView = view.findViewById(R.id.imageView)
    }
}
