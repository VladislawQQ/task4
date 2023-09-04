package com.example.messengerApp.ui.authentication.splash_screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.messengerApp.base.BaseFragment
import com.example.messengerApp.databinding.FragmentSplashScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenFragment : BaseFragment<FragmentSplashScreenBinding>(FragmentSplashScreenBinding::inflate) {

    // TODO: add checkbox and if viewModel.isCheckBox == true go to MyProfile else go Login
    private val viewModel : SplashScreenViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        openRegisterFragment()
    }

    private fun openRegisterFragment() {
        val direction = SplashScreenFragmentDirections.actionSplashScreenFragmentToAuthenticationFragment()

        navController.navigate(direction)
    }
}