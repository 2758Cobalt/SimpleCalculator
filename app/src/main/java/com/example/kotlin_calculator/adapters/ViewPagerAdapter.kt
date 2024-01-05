package com.example.kotlin_calculator.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fa: FragmentActivity, private var list: List<Fragment>): FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return list[position]
    }
    fun replace(position: Int, newFragment: Fragment) {
        list = list.toMutableList().apply { set(position, newFragment) }
        notifyItemChanged(position)
    }
}