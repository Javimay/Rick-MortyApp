package com.javimay.rickmortyapp.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.javimay.rickmortyapp.R
import com.javimay.rickmortyapp.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private lateinit var binding : FragmentSplashBinding

    private val splashViewModel: SplashFragmentViewModel by viewModels()
    private lateinit var navController : NavController
    private var animationObserver = Observer<Boolean> { splashViewModel.animFinished }
    private var dataObserver = Observer<Boolean> { splashViewModel.animFinished }
    private var animFinished = false
    private var dataDownloaded = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        navController = findNavController()
        splashViewModel.animFinished.observe(viewLifecycleOwner) {
            if (it) verifyData()
        }
        splashViewModel.logoAppear(binding.ivLogo)
        return binding.root
    }

    private fun verifyData() {
        if (dataDownloaded && animFinished) goToHomeScreen()
    }

    private fun goToHomeScreen() {
        navController.navigate(R.id.action_splashFragment_to_homeFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        splashViewModel.animFinished.removeObserver(animationObserver)
    }
}