package com.android.ipca.prematch.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.android.ipca.prematch.R
import com.android.ipca.prematch.helpers.VolleyHelper
import com.android.ipca.prematch.models.TeamModel
import com.android.ipca.prematch.models.TournamentModel
import kotlinx.android.synthetic.main.activity_team_favorites.*
import kotlinx.android.synthetic.main.activity_tournament_favorites.*
import org.json.JSONObject

class TeamFavoritesActivity : AppCompatActivity() {

    var username : String? = null

    var teams : MutableList<TeamModel> = ArrayList()
    var teamAdapter : TeamFavoritesActivity.TeamAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_favorites)

        val bundle = intent.extras
        bundle?.let {

            username = it.getString("Username")
        }

        teamHomeTeamButton.setBackgroundResource(R.drawable.button_border_selected)

        teamAdapter = TeamAdapter()
        favoriteTeamsListView.adapter = teamAdapter

        VolleyHelper.instance.getTeams(this) { response ->

            response?.let {

                for (index in 0 until it.length()) {

                    val teamJSON : JSONObject = it[index] as JSONObject
                    teams.add(TeamModel.parseJSON(teamJSON))
                }
                teamAdapter?.notifyDataSetChanged()
            }
        }

        tournamentHomeTeamButton.setOnClickListener {

            val intent = Intent(this, TournamentFavoritesActivity::class.java)
            intent.putExtra("Username", username!!)
            startActivity(intent)
        }

        playerHomeTeamButton.setOnClickListener {

            val intent = Intent(this, PlayerFavoritesActivity::class.java)
            intent.putExtra("Username", username!!)
            startActivity(intent)
        }

        settingsTeamButton.setOnClickListener {

            val intent = Intent(this, SettingsActivity::class.java)
            intent.putExtra("Username", username!!)
            startActivity(intent)
        }
    }

    inner class TeamAdapter : BaseAdapter() {

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

            /*rowView.setOnClickListener {

                val intent = Intent(this@TeamFavoritesActivity, TeamDetailActivity::class.java)
                intent.putExtra("Team ID", teams[position].teamID)
                intent.putExtra("Tournament ID", teams[position].teamTournamentID)
                startActivity(intent)
            }*/

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
