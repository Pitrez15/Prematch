package com.android.ipca.prematch.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.android.ipca.prematch.R
import com.android.ipca.prematch.databinding.FragmentNewTeamBinding

class NewTeamFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentNewTeamBinding>(inflater, R.layout.fragment_new_team, container, false)

        binding.backTeamButton.setOnClickListener {

            view?.findNavController()?.navigate(NewTeamFragmentDirections.actionNewTeamFragmentToTeamFavoritesFragment())
        }

        return binding.root
    }
}
