package com.ssafy.silencelake

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ssafy.silencelake.fragment.CommentFragment

private const val NUM_TABS = 2

class ProductDetailViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return OrderFragment()
            1 -> return CommentFragment()
        }
        return OrderFragment()
    }
}