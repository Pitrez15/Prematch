package com.android.ipca.prematch.main

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
import com.android.ipca.prematch.models.PlayerModel
import com.android.ipca.prematch.models.TeamModel
import kotlinx.android.synthetic.main.activity_player_detail.*
import org.json.JSONObject

class PlayerDetailActivity : AppCompatActivity() {

    var playerID : Int? = null
    var teamID : Int? = null
    var tournamentID : Int? = null
    var teamsNumber : Int? = null
    var username : String? = null

    var player : MutableList<PlayerModel> = ArrayList()
    var playerAdapter : PlayerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)

        val bundle = intent.extras
        bundle?.let {

            playerID = it.getInt("Player ID")
            teamID = it.getInt("Team ID")
            tournamentID = it.getInt("Tournament ID")
            teamsNumber = it.getInt("Teams Number")
            username = it.getString("Username")
        }

        playerAdapter = PlayerAdapter()
        playerDetailsListView.adapter = playerAdapter

        VolleyHelper.instance.getPlayerByID(this, playerID!!.toInt()) { response ->

            response?.let {

                for(index in 0 until it.length()){

                    val playerJSON : JSONObject = it[index] as JSONObject
                    player.add(PlayerModel.parseJSON(playerJSON))
                }
                playerAdapter?.notifyDataSetChanged()
            }
        }

        playerDetailBackButton.setOnClickListener {

            val intent = Intent(this, TeamDetailPlayersActivity::class.java)
            intent.putExtra("Team ID", teamID!!.toInt())
            intent.putExtra("Tournament ID", tournamentID!!.toInt())
            intent.putExtra("Teams Number", teamsNumber!!.toInt())
            intent.putExtra("Username", username!!)
            startActivity(intent)
        }

        playerDetailDeleteButton.setOnClickListener {

            val intent = Intent(this@PlayerDetailActivity, TeamDetailPlayersActivity::class.java)
            intent.putExtra("Team ID", teamID!!.toInt())
            intent.putExtra("Tournament ID", tournamentID!!.toInt())
            intent.putExtra("Teams Number", teamsNumber!!.toInt())
            intent.putExtra("Username", username!!)

            VolleyHelper.instance.deletePlayerByID(this@PlayerDetailActivity, playerID!!.toInt()) {

                if (it) {

                    Toast.makeText(applicationContext,"Team Deleted !", Toast.LENGTH_SHORT).show()
                }
                else {

                    Toast.makeText(applicationContext,"Failed to Delete Team !", Toast.LENGTH_SHORT).show()
                }
            }

            startActivity(intent)
        }
    }

    inner class PlayerAdapter : BaseAdapter() {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            val rowView = layoutInflater.inflate(R.layout.row_player_detail, parent, false)

            val textViewPlayerName = findViewById<TextView>(R.id.detailPlayerTextView)
            val textViewPlayerHeight = rowView.findViewById<TextView>(R.id.playerHeightTextView)
            val textViewPlayerAge = rowView.findViewById<TextView>(R.id.playerAgeTextView)
            val textViewPlayerPosition = rowView.findViewById<TextView>(R.id.playerPositionTextView)
            val textViewPlayerTeamID = rowView.findViewById<TextView>(R.id.playerTeamIDTextView)

            textViewPlayerName.text = player[position].playerFirstName + " " + player[position].playerLastName
            textViewPlayerHeight.text = player[position].playerHeight.toString()
            textViewPlayerAge.text = player[position].playerAge.toString()
            textViewPlayerPosition.text = player[position].playerPosition
            textViewPlayerTeamID.text = player[position].playerTeamID.toString()

            return rowView
        }

        override fun getItem(position: Int): Any {

            return player[position]
        }

        override fun getItemId(position: Int): Long {

            return 0
        }

        override fun getCount(): Int {

            return player.size
        }


    }
}