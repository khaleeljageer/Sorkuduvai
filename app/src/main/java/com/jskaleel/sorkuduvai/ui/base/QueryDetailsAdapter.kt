package com.jskaleel.sorkuduvai.ui.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jskaleel.sorkuduvai.ui.fragments.query.QueryDetailFragment
import com.jskaleel.sorkuduvai.ui.fragments.recent.RecentSearchesFragment

class QueryDetailsAdapter(
    fragmentActivity: FragmentActivity,
    private val tabList: MutableList<String>
) : FragmentStateAdapter(fragmentActivity) {

    private val pageIds = tabList.map { it.hashCode().toLong() }

    override fun getItemCount(): Int = tabList.size

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            RecentSearchesFragment.newInstance(position)
        } else {
            QueryDetailFragment.newInstance(position, tabList[position])
        }
    }

    override fun getItemId(position: Int): Long = tabList[position].hashCode().toLong()

    fun addItem(query: String) {
        tabList.add(query)
        notifyItemInserted(tabList.lastIndex)
    }

    fun removeItem(index: Int) {
        if (tabList.size > index) {
            tabList.removeAt(index)
            notifyItemRangeRemoved(index, tabList.size)
        }
    }

    override fun containsItem(itemId: Long): Boolean {
        return pageIds.contains(itemId)
    }
}
