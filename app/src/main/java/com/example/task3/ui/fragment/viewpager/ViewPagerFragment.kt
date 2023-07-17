package com.example.task3.ui.fragment.viewpager

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.task3.R
import com.example.task3.databinding.FragmentViewPagerBinding
import com.example.task3.ui.fragment.BaseFragment
import com.example.task3.ui.fragment.myContacts.MyContactsFragment
import com.example.task3.ui.fragment.myProfile.MyProfileFragment
import com.example.task3.ui.utils.Constants.FRAGMENT_COUNT
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
                    1 ->getString(R.string.fragment_my_contacts_name)
                    else -> throw IllegalStateException()
                }
            }.attach()
        }
    }

    inner class ViewPagerAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {
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