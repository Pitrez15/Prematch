package com.android.ipca.prematch.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.android.ipca.prematch.R
import com.android.ipca.prematch.models.TeamModel
import com.android.ipca.prematch.models.TournamentModel
import kotlinx.android.synthetic.main.activity_team_favorites.*
import kotlinx.android.synthetic.main.activity_tournament_favorites.*

class TeamFavoritesActivity : AppCompatActivity() {

    //var teams : MutableList<TeamModel> = ArrayList<TeamModel>()
    //var teamAdapter : TeamFavoritesActivity.TeamAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_favorites)

        //teamAdapter = TeamAdapter()
        //favoriteTeamsListView.adapter = teamAdapter

        teamHomeTeamButton.setBackgroundResource(R.drawable.button_border_selected)

        addTeamButton.setOnClickListener {

            val intent = Intent(this, TeamNewActivity::class.java)
            startActivityForResult(intent, 1002)
        }

        tournamentHomeTeamButton.setOnClickListener {

            val intent = Intent(this, TournamentFavoritesActivity::class.java)
            startActivity(intent)
        }

        playerHomeTeamButton.setOnClickListener {

            val intent = Intent(this, PlayerFavoritesActivity::class.java)
            startActivity(intent)
        }

        settingsTeamButton.setOnClickListener {

            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    /*inner class TeamAdapter : BaseAdapter() {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        }

        override fun getItem(position: Int): Any {
            TODO("Not yet implemented")
        }

        override fun getItemId(position: Int): Long {
            TODO("Not yet implemented")
        }

        override fun getCount(): Int {
            TODO("Not yet implemented")
        }
    }*/
}
