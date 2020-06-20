package com.android.ipca.prematch.main

import android.content.Intent
import android.os.BadParcelableException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.android.ipca.prematch.R
import com.android.ipca.prematch.helpers.VolleyHelper
import com.android.ipca.prematch.models.PlayerModel
import com.android.ipca.prematch.models.TeamModel
import kotlinx.android.synthetic.main.activity_team_detail_players.*
import org.json.JSONObject

class TeamDetailPlayersActivity : AppCompatActivity() {

    var teamID : Int? = null
    var tournamentID : Int? = null
    var teamsNumber : Int? = null
    var username : String? = null

    var allPlayers : MutableList<PlayerModel> = ArrayList()
    var players : MutableList<PlayerModel> = ArrayList()
    private var playerAdapter : TeamDetailPlayersActivity.PlayersAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail_players)

        val bundle = intent.extras
        bundle?.let {

            teamID = it.getInt("Team ID")
            tournamentID = it.getInt("Tournament ID")
            teamsNumber = it.getInt("Teams Number")
            username = it.getString("Username")
        }

        playerAdapter = PlayersAdapter()
        teamDetailPlayersListView.adapter = playerAdapter

        VolleyHelper.instance.getPlayersByTeamID(this, teamID!!.toInt()) { response ->

            response?.let {

                for (index in 0 until it.length()) {

                    val playerJSON : JSONObject = it[index] as JSONObject
                    players.add(PlayerModel.parseJSON(playerJSON))
                }
                playerAdapter?.notifyDataSetChanged()
            }
        }

        VolleyHelper.instance.getPlayers(this) { response ->

            response?.let {

                for (index in 0 until it.length()) {

                    val playerJSON : JSONObject = it[index] as JSONObject
                    allPlayers.add(PlayerModel.parseJSON(playerJSON))
                }
            }
        }

        teamDetailAddPlayersButton.setOnClickListener {

            val intent = Intent(this, PlayerNewActivity::class.java)

            intent.putExtra("Team ID", teamID!!.toInt())
            intent.putExtra("Player ID", allPlayers.size)
            intent.putExtra("Tournament ID", tournamentID!!.toInt())
            intent.putExtra("Teams Number", teamsNumber!!.toInt())
            intent.putExtra("Username", username!!)
            startActivity(intent)
        }

        teamDetailBackTeamButton.setOnClickListener {

            val intent = Intent(this, TeamDetailActivity::class.java)

            intent.putExtra("Team ID", teamID!!.toInt())
            intent.putExtra("Tournament ID", tournamentID!!.toInt())
            intent.putExtra("Teams Number", teamsNumber!!.toInt())
            intent.putExtra("Username", username!!)
            startActivity(intent)
        }
    }

    inner class PlayersAdapter : BaseAdapter() {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            val rowView = layoutInflater.inflate(R.layout.row_player, parent, false)

            val textViewPlayerName = rowView.findViewById<TextView>(R.id.playerNameRowTextView)
            val textViewPlayerPosition = rowView.findViewById<TextView>(R.id.playerPositionRowTextView)
            val textViewTeamID = rowView.findViewById<TextView>(R.id.playerTeamRowTextView)
            val textViewPlayerHeight = rowView.findViewById<TextView>(R.id.playerHeightRowTextView)
            val textViewPlayerAge = rowView.findViewById<TextView>(R.id.playerAgeRowTextView)

            textViewPlayerName.text = players[position].playerFirstName + " " + players[position].playerLastName
            textViewPlayerPosition.text = players[position].playerPosition
            textViewTeamID.text = players[position].playerTeamName
            textViewPlayerHeight.text = players[position].playerHeight.toString() + " " + "cm"
            textViewPlayerAge.text = players[position].playerAge.toString() + " " + "Years Old"

            rowView.setOnClickListener {

                val intent = Intent(this@TeamDetailPlayersActivity, PlayerDetailActivity::class.java)
                intent.putExtra("Player ID", players[position].playerID)
                intent.putExtra("Team ID", players[position].playerTeamID)
                intent.putExtra("Tournament ID", tournamentID!!.toInt())
                intent.putExtra("Teams Number", teamsNumber!!.toInt())
                intent.putExtra("Username", username!!)
                startActivity(intent)
            }

            return rowView
        }

        override fun getItem(position: Int): Any {

            return players[position]
        }

        override fun getItemId(position: Int): Long {

            return 0
        }

        override fun getCount(): Int {

            return players.size
        }


    }
}