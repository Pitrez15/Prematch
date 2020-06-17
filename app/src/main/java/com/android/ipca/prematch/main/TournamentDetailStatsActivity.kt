package com.android.ipca.prematch.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.android.ipca.prematch.R
import com.android.ipca.prematch.helpers.VolleyHelper
import com.android.ipca.prematch.models.GameModel
import com.android.ipca.prematch.models.TeamModel
import com.android.ipca.prematch.models.TournamentModel
import kotlinx.android.synthetic.main.activity_tournament_detail_stats.*
import org.json.JSONObject

class TournamentDetailStatsActivity : AppCompatActivity() {

    var tournamentID : Int? = null

    var allTeams : MutableList<TeamModel> = ArrayList()
    var allGames : MutableList<GameModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tournament_detail_stats)

        val bundle = intent.extras
        bundle?.let {

            tournamentID = it.getInt("Tournament ID")
        }

        val textViewTotalTeams = findViewById<TextView>(R.id.numberTeamsTextView)
        val textViewTotalGames = findViewById<TextView>(R.id.numberGamesTextView)
        val textViewTotalGoals = findViewById<TextView>(R.id.tournamentTotalGoalsTextView)
        val textViewTotalHomeGoals = findViewById<TextView>(R.id.tournamentTotalHomeGoalsTextView)
        val textViewTotalAwayGoals = findViewById<TextView>(R.id.tournamentTotalAwayGoalsTextView)
        val textViewAverageGoalsTeam = findViewById<TextView>(R.id.tournamentAverageGoalsTeamTextView)
        val textViewAverageGoalsGame = findViewById<TextView>(R.id.tournamentAverageGoalsGameTextView)
        val textViewAverageHomeGoals = findViewById<TextView>(R.id.tournamentAverageHomeGoalsTextView)
        val textViewAverageAwayGoals = findViewById<TextView>(R.id.tournamentAverageAwayGoalsTextView)

        var totalGoals : Int = 0
        var totalHomeGoals : Int = 0
        var totalAwayGoals : Int = 0
        var averageGoalsTeam : Float = 0.2F
        var averageGoalsGame : Float = 0.2F
        var averageHomeGoals : Float = 0.2F
        var averageAwayGoals : Float = 0.2F


        VolleyHelper.instance.getTeamsByTournamentID(this, tournamentID!!) { response ->

            response?.let {

                for (index in 0 until it.length()) {

                    val teamJSON : JSONObject = it[index] as JSONObject
                    allTeams.add(TeamModel.parseJSON(teamJSON))

                    textViewTotalTeams.text = allTeams.size.toString()
                }
            }
        }

        VolleyHelper.instance.getGamesByTournamentID(this, tournamentID!!.toInt()) { response ->

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
                    averageGoalsTeam = totalGoals.toFloat() / textViewTotalTeams.text.toString().toInt()

                    textViewTotalGames.text = allGames.size.toString()
                    textViewTotalGoals.text = totalGoals.toString()
                    textViewTotalHomeGoals.text = totalHomeGoals.toString()
                    textViewTotalAwayGoals.text = totalAwayGoals.toString()
                    textViewAverageGoalsGame.text = averageGoalsGame.toString()
                    textViewAverageHomeGoals.text = averageHomeGoals.toString()
                    textViewAverageAwayGoals.text = averageAwayGoals.toString()
                    textViewAverageGoalsTeam.text = averageGoalsTeam.toString()
                }
            }
        }

        tournamentDetailBackStatsButton.setOnClickListener {

            val intent = Intent(this, TournamentDetailActivity::class.java)
            intent.putExtra("Tournament ID", tournamentID!!.toInt())
            startActivity(intent)
        }
    }
}