package com.rakuishi.postalcode2.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rakuishi.postalcode2.R

class BookmarkFragment : Fragment() {

    companion object {
        fun createInstance(): BookmarkFragment = BookmarkFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_toolbar_recycler_view, container, false)
    }
}