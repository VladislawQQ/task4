package com.example.task4.ui.main.viewpager

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.task4.R
import com.example.task4.databinding.FragmentViewPagerBinding
import com.example.task4.base.BaseFragment
import com.example.task4.ui.main.viewpager.myContacts.MyContactsFragment
import com.example.task4.ui.main.viewpager.myProfile.MyProfileFragment
import com.example.task4.ui.utils.Constants.SCREENS.*
import com.google.android.material.tabs.TabLayoutMediator

class ViewPagerFragment
    : BaseFragment<FragmentViewPagerBinding>(FragmentViewPagerBinding::inflate) {

    private val args : ViewPagerFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewPager()
    }

    private fun setViewPager() {
        with(binding) {
            viewPager.adapter = ViewPagerAdapter(this@ViewPagerFragment)
            viewPager.offscreenPageLimit = 1
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = when(values()[position]) {
                    PROFILE_SCREEN -> getString(R.string.fragment_my_profile_name) // TODO: to constants
                    CONTACTS_SCREEN -> getString(R.string.fragment_my_contacts_name)
                }
            }.attach()
        }
    }

    fun openTab(index : Int) {
        binding.viewPager.currentItem = index
    }


    inner class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) { // TODO: to another class (single responsibility)
        override fun createFragment(position: Int): Fragment {
            return when(values()[position]) {
                PROFILE_SCREEN -> {
                    val myProfileFragment = MyProfileFragment()
                    myProfileFragment.arguments = args.toBundle()
                    myProfileFragment
                }
                CONTACTS_SCREEN -> MyContactsFragment()
            }
        }
        override fun getItemCount(): Int = values().size
    }


}