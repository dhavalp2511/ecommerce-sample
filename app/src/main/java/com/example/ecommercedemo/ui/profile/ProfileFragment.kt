package com.example.ecommercedemo.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.ecommercedemo.R
import com.example.ecommercedemo.common.UiState
import com.example.ecommercedemo.databinding.FragmentProfileBinding
import com.example.ecommercedemo.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val profileViewModel: ProfileViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.viewModel = profileViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileViewModel.getProfile()
        viewModelSetup()
        binding.toolbar.toolbar.title = getString(R.string.profile)
        binding.btnLogout.setOnClickListener {
            profileViewModel.logout()
            launchLoginActivity()
        }
    }

    private fun viewModelSetup() {
        profileViewModel.profileLiveData.observe(viewLifecycleOwner) {
            binding.apply {
                when (it) {
                    is UiState.Error -> {
                        progressbar.isVisible = false
                        Toast.makeText(requireContext(), it.throwable.message, Toast.LENGTH_SHORT)
                            .show()
                    }

                    is UiState.Loading -> {
                        progressbar.isVisible = true
                    }

                    is UiState.Success -> {
                        val profile = it.data
                        progressbar.isVisible = false
                        firstName.label.text = getString(R.string.first_name)
                        firstName.value.text = profile.firstname
                        lastName.label.text = getString(R.string.last_name)
                        lastName.value.text = profile.lastName
                        userName.label.text = getString(R.string.user_name)
                        userName.value.text = profile.username
                        dob.label.text = getString(R.string.date_of_birth)
                        dob.value.text = profile.dob
                        address.label.text = getString(R.string.address)
                        address.value.text = profile.address
                        rewards.label.text = getString(R.string.points_earned)
                        rewards.value.text = profile.pointsEarned
                        wallet.label.text = getString(R.string.wallet_balance)
                        wallet.value.text = getString(R.string.product_price, profile.walletBalance)
                    }
                }
            }


        }


    }

    private fun launchLoginActivity() {
        startActivity(Intent(requireActivity(), LoginActivity::class.java))
        requireActivity().finish()
    }
}