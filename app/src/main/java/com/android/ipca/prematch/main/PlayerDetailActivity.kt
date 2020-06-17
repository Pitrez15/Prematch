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
import com.android.ipca.prematch.models.PlayerModel
import com.android.ipca.prematch.models.TeamModel
import kotlinx.android.synthetic.main.activity_player_detail.*
import org.json.JSONObject

class PlayerDetailActivity : AppCompatActivity() {

    var playerID : Int? = null
    var teamID : Int? = null

    var player : MutableList<PlayerModel> = ArrayList()
    var playerAdapter : PlayerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)

        playerAdapter = PlayerAdapter()
        playerDetailsListView.adapter = playerAdapter

        val bundle = intent.extras
        bundle?.let {

            playerID = it.getInt("Player ID")
            teamID = it.getInt("Team ID")
        }

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