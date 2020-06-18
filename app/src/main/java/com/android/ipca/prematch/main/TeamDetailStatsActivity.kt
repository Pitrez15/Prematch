package com.android.ipca.prematch.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.android.ipca.prematch.R
import com.android.ipca.prematch.helpers.VolleyHelper
import com.android.ipca.prematch.models.GameModel
import com.android.ipca.prematch.models.TeamModel
import kotlinx.android.synthetic.main.activity_team_detail_stats.*
import kotlinx.android.synthetic.main.activity_tournament_detail_stats.*
import org.json.JSONObject

class TeamDetailStatsActivity : AppCompatActivity() {

    var teamID : Int? = null
    var tournamentID : Int? = null
    var teamsNumber : Int? = null
    var username : String? = null

    var allGames : MutableList<GameModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail_stats)

        val bundle = intent.extras
        bundle?.let {

            teamID = it.getInt("Team ID")
            tournamentID = it.getInt("Tournament ID")
            teamsNumber = it.getInt("Teams Number")
            username = it.getString("Username")
        }

        val textViewTotalGames = findViewById<TextView>(R.id.numberGamesTeamTextView)
        val textViewTotalGoals = findViewById<TextView>(R.id.teamTotalGoalsTextView)
        val textViewTotalHomeGoals = findViewById<TextView>(R.id.teamTotalHomeGoalsTextView)
        val textViewTotalAwayGoals = findViewById<TextView>(R.id.teamTotalAwayGoalsTextView)
        val textViewAverageGoalsGame = findViewById<TextView>(R.id.teamAverageGoalsGameTextView)
        val textViewAverageHomeGoals = findViewById<TextView>(R.id.teamAverageHomeGoalsTextView)
        val textViewAverageAwayGoals = findViewById<TextView>(R.id.teamAverageAwayGoalsTextView)

        var totalGoals : Int = 0
        var totalHomeGoals : Int = 0
        var totalAwayGoals : Int = 0
        var averageGoalsGame : Float
        var averageHomeGoals : Float
        var averageAwayGoals : Float

        VolleyHelper.instance.getGamesByTeamID(this, teamID!!.toInt()) { response ->

            response?.let {

                for (index in 0 until it.length()) {

                    val gameJSON : JSONObject = it[index] as JSONObject

                    allGames.add(GameModel.parseJSON(gameJSON))

                    totalGoals += allGames[index].goalsHomeTeam!! + allGames[index].goalsAwayTeam!!
                    totalHomeGoals += allGames[index].goalsHomeTeam!!
                    totalAwayGoals += allGames[index].goalsAwayTeam!!
                    averageGoalsGame = totalGoals.toFloat() / allGames.size
                    averageHomeGoals = totalHomeGoals.toFloat() / allGames.size
                    averageAwayGoals = totalAwayGoals.toFloat() / allGames.size

                    textViewTotalGames.text = allGames.size.toString()
                    textViewTotalGoals.text = totalGoals.toString()
                    textViewTotalHomeGoals.text = totalHomeGoals.toString()
                    textViewTotalAwayGoals.text = totalAwayGoals.toString()
                    textViewAverageGoalsGame.text = "%.2f".format(averageGoalsGame)
                    textViewAverageHomeGoals.text = "%.2f".format(averageHomeGoals)
                    textViewAverageAwayGoals.text = "%.2f".format(averageAwayGoals)
                }
            }
        }

        teamDetailBackStatsButton.setOnClickListener {

            val intent = Intent(this, TeamDetailActivity::class.java)

            intent.putExtra("Team ID", teamID!!.toInt())
            intent.putExtra("Tournament ID", tournamentID!!.toInt())
            intent.putExtra("Teams Number", teamsNumber!!.toInt())
            intent.putExtra("Username", username!!)
            startActivity(intent)
        }
    }
}