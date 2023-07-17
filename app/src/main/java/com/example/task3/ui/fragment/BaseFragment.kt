package com.example.task3.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VBinding : ViewBinding>
    (private val inflate : (LayoutInflater, ViewGroup?, Boolean) -> VBinding)
    : Fragment() {

    private var _binding : VBinding? = null

    // This can be accessed by the child fragments
    // Only valid between onCreateView and onDestroyView
    protected val binding : VBinding get() = _binding!!

    protected val navController : NavController
        get() = findNavController()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(layoutInflater, container, false)
        return binding.root
    }

    // Removing the binding reference when not needed is recommended as it avoids memory leak
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}