package com.rakuishi.postalcode2.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rakuishi.postalcode2.R

class SearchFragment : Fragment() {

    companion object {
        fun createInstance(): SearchFragment = SearchFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_recycler_view, container, false)
        view.setBackgroundResource(R.color.colorPrimaryDark)
        return view
    }
}