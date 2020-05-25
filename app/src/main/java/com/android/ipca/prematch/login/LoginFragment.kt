package com.android.ipca.prematch.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController

import com.android.ipca.prematch.R
import com.android.ipca.prematch.databinding.FragmentLoginBinding
import com.android.ipca.prematch.main.TournamentMainActivity

class LoginFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(inflater, R.layout.fragment_login, container, false)

        binding.loginButton.setOnClickListener {

            val intent = Intent(context, TournamentMainActivity::class.java)
            startActivity(intent)
        }

        binding.createAccountClickableTextView.setOnClickListener {

            view?.findNavController()?.navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

        return binding.root
    }
}
