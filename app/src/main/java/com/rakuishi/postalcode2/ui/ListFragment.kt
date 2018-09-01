package com.rakuishi.postalcode2.ui

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rakuishi.postalcode2.Constant.Companion.ViewType
import com.rakuishi.postalcode2.R
import com.rakuishi.postalcode2.persistence.PostalCode
import kotlinx.android.synthetic.main.fragment_toolbar_recycler_view.*

class ListFragment : Fragment(), PostalCodeListFragment.Callback {

    companion object {
        fun createInstance(): ListFragment = ListFragment()
    }

    private var titles: MutableList<String> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_toolbar_container, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        replaceFragment(ViewType.PREFECTURE, null)
        toolbar.setNavigationOnClickListener { consumeBackPress() }
    }

    private fun replaceFragment(viewType: ViewType, postalCode: PostalCode?) {
        childFragmentManager
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.container, getFragment(viewType, postalCode))
                .addToBackStack(null)
                .commit()

        titles.add(getTitle(viewType, postalCode))
        updateToolbar()
    }

    private fun getFragment(viewType: ViewType, postalCode: PostalCode?): Fragment =
            when (viewType) {
                ViewType.PREFECTURE -> PostalCodeListFragment.createPrefectureInstance()
                ViewType.CITY -> PostalCodeListFragment.createCityInstance(postalCode!!.prefectureId)
                ViewType.STREET -> PostalCodeListFragment.createStreetInstance(postalCode!!.cityId)
                ViewType.DETAIL -> throw IllegalStateException("This viewType: $viewType is not supported.")
            }

    private fun getTitle(viewType: ViewType, postalCode: PostalCode?): String =
            when (viewType) {
                ViewType.PREFECTURE -> getString(R.string.title_prefecture)
                ViewType.CITY -> postalCode!!.prefecture
                ViewType.STREET -> postalCode!!.city
                ViewType.DETAIL -> throw IllegalStateException("This viewType: $viewType is not supported.")
            }

    private fun updateToolbar() {
        toolbar.title = titles.last()
        toolbar.navigationIcon = if (titles.size == 1) null else getBackIcon()
    }

    private fun getBackIcon(): Drawable =
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_back_black_24dp)!!.also {
                it.setTint(ContextCompat.getColor(requireContext(), R.color.white))
                it.setTintMode(PorterDuff.Mode.SRC_IN)
            }

    fun consumeBackPress(): Boolean {
        if (childFragmentManager.backStackEntryCount > 1) {
            childFragmentManager.popBackStack()
            titles.removeAt(titles.lastIndex)
            updateToolbar()
            return true
        }

        return false
    }

    // region PostalCodeListFragment.Callback

    override fun onItemClick(viewType: ViewType, postalCode: PostalCode) {
        if (viewType == ViewType.DETAIL) {
            PostalCodeDetailActivity.start(requireContext(), postalCode)
        } else {
            replaceFragment(viewType, postalCode)
        }
    }

    // endregion
}