package com.example.task3.ui.fragment.viewpager.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.task3.ui.fragment.myProfile.MyProfileFragment
import com.example.task3.ui.utils.Constants.FRAGMENT_COUNT

class ViewPagerAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                val myProfileFragment = MyProfileFragment()
                myProfileFragment.arguments =
            }
            1 -> {

            }
            else -> throw IllegalStateException()
        }
    }

    override fun getItemCount(): Int = FRAGMENT_COUNT
}
