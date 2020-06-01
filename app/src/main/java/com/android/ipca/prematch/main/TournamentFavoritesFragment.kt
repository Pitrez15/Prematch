package com.android.ipca.prematch.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController

import com.android.ipca.prematch.R
import com.android.ipca.prematch.databinding.FragmentTournamentFavoritesBinding
import com.android.ipca.prematch.models.TournamentModel
import kotlinx.android.synthetic.main.fragment_tournament_favorites.*

class TournamentFavoritesFragment : Fragment() {

    var tournaments : MutableList<TournamentModel> = ArrayList<TournamentModel>()
    private var tournamentsAdapter : TournamentsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentTournamentFavoritesBinding>(inflater, R.layout.fragment_tournament_favorites, container, false)

        tournamentsAdapter = TournamentsAdapter()
        favoriteTournamentsListView?.adapter = tournamentsAdapter

        binding.addTournamentButton.setOnClickListener {

            view?.findNavController()?.navigate(TournamentFavoritesFragmentDirections.actionTournamentFavoritesFragmentToNewTournamentFragment())
        }

        return binding.root
    }

    inner class TournamentsAdapter : BaseAdapter() {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            var rowView = layoutInflater.inflate(R.layout.row_tournament, parent, false)

            val textViewTournamentName = rowView.findViewById<TextView>(R.id.tournamentTitleRowTextView)
            val textViewTournamentCity = rowView.findViewById<TextView>(R.id.tournamentCityRowTextView)
            val textViewTournamentStartDate = rowView.findViewById<TextView>(R.id.tournamentStartDateRowTextView)
            val textViewTournamentFinishDate = rowView.findViewById<TextView>(R.id.tournamentFinishDateRowTextView)
            val textViewTournamentTeamsNumber = rowView.findViewById<TextView>(R.id.tournamentTeamsNumberRowTextView)
            val textViewTournamentType = rowView.findViewById<TextView>(R.id.tournamentTypeRowTextView)
            val textViewTournamentContactEmail = rowView.findViewById<TextView>(R.id.tournamentContactEmailRowTextView)
            val textViewTournamentContactPhone = rowView.findViewById<TextView>(R.id.tournamentContactPhoneRowTextView)

            textViewTournamentName.text = tournaments[position].tournamentName
            textViewTournamentCity.text = tournaments[position].tournamentCity
            textViewTournamentStartDate.text = tournaments[position].startDate
            textViewTournamentFinishDate.text = tournaments[position].finishDate
            textViewTournamentTeamsNumber.text = tournaments[position].teamsNumber
            textViewTournamentType.text = tournaments[position].tournamentType
            textViewTournamentContactEmail.text = tournaments[position].contactEmail
            textViewTournamentContactPhone.text = tournaments[position].contactPhone

            return rowView
        }

        override fun getItem(position: Int): Any {

            return tournaments[position]
        }

        override fun getItemId(position: Int): Long {

            return 0
        }

        override fun getCount(): Int {

            return tournaments.size
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode === Activity.RESULT_OK) {

            if (requestCode == 1002) {

                data?.extras?.let {

                    val tournamentName : String? = it.getString(NewTournamentFragment.TOURNAMENT_NAME)
                    val tournamentCity = it.getString(NewTournamentFragment.TOURNAMENT_CITY)
                    val tournamentStartDate = it.getString(NewTournamentFragment.START_DATE)
                    val tournamentFinishDate : String? = it.getString(NewTournamentFragment.FINISH_DATE)
                    val tournamentContactEmail = it.getString(NewTournamentFragment.CONTACT_EMAIL)
                    val tournamentContactPhone = it.getString(NewTournamentFragment.CONTACT_PHONE)
                    val tournamentTeamsNumber = it.getString(NewTournamentFragment.TEAMS_NUMBER)
                    val tournamentType = it.getString(NewTournamentFragment.TOURNAMENT_TYPE)

                    val tournament = TournamentModel()

                    tournament.tournamentName = tournamentName
                    tournament.tournamentCity = tournamentCity
                    tournament.startDate = tournamentStartDate
                    tournament.finishDate = tournamentFinishDate
                    tournament.contactEmail = tournamentContactEmail
                    tournament.contactPhone = tournamentContactPhone
                    tournament.teamsNumber = tournamentTeamsNumber
                    tournament.tournamentType = tournamentType

                    tournaments.add(tournament)

                    tournamentsAdapter?.notifyDataSetChanged()
                }
            }
        }
    }
}
