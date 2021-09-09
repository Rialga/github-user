package com.example.githubuser.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuser.views.DetailActivity
import com.example.githubuser.views.FollowersFragment
import com.example.githubuser.views.FollowingFragment

class SectionsPagerAdapter(activity: DetailActivity) :  FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return  2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        return fragment as Fragment
    }
}