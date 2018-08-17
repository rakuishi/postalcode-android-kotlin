package com.rakuishi.postalcode2.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rakuishi.postalcode2.R
import kotlinx.android.synthetic.main.fragment_toolbar_recycler_view.*

class BookmarkFragment : Fragment() {

    companion object {
        fun createInstance(): BookmarkFragment = BookmarkFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_toolbar_recycler_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView()
        setupToolbar()
    }

    private fun setupRecyclerView() {
        recyclerView.also {
            val adapter = EmptyAdapter(R.string.empty_bookmark, R.drawable.ic_bookmark_black_24dp)
            val linearLayoutManager = LinearLayoutManager(context)
            it.layoutManager = linearLayoutManager
            it.adapter = adapter
        }
    }

    private fun setupToolbar() {
        toolbar.also {
            it.setTitle(R.string.title_bookmark)
        }
    }
}