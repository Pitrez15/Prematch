package com.android.ipca.prematch.main

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.android.ipca.prematch.R
import com.android.ipca.prematch.helpers.VolleyHelper
import com.android.ipca.prematch.models.TournamentModel
import kotlinx.android.synthetic.main.activity_tournament_favorites.*
import org.json.JSONObject

class TournamentFavoritesActivity : AppCompatActivity() {

    var tournaments : MutableList<TournamentModel> = ArrayList()
    var tournamentAdapter : TournamentAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tournament_favorites)

        tournamentHomeTournamentButton.setBackgroundResource(R.drawable.button_border_selected)

        tournamentAdapter = TournamentAdapter()
        favoriteTournamentsListView.adapter = tournamentAdapter

        VolleyHelper.instance.getTournaments(this) { response ->

            response?.let {

                for (index in 0 until it.length()) {

                    val tournamentsJSON : JSONObject = it[index] as JSONObject
                    tournaments.add(TournamentModel.parseJSON(tournamentsJSON))
                }
                tournamentAdapter?.notifyDataSetChanged()
            }
        }

        addTournamentButton.setOnClickListener {

            val intent = Intent(this, TournamentNewActivity::class.java)
            intent.putExtra("Tournament ID", tournaments.size)
            startActivityForResult(intent, 1002)
        }

        teamHomeTournamentButton.setOnClickListener {

            val intent = Intent(this, TeamFavoritesActivity::class.java)
            startActivity(intent)
        }

        playerHomeTournamentButton.setOnClickListener {

            val intent = Intent(this, PlayerFavoritesActivity::class.java)
            startActivity(intent)
        }

        settingsTournamentButton.setOnClickListener {

            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    inner class TournamentAdapter : BaseAdapter() {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            val rowView = layoutInflater.inflate(R.layout.row_tournament, parent, false)

            val textViewTournamentName = rowView.findViewById<TextView>(R.id.tournamentTitleRowTextView)
            val textViewTournamentStartDate = rowView.findViewById<TextView>(R.id.tournamentStartDateRowTextView)
            val textViewTournamentFinishDate = rowView.findViewById<TextView>(R.id.tournamentFinishDateRowTextView)
            val textViewTournamentTeamsNumber = rowView.findViewById<TextView>(R.id.tournamentTeamsNumberRowTextView)
            val textViewTournamentType = rowView.findViewById<TextView>(R.id.tournamentTypeRowTextView)
            //val textViewTournamentContactEmail = rowView.findViewById<TextView>(R.id.tournamentContactEmailRowTextView)
            //val textViewTournamentContactPhone = rowView.findViewById<TextView>(R.id.tournamentContactPhoneRowTextView)

            textViewTournamentName.text = tournaments[position].tournamentName
            textViewTournamentStartDate.text = tournaments[position].startDate
            textViewTournamentFinishDate.text = tournaments[position].finishDate
            textViewTournamentTeamsNumber.text = tournaments[position].teamsNumber.toString()
            textViewTournamentType.text = tournaments[position].tournamentType
            //textViewTournamentContactEmail.text = tournaments[position].contactEmail
            //textViewTournamentContactPhone.text = tournaments[position].contactPhone.toString()

            rowView.setOnClickListener {

                val intent = Intent(this@TournamentFavoritesActivity, TournamentDetailActivity::class.java)

                /*intent.putExtra("Tournament Name", tournaments[position].tournamentName)
                intent.putExtra("Start Date", tournaments[position].startDate)
                intent.putExtra("Finish Date", tournaments[position].finishDate)
                //intent.putExtra("Contact Email", tournaments[position].contactEmail)
                //intent.putExtra("Contact Phone", tournaments[position].contactPhone)
                intent.putExtra("Teams Number", tournaments[position].teamsNumber)
                intent.putExtra("Tournament Type", tournaments[position].tournamentType)*/

                intent.putExtra("Tournament ID", position + 1)

                startActivity(intent)
            }

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

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode === Activity.RESULT_OK) {

            if (requestCode == 1002) {

                data?.extras?.let {

                    val tournamentName = it.getString(TournamentNewActivity.TOURNAMENT_NAME)
                    val tournamentStartDate = it.getString(TournamentNewActivity.START_DATE)
                    val tournamentFinishDate = it.getString(TournamentNewActivity.FINISH_DATE)
                    val tournamentContactEmail = it.getString(TournamentNewActivity.CONTACT_EMAIL)
                    val tournamentContactPhone = it.getInt(TournamentNewActivity.CONTACT_PHONE)
                    val tournamentTeamsNumber = it.getInt(TournamentNewActivity.TEAMS_NUMBER)
                    val tournamentType = it.getString(TournamentNewActivity.TOURNAMENT_TYPE)

                    val tournament = TournamentModel()

                    tournament.tournamentName = tournamentName
                    tournament.startDate = tournamentStartDate
                    tournament.finishDate = tournamentFinishDate
                    tournament.contactEmail = tournamentContactEmail
                    tournament.contactPhone = tournamentContactPhone
                    tournament.teamsNumber = tournamentTeamsNumber
                    tournament.tournamentType = tournamentType

                    tournaments.add(tournament)

                    tournamentAdapter?.notifyDataSetChanged()
                }
            }
        }
    }*/
}