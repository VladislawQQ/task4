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
import com.example.task4.ui.utils.constants.Constants.FRAGMENT_COUNT
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
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = when(position) {
                    0 -> getString(R.string.fragment_my_profile_name)
                    1 -> getString(R.string.fragment_my_contacts_name)
                    else -> throw IllegalStateException()
                }
            }.attach()
        }
    }

    fun openTab(index : Int) {
        binding.viewPager.currentItem = index
    }

    inner class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> {
                    val myProfileFragment = MyProfileFragment()
                    myProfileFragment.arguments = args.toBundle()
                    myProfileFragment
                }
                1 -> MyContactsFragment()
                else -> throw IllegalStateException()
            }
        }

        override fun getItemCount(): Int = FRAGMENT_COUNT
    }

}