package com.dicoding.githubappsub3.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.githubappsub3.ui.FollowersFragment
import com.dicoding.githubappsub3.ui.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity, private val username: String?) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FollowingFragment.newInstance(username)
            1 -> FollowersFragment.newInstance(username)
            else -> Fragment()
        }
    }
}