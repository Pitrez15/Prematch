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

private const val ARG_TOURNAMENT_NAME = "Tournament Name"
private const val ARG_TOURNAMENT_CITY = "Tournament City"
private const val ARG_START_DATE = "Start Date"
private const val ARG_FINISH_DATE = "Finish Date"
private const val ARG_CONTACT_EMAIL = "Contact Email"
private const val ARG_CONTACT_PHONE = "Contact Phone"
private const val ARG_TEAMS_NUMBER = "Teams Number"
private const val ARG_TOURNAMENT_TYPE = "Tournament Type"

class TournamentFavoritesFragment : Fragment() {

    private var tournamentName: String? = null
    private var tournamentCity: String? = null
    private var startDate: String? = null
    private var finishDate: String? = null
    private var contactEmail: String? = null
    private var contactPhone: String? = null
    private var teamsNumber: String? = null
    private var tournamentType: String? = null

    var tournaments : MutableList<TournamentModel> = ArrayList<TournamentModel>()
    private var tournamentsAdapter : TournamentsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

            tournamentName = it.getString(ARG_TOURNAMENT_NAME)
            tournamentCity = it.getString(ARG_TOURNAMENT_CITY)
            startDate = it.getString(ARG_START_DATE)
            finishDate = it.getString(ARG_FINISH_DATE)
            contactEmail = it.getString(ARG_CONTACT_EMAIL)
            contactPhone = it.getString(ARG_CONTACT_PHONE)
            teamsNumber = it.getString(ARG_TEAMS_NUMBER)
            tournamentType = it.getString(ARG_TOURNAMENT_TYPE)
        }
    }

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
            val textViewTournamentStartDate = rowView.findViewById<TextView>(R.id.tournamentStartDateRowTextView)
            val textViewTournamentFinishDate = rowView.findViewById<TextView>(R.id.tournamentFinishDateRowTextView)
            val textViewTournamentTeamsNumber = rowView.findViewById<TextView>(R.id.tournamentTeamsNumberRowTextView)
            val textViewTournamentType = rowView.findViewById<TextView>(R.id.tournamentTypeRowTextView)
            val textViewTournamentContactEmail = rowView.findViewById<TextView>(R.id.tournamentContactEmailRowTextView)
            val textViewTournamentContactPhone = rowView.findViewById<TextView>(R.id.tournamentContactPhoneRowTextView)

            textViewTournamentName.text = tournaments[position].tournamentName
            textViewTournamentStartDate.text = tournaments[position].startDate
            textViewTournamentFinishDate.text = tournaments[position].finishDate
            textViewTournamentTeamsNumber.text = tournaments[position].teamsNumber
            textViewTournamentType.text = tournaments[position].tournamentType
            textViewTournamentContactEmail.text = tournaments[position].contactEmail
            textViewTournamentContactPhone.text = tournaments[position].contactPhone

            /*rowView.setOnClickListener {

                val intent = Intent(this@TournamentFavoritesFragment, )

                intent.putExtra("Tournament Name", tournaments[position].tournamentName)
                intent.putExtra("Tournament City", tournaments[position].tournamentCity)
                intent.putExtra("Start Date", tournaments[position].startDate)
                intent.putExtra("Finish Date", tournaments[position].finishDate)
                intent.putExtra("Contact Email", tournaments[position].contactEmail)
                intent.putExtra("Contact Phone", tournaments[position].contactPhone)
                intent.putExtra("Teams Number", tournaments[position].teamsNumber)
                intent.putExtra("Tournament Type", tournaments[position].tournamentType)
                startActivity(intent)
            }*/

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
                    val tournamentStartDate = it.getString(NewTournamentFragment.START_DATE)
                    val tournamentFinishDate : String? = it.getString(NewTournamentFragment.FINISH_DATE)
                    val tournamentContactEmail = it.getString(NewTournamentFragment.CONTACT_EMAIL)
                    val tournamentContactPhone = it.getString(NewTournamentFragment.CONTACT_PHONE)
                    val tournamentTeamsNumber = it.getString(NewTournamentFragment.TEAMS_NUMBER)
                    val tournamentType = it.getString(NewTournamentFragment.TOURNAMENT_TYPE)

                    val tournament = TournamentModel()

                    tournament.tournamentName = tournamentName
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
