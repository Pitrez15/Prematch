package com.android.ipca.prematch.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController

import com.android.ipca.prematch.R
import com.android.ipca.prematch.databinding.FragmentNewTournamentBinding
import kotlinx.android.synthetic.main.activity_tournament_main.*

class NewTournamentFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentNewTournamentBinding>(inflater, R.layout.fragment_new_tournament, container, false)

        binding.backTournamentButton.setOnClickListener {

            view?.findNavController()?.navigate(NewTournamentFragmentDirections.actionNewTournamentFragmentToTournamentFavoritesFragment())
        }

        binding.confirmTournamentButton.setOnClickListener {

            val tournamentFavoritesFragment = TournamentFavoritesFragment()
            val bundle = Bundle()

            val fragmentTransaction = fragmentManager?.beginTransaction()

            bundle.putString(TOURNAMENT_NAME, binding.enterTournamentNameEditText.text.toString())
            bundle.putString(TOURNAMENT_CITY, binding.enterTournamentCityEditText.text.toString())
            bundle.putString(START_DATE, binding.enterStartDateEditText.text.toString())
            bundle.putString(FINISH_DATE, binding.enterFinishDateEditText.text.toString())
            bundle.putString(CONTACT_EMAIL, binding.tournamentContactEmailEditText.text.toString())
            bundle.putString(CONTACT_PHONE, binding.tournamentContactPhoneEditText.text.toString())
            bundle.putString(TEAMS_NUMBER, binding.enterTeamNumberEditText.text.toString())
            bundle.putString(TOURNAMENT_TYPE, binding.enterTournamentTypeEditText.text.toString())

            tournamentFavoritesFragment.arguments = bundle

            //view?.findNavController()?.navigate(NewTournamentFragmentDirections.actionNewTournamentFragmentToTournamentFavoritesFragment())

            fragmentTransaction?.replace(R.id.nav_host_fragment, tournamentFavoritesFragment)
            fragmentTransaction?.commit()

            /*val tournamentFavoritesFragment = NewTournamentFragment.newInstance(

                binding.enterTournamentNameEditText.text.toString(),
                binding.enterTournamentCityEditText.text.toString(),
                binding.enterStartDateEditText.text.toString(),
                binding.enterFinishDateEditText.text.toString(),
                binding.tournamentContactEmailEditText.text.toString(),
                binding.tournamentContactPhoneEditText.text.toString(),
                binding.enterTeamNumberEditText.text.toString(),
                binding.enterTournamentTypeEditText.text.toString()
            )*/
        }

        return binding.root
    }

    companion object {

        var TOURNAMENT_NAME: String? = "Tournament Name"
        var TOURNAMENT_CITY: String? = "Tournament City"
        var START_DATE: String? = "Start Date"
        var FINISH_DATE: String? = "Finish Date"
        var CONTACT_EMAIL: String? = "Contact Email"
        var CONTACT_PHONE: String? = "Contact Phone"
        var TEAMS_NUMBER: String? = "Teams Number"
        var TOURNAMENT_TYPE: String? = "Tournament Type"
    }
}
