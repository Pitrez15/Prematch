package com.android.ipca.prematch.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.android.ipca.prematch.R
import com.android.ipca.prematch.helpers.VolleyHelper
import com.android.ipca.prematch.models.GameModel
import kotlinx.android.synthetic.main.activity_team_detail_games.*
import org.json.JSONObject

class TeamDetailGamesActivity : AppCompatActivity() {

    var teamID: Int? = null

    var allGames: MutableList<GameModel> = ArrayList()
    var games: MutableList<GameModel> = ArrayList()
    private var gamesAdapter: GamesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail_games)

        gamesAdapter = GamesAdapter()
        teamDetailGamesListView.adapter = gamesAdapter



        val bundle = intent.extras
        bundle?.let {

            teamID = it.getInt("Team ID")
        }

        VolleyHelper.instance.getGamesByTeamID(this, teamID!!.toInt()) { response ->

            response?.let {

                for (index in 0 until it.length()) {

                    val gameJSON: JSONObject = it[index] as JSONObject
                    games.add(GameModel.parseJSON(gameJSON))
                }
                gamesAdapter?.notifyDataSetChanged()
            }
        }

        VolleyHelper.instance.getGames(this) { response ->

            response?.let {

                for (index in 0 until it.length()) {

                    val gameJSON: JSONObject = it[index] as JSONObject
                    allGames.add(GameModel.parseJSON(gameJSON))
                }
            }
        }

        teamDetailBackGamesButton.setOnClickListener {

            val intent = Intent(this, TeamDetailActivity::class.java)

            intent.putExtra("Team ID", teamID!!.toInt())
            startActivity(intent)
        }
    }

    inner class GamesAdapter : BaseAdapter() {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            val rowView = layoutInflater.inflate(R.layout.row_game, parent, false)

            val gameTeams = rowView.findViewById<TextView>(R.id.gameTeamsRowTextView)
            val gameHomeGoals = rowView.findViewById<TextView>(R.id.gameHomeGoalsRowTextView)
            val gameAwayGoals = rowView.findViewById<TextView>(R.id.gameAwayGoalsRowTextView)
            val gameStage = rowView.findViewById<TextView>(R.id.gameStageRowTextView)
            val buttonDeleteGame = rowView.findViewById<ImageButton>(R.id.gameDeleteRowButton)

            buttonDeleteGame.visibility = View.GONE
            gameTeams.text = games[position].homeTeamName + " vs " + games[position].awayTeamName
            gameHomeGoals.text = games[position].goalsHomeTeam.toString()
            gameAwayGoals.text = games[position].goalsAwayTeam.toString()
            gameStage.text = games[position].gameStage.toString()

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