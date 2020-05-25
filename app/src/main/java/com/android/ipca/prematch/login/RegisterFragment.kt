package com.android.ipca.prematch.login

import android.graphics.Color
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController

import com.android.ipca.prematch.R
import com.android.ipca.prematch.databinding.FragmentIntroBinding
import com.android.ipca.prematch.databinding.FragmentRegisterBinding
import com.android.ipca.prematch.databinding.FragmentTermsAndConditionsBinding

class RegisterFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentRegisterBinding>(inflater, R.layout.fragment_register, container, false)

        binding.termsClickableTextView.setOnClickListener {

            view?.findNavController()?.navigate(RegisterFragmentDirections.actionRegisterFragmentToTermsAndConditionsFragment())
        }

        return binding.root
    }
}
