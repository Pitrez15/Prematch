package com.android.ipca.prematch.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.android.ipca.prematch.R
import com.android.ipca.prematch.databinding.FragmentMyTournamentsBinding

class MyTournamentsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentMyTournamentsBinding>(inflater, R.layout.fragment_my_tournaments, container, false)

        return binding.root
    }
}