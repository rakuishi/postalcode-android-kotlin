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
import com.rakuishi.postalcode2.persistence.PostalCode
import com.rakuishi.postalcode2.persistence.PostalCodeDao
import kotlinx.android.synthetic.main.fragment_toolbar_recycler_view.*

class PostalCodeListFragment : Fragment() {

    companion object {
        private const val KEY_VIEW_TYPE = "view_type"
        private const val KEY_ID = "id"

        fun createPrefectureInstance(): PostalCodeListFragment =
                PostalCodeListFragment().also {
                    it.arguments = Bundle().also {
                        it.putSerializable(KEY_VIEW_TYPE, ViewType.PREFECTURE)
                    }
                }

        fun createCityInstance(prefectureId: Int): PostalCodeListFragment =
                PostalCodeListFragment().also {
                    it.arguments = Bundle().also {
                        it.putSerializable(KEY_VIEW_TYPE, ViewType.CITY)
                        it.putInt(KEY_ID, prefectureId)
                    }
                }

        fun createStreetInstance(cityId: Int): PostalCodeListFragment =
                PostalCodeListFragment().also {
                    it.arguments = Bundle().also {
                        it.putSerializable(KEY_VIEW_TYPE, ViewType.STREET)
                        it.putInt(KEY_ID, cityId)
                    }
                }
    }

    interface Callback {
        fun onItemClick(viewType: ViewType, postalCode: PostalCode)
    }

    private lateinit var postalCodeDao: PostalCodeDao
    private lateinit var viewType: ViewType
    private var prefectureOrCityId: Int = 0
    private lateinit var callback: Callback

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        inject()
        if (parentFragment is Callback) callback = parentFragment as Callback
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
        val app = requireContext().applicationContext as App
        postalCodeDao = app.postalCodeDao
    }

    private fun setupParams() {
        viewType = arguments!!.getSerializable(KEY_VIEW_TYPE) as ViewType
        prefectureOrCityId = arguments!!.getInt(KEY_ID)
    }

    private fun setupRecyclerView() {
        recyclerView.also {
            val adapter = PostalCodeListAdapter(viewType, getPostalCodes())
            adapter.onItemClick = onItemClick()

            it.layoutManager = LinearLayoutManager(context)
            it.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            it.adapter = adapter
        }
    }

    private fun getPostalCodes(): List<PostalCode> =
            when (viewType) {
                ViewType.PREFECTURE -> postalCodeDao.findPrefectures()
                ViewType.CITY -> postalCodeDao.findCities(prefectureOrCityId)
                ViewType.STREET -> postalCodeDao.findStreets(prefectureOrCityId)
                ViewType.DETAIL -> throw IllegalStateException("This viewType: $viewType is not supported.")
            }

    private fun onItemClick(): ((PostalCode) -> Unit)? =
            when (viewType) {
                ViewType.PREFECTURE -> { postalCode -> callback.onItemClick(ViewType.CITY, postalCode) }
                ViewType.CITY -> { postalCode -> callback.onItemClick(ViewType.STREET, postalCode) }
                ViewType.STREET -> { postalCode -> callback.onItemClick(ViewType.DETAIL, postalCode) }
                ViewType.DETAIL -> throw IllegalStateException("This viewType: $viewType is not supported.")
            }
}