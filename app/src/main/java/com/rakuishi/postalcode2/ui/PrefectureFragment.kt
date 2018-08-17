package com.rakuishi.postalcode2.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rakuishi.postalcode2.App
import com.rakuishi.postalcode2.R
import com.rakuishi.postalcode2.persistence.PostalCodeDao
import kotlinx.android.synthetic.main.fragment_toolbar_recycler_view.*
import timber.log.Timber

class PrefectureFragment : Fragment() {

    private lateinit var postalCodeDao: PostalCodeDao

    companion object {
        fun createInstance(): PrefectureFragment = PrefectureFragment()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        inject()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_toolbar_recycler_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView()
        setupToolbar()
    }

    private fun inject() {
        val app = context!!.applicationContext as App
        postalCodeDao = app.postalCodeDao
    }

    private fun setupRecyclerView() {
        recyclerView.also {
            val adapter = PostalCodeListAdapter(postalCodeDao.findPrefectures())
            adapter.onItemClick = { postalCode -> Timber.d(postalCode.toString()) }

            val linearLayoutManager = LinearLayoutManager(context)

            it.layoutManager = linearLayoutManager
            it.addItemDecoration(DividerItemDecoration(context, linearLayoutManager.orientation))
            it.adapter = adapter
        }
    }

    private fun setupToolbar() {
        toolbar.also {
            it.setTitle(R.string.title_prefecture)
        }
    }
}