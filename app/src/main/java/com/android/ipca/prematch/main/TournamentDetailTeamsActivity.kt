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
import com.android.ipca.prematch.models.TeamModel
import kotlinx.android.synthetic.main.activity_tournament_detail_teams.*

private var TEAMS_NUMBER: String? = null

class TournamentDetailTeamsActivity : AppCompatActivity() {

    var teams : MutableList<TeamModel> = ArrayList<TeamModel>()
    private var teamsAdapter : TeamsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tournament_detail_teams)

        val bundle = intent.extras
        bundle?.let {

            TEAMS_NUMBER = it.getString("Team Number")
        }

        teamsAdapter = TeamsAdapter()
        tournamentDetailTeamsListView.adapter = teamsAdapter

        tournamentDetailAddTeamButton.setOnClickListener {

            val intent = Intent(this, TeamNewActivity::class.java)
            startActivityForResult(intent, 1002)

            /*if (teams.size >= TEAMS_NUMBER?.toInt()!!) {

                Toast.makeText(applicationContext,"You can't add more teams !",Toast.LENGTH_SHORT).show()
            }

            else {

                val intent = Intent(this, TeamNewActivity::class.java)
                startActivityForResult(intent, 1002)
            }*/
        }
    }

    inner class TeamsAdapter : BaseAdapter() {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            var rowTeamView = layoutInflater.inflate(R.layout.row_team, parent, false)

            val textViewTournamentDetailTeamName = rowTeamView.findViewById<TextView>(R.id.tournamentDetailTeamNameRowTextView)
            val textViewTournamentDetailTeamInitials = rowTeamView.findViewById<TextView>(R.id.tournamentDetailTeamInitialsRowTextView)
            val textViewTournamentDetailTeamCity = rowTeamView.findViewById<TextView>(R.id.tournamentDetailTeamCityRowTextView)
            val textViewTournamentDetailTeamPrimaryColor = rowTeamView.findViewById<TextView>(R.id.tournamentDetailTeamPrimaryRowTextView)
            val textViewTournamentDetailTeamSecondaryColor = rowTeamView.findViewById<TextView>(R.id.tournamentDetailTeamSecondaryRowTextView)
            //val textViewTournamentDetailTeamContactEmail = rowTeamView.findViewById<TextView>(R.id.tournamentDetailTeamContactEmailRowTextView)
            //val textViewTournamentDetailTeamContactPhone = rowTeamView.findViewById<TextView>(R.id.tournamentDetailTeamContactPhoneRowTextView)

            textViewTournamentDetailTeamName.text = teams[position].teamName
            textViewTournamentDetailTeamInitials.text = teams[position].teamInitials
            textViewTournamentDetailTeamCity.text = teams[position].teamCity
            textViewTournamentDetailTeamPrimaryColor.text = teams[position].teamPrimaryColor
            textViewTournamentDetailTeamSecondaryColor.text = teams[position].teamSecondaryColor
            //textViewTournamentDetailTeamContactEmail.text = teams[position].teamContactEmail
            //textViewTournamentDetailTeamContactPhone.text = teams[position].teamContactPhone

            /*rowTeamView.setOnClickListener {

                val intent = Intent(this@TournamentDetailTeamsActivity, ::class.java)

                intent.putExtra("Team Name", teams[position].teamName)
                intent.putExtra("Team Initials", teams[position].teamInitials)
                intent.putExtra("Team City", teams[position].teamCity)
                intent.putExtra("Primary Color", teams[position].teamPrimaryColor)
                intent.putExtra("Secondary Color", teams[position].teamSecondaryColor)
                intent.putExtra("Team Email", teams[position].teamContactEmail)
                intent.putExtra("Team Phone", teams[position].teamContactPhone)
                startActivity(intent)
            }*/

            return rowTeamView
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode === Activity.RESULT_OK) {

            if (requestCode == 1002) {

                data?.extras?.let {

                    val teamName : String? = it.getString(TeamNewActivity.TEAM_NAME)
                    val teamInitials = it.getString(TeamNewActivity.TEAM_INITIALS)
                    val teamCity : String? = it.getString(TeamNewActivity.TEAM_CITY)
                    val teamPrimaryColor = it.getString(TeamNewActivity.TEAM_PRIMARY_COLOR)
                    val teamSecondaryColor = it.getString(TeamNewActivity.TEAM_SECONDARY_COLOR)
                    val teamContactEmail = it.getString(TeamNewActivity.TEAM_CONTACT_EMAIL)
                    val teamContactPhone = it.getString(TeamNewActivity.TEAM_CONTACT_PHONE)

                    val team = TeamModel()

                    team.teamName = teamName
                    team.teamInitials = teamInitials
                    team.teamCity = teamCity
                    team.teamPrimaryColor = teamPrimaryColor
                    team.teamSecondaryColor = teamSecondaryColor
                    team.teamContactEmail = teamContactEmail
                    //team.teamContactPhone = teamContactPhone

                    teams.add(team)

                    teamsAdapter?.notifyDataSetChanged()
                }
            }
        }
    }
}