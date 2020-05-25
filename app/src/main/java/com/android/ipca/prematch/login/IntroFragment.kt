package com.android.ipca.prematch.login

import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.android.ipca.prematch.R
import com.android.ipca.prematch.databinding.FragmentIntroBinding
import java.util.Timer
import kotlin.concurrent.schedule
import java.util.*

class IntroFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentIntroBinding>(inflater, R.layout.fragment_intro, container, false)

        /*binding.appNameText.setOnClickListener {

            view?.findNavController()?.navigate(IntroFragmentDirections.actionIntroFragmentToLoginFragment())
        }*/

        Handler().postDelayed({

            view?.findNavController()?.navigate(IntroFragmentDirections.actionIntroFragmentToLoginFragment())
        },3000)

        return binding.root
    }
}
