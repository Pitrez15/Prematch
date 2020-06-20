package com.android.ipca.prematch.main

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import com.android.ipca.prematch.R
import com.android.ipca.prematch.helpers.VolleyHelper
import com.android.ipca.prematch.models.TeamModel
import kotlinx.android.synthetic.main.activity_tournament_detail_teams.*
import org.json.JSONObject

class TournamentDetailTeamsActivity : AppCompatActivity() {

    var tournamentID : Int? = null
    var teamsNumber : Int? = null
    var username : String? = null

    var allTeams : MutableList<TeamModel> = ArrayList()
    var teams : MutableList<TeamModel> = ArrayList()
    private var teamsAdapter : TeamsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tournament_detail_teams)

        val bundle = intent.extras
        bundle?.let {

            tournamentID = it.getInt("Tournament ID")
            teamsNumber = it.getInt("Teams Number")
            username = it.getString("Username")
        }

        teamsAdapter = TeamsAdapter()
        tournamentDetailTeamsListView.adapter = teamsAdapter

        VolleyHelper.instance.getTeamsByTournamentID(this, tournamentID!!.toInt()) { response ->

            response?.let {

                for (index in 0 until it.length()) {

                    val teamJSON : JSONObject = it[index] as JSONObject
                    teams.add(TeamModel.parseJSON(teamJSON))
                }
                teamsAdapter?.notifyDataSetChanged()
            }
        }

        VolleyHelper.instance.getTeams(this) { response ->

            response?.let {

                for (index in 0 until it.length()) {

                    val teamJSON : JSONObject = it[index] as JSONObject
                    allTeams.add(TeamModel.parseJSON(teamJSON))
                }
            }
        }

        tournamentDetailAddTeamButton.setOnClickListener {

            if (teams.size >= teamsNumber!!) {

                Toast.makeText(applicationContext,getString(R.string.cant_add_teams),Toast.LENGTH_SHORT).show()
            }

            else {

                val intent = Intent(this, TeamNewActivity::class.java)
                intent.putExtra("Team ID", allTeams[allTeams.size.minus(1)].teamID)
                intent.putExtra("Tournament ID", tournamentID!!.toInt())
                intent.putExtra("Teams Number", teamsNumber!!.toInt())
                intent.putExtra("Username", username!!)
                startActivity(intent)
            }
        }

        tournamentDetailBackTeamButton.setOnClickListener {

            val intent = Intent(this, TournamentDetailActivity::class.java)
            intent.putExtra("Tournament ID", tournamentID!!.toInt())
            intent.putExtra("Teams Number", teamsNumber!!)
            intent.putExtra("Username", username!!)
            startActivity(intent)
        }
    }

    inner class TeamsAdapter : BaseAdapter() {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            val rowView = layoutInflater.inflate(R.layout.row_team, parent, false)

            val textViewTeamName = rowView.findViewById<TextView>(R.id.tournamentDetailTeamNameRowTextView)
            val textViewTeamInitials = rowView.findViewById<TextView>(R.id.tournamentDetailTeamInitialsRowTextView)
            val textViewTeamCity = rowView.findViewById<TextView>(R.id.tournamentDetailTeamCityRowTextView)
            val textViewTeamPrimaryColor = rowView.findViewById<TextView>(R.id.tournamentDetailTeamPrimaryRowTextView)
            val textViewTeamSecondaryColor = rowView.findViewById<TextView>(R.id.tournamentDetailTeamSecondaryRowTextView)

            textViewTeamName.text = teams[position].teamName
            textViewTeamInitials.text = teams[position].teamInitials
            textViewTeamCity.text = teams[position].teamCity
            textViewTeamPrimaryColor.text = teams[position].teamPrimaryColor
            textViewTeamSecondaryColor.text = teams[position].teamSecondaryColor

            rowView.setOnClickListener {

                val intent = Intent(this@TournamentDetailTeamsActivity, TeamDetailActivity::class.java)
                intent.putExtra("Team ID", teams[position].teamID)
                intent.putExtra("Tournament ID", tournamentID!!.toInt())
                intent.putExtra("Teams Number", teamsNumber!!)
                intent.putExtra("Username", username!!)
                startActivity(intent)
            }

            return rowView
        }

        override fun getItem(position: Int): Any {

            return teams[position]
        }

        override fun getItemId(position: Int): Long {

            return 0
        }

        override fun getCount(): Int {

            return teams.size
        }
    }
}