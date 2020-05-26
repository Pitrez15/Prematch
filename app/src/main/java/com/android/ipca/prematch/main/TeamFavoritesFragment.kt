package com.android.ipca.prematch.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.android.ipca.prematch.R
import com.android.ipca.prematch.databinding.FragmentTeamFavoritesBinding

class TeamFavoritesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentTeamFavoritesBinding>(inflater, R.layout.fragment_team_favorites, container, false)

        binding.addTeamButton.setOnClickListener {

            view?.findNavController()?.navigate(TeamFavoritesFragmentDirections.actionTeamFavoritesFragmentToNewTeamFragment())
        }

        return binding.root
    }
}
