package com.rakuishi.postalcode2.ui

import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.util.SparseArray
import android.view.MenuItem
import com.rakuishi.postalcode2.R
import com.rakuishi.postalcode2.util.forEach

class MainBottomNavigationBehavior(bottomNavigationView: BottomNavigationView,
                                   private val fragmentManager: FragmentManager,
                                   private val containerId: Int)
    : BottomNavigationView.OnNavigationItemSelectedListener {

    private var tabs: SparseArray<Int> = SparseArray()
    private var currentPosition = TAB_LIST

    init {
        tabs.put(TAB_LIST, R.id.navigation_prefecture)
        tabs.put(TAB_SEARCH, R.id.navigation_search)
        tabs.put(TAB_BOOKMARK, R.id.navigation_bookmark)

        val itemId = tabs.get(currentPosition)
        bottomNavigationView.selectedItemId = itemId
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        onNavigationItemSelected(itemId)
    }

    private fun showFragmentAndCommitTransaction(position: Int) {
        currentPosition = position
        val transaction = fragmentManager.beginTransaction()

        for (index in 0 until TAB_COUNT) {
            val name = getFragmentName(index)
            val fragment = fragmentManager.findFragmentByTag(name)
            if (index == position) {
                // show
                if (fragment != null) {
                    transaction.show(fragment)
                } else {
                    transaction.add(containerId, getFragment(index), name)
                }
            } else {
                // hide
                if (fragment != null) {
                    transaction.hide(fragment)
                }
            }
        }

        transaction.commitAllowingStateLoss()
    }

    private fun getTabPosition(id: Int): Int {
        tabs.forEach { key, value -> if (value == id) return key }
        throw IllegalStateException("This id: $id is not supported.")
    }

    private fun getFragmentName(position: Int): String =
            "android:switcher:$containerId:$position"

    private fun getFragment(position: Int): Fragment {
        return when (position) {
            TAB_LIST -> PrefectureFragment.createInstance()
            TAB_SEARCH -> SearchFragment.createInstance()
            TAB_BOOKMARK -> BookmarkFragment.createInstance()
            else -> throw IllegalStateException("This position: $position is not supported.")
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        return onNavigationItemSelected(menuItem.itemId)
    }

    private fun onNavigationItemSelected(itemId: Int): Boolean {
        val position = getTabPosition(itemId)
        val name = getFragmentName(position)
        val fragment = fragmentManager.findFragmentByTag(name)
        if (currentPosition != position || fragment == null) {
            showFragmentAndCommitTransaction(position)
        }
        return true
    }

    companion object {
        const val TAB_LIST = 0
        const val TAB_SEARCH = 1
        const val TAB_BOOKMARK = 2
        const val TAB_COUNT = 3
    }
}