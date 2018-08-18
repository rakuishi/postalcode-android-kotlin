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
import com.rakuishi.postalcode2.Constant.Companion.ViewType
import com.rakuishi.postalcode2.R
import com.rakuishi.postalcode2.persistence.PostalCodeDao
import kotlinx.android.synthetic.main.fragment_toolbar_recycler_view.*
import timber.log.Timber

class CityOrStreetFragment : Fragment() {

    companion object {
        private const val KEY_VIEW_TYPE = "view_type"
        private const val KEY_ID = "id"

        fun createInstance(viewType: ViewType, id: Int): CityOrStreetFragment =
                CityOrStreetFragment().also {
                    it.arguments = Bundle().also {
                        it.putSerializable(KEY_VIEW_TYPE, viewType)
                        it.putInt(KEY_ID, id)
                    }
                }
    }

    private lateinit var postalCodeDao: PostalCodeDao
    private lateinit var viewType: ViewType
    private var prefectureOrCityId: Int = 0

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        inject()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupParams()
        setupRecyclerView()
    }

    private fun inject() {
        val app = context!!.applicationContext as App
        postalCodeDao = app.postalCodeDao
    }

    private fun setupParams() {
        viewType = arguments!!.getSerializable(KEY_VIEW_TYPE) as ViewType
        prefectureOrCityId = arguments!!.getInt(KEY_ID)
    }

    private fun setupRecyclerView() {
        recyclerView.also {
            val adapter = PostalCodeListAdapter(
                    viewType,
                    when (viewType) {
                        ViewType.PREFECTURE, ViewType.DETAIL -> throw IllegalStateException("This viewType: $viewType is not supported.")
                        ViewType.CITY -> postalCodeDao.findCities(prefectureOrCityId)
                        ViewType.STREET -> postalCodeDao.findStreets(prefectureOrCityId)
                    })
            adapter.onItemClick = when (viewType) {
                ViewType.PREFECTURE, ViewType.DETAIL -> throw IllegalStateException("This viewType: $viewType is not supported.")
                ViewType.CITY -> { postalCode -> Timber.d(postalCode.toString()) }
                ViewType.STREET -> { postalCode -> Timber.d(postalCode.toString()) }
            }

            it.layoutManager = LinearLayoutManager(context)
            it.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            it.adapter = adapter
        }
    }
}