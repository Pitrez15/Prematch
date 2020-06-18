package com.android.ipca.prematch.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.android.ipca.prematch.R
import com.android.ipca.prematch.helpers.VolleyHelper
import com.android.ipca.prematch.models.GameModel
import kotlinx.android.synthetic.main.activity_tournament_detail_games.*
import org.json.JSONObject

class TournamentDetailGamesActivity : AppCompatActivity() {

    var tournamentID : Int? = null
    var teamsNumber : Int? = null
    var username : String? = null

    var allGames : MutableList<GameModel> = ArrayList()
    var games : MutableList<GameModel> = ArrayList()
    private var gamesAdapter : TournamentDetailGamesActivity.GamesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tournament_detail_games)

        val bundle = intent.extras
        bundle?.let {

            tournamentID = it.getInt("Tournament ID")
            teamsNumber = it.getInt("Teams Number")
            username = it.getString("Username")
        }

        gamesAdapter = GamesAdapter()
        tournamentDetailGamesListView.adapter = gamesAdapter

        VolleyHelper.instance.getGamesByTournamentID(this, tournamentID!!.toInt()) { response ->

            response?.let {

                for (index in 0 until it.length()) {

                    val gameJSON : JSONObject = it[index] as JSONObject
                    games.add(GameModel.parseJSON(gameJSON))
                }
                gamesAdapter?.notifyDataSetChanged()
            }
        }

        VolleyHelper.instance.getGames(this) { response ->

            response?.let {

                for (index in 0 until it.length()) {

                    val gameJSON : JSONObject = it[index] as JSONObject
                    allGames.add(GameModel.parseJSON(gameJSON))
                }
            }
        }

        tournamentDetailAddGameButton.setOnClickListener {

            val intent = Intent(this, GameNewActivity::class.java)
            intent.putExtra("Game ID", allGames.size)
            intent.putExtra("Tournament ID", tournamentID!!.toInt())
            intent.putExtra("Teams Number", teamsNumber!!.toInt())
            intent.putExtra("Username", username!!)
            startActivity(intent)
        }

        tournamentDetailBackGamesButton.setOnClickListener {

            val intent = Intent(this, TournamentDetailActivity::class.java)
            intent.putExtra("Tournament ID", tournamentID!!.toInt())
            intent.putExtra("Teams Number", teamsNumber!!.toInt())
            intent.putExtra("Username", username!!)
            startActivity(intent)
        }
    }

    inner class GamesAdapter : BaseAdapter() {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?) : View {

            val rowView = layoutInflater.inflate(R.layout.row_game, parent, false)

            val gameTeams = rowView.findViewById<TextView>(R.id.gameTeamsRowTextView)
            val gameHomeGoals = rowView.findViewById<TextView>(R.id.gameHomeGoalsRowTextView)
            val gameAwayGoals = rowView.findViewById<TextView>(R.id.gameAwayGoalsRowTextView)
            val gameStage = rowView.findViewById<TextView>(R.id.gameStageRowTextView)
            val deleteGameButton = rowView.findViewById<ImageButton>(R.id.gameDeleteRowButton)

            gameTeams.text = games[position].homeTeamName + " vs " + games[position].awayTeamName
            gameHomeGoals.text = games[position].goalsHomeTeam.toString()
            gameAwayGoals.text = games[position].goalsAwayTeam.toString()
            gameStage.text = games[position].gameStage.toString()

            deleteGameButton.setOnClickListener {

                val intent = Intent(this@TournamentDetailGamesActivity, TournamentDetailGamesActivity::class.java)
                intent.putExtra("Tournament ID", tournamentID!!.toInt())
                intent.putExtra("Teams Number", teamsNumber!!.toInt())
                intent.putExtra("Username", username!!)

                VolleyHelper.instance.deleteGameByID(this@TournamentDetailGamesActivity, games[position].gameID!!) {

                    if (it) {

                        Toast.makeText(applicationContext,"Game Deleted !", Toast.LENGTH_SHORT).show()
                    }
                    else {

                        Toast.makeText(applicationContext,"Failed to Delete Game !", Toast.LENGTH_SHORT).show()
                    }
                }
                startActivity(intent)
            }

            return rowView
        }

        override fun getItem(position: Int): Any {

            return games[position]
        }

        override fun getItemId(position: Int): Long {

            return 0
        }

        override fun getCount(): Int {

            return games.size
        }
    }
}