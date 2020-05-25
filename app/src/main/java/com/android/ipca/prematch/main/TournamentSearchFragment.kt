package com.android.ipca.prematch.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.android.ipca.prematch.R
import com.android.ipca.prematch.databinding.FragmentTournamentSearchBinding

class TournamentSearchFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentTournamentSearchBinding>(inflater, R.layout.fragment_tournament_search, container, false)

        return binding.root
    }
}
