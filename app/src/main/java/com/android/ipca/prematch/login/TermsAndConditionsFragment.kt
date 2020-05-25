package com.android.ipca.prematch.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.android.ipca.prematch.R
import com.android.ipca.prematch.databinding.FragmentTermsAndConditionsBinding

class TermsAndConditionsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentTermsAndConditionsBinding>(inflater, R.layout.fragment_terms_and_conditions, container, false)

        return binding.root
    }
}
