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
import kotlinx.android.synthetic.main.activity_player_favorites.*
import org.json.JSONObject

class PlayerFavoritesActivity : AppCompatActivity() {

    var username : String? = null

    var players : MutableList<PlayerModel> = ArrayList()
    var playerAdapter : PlayerFavoritesActivity.PlayerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_favorites)

        val bundle = intent.extras
        bundle?.let {

            username = it.getString("Username")
        }

        playerHomePlayerButton.setBackgroundResource(R.drawable.button_border_selected)

        playerAdapter = PlayerAdapter()
        favoritePlayersListView.adapter = playerAdapter

        VolleyHelper.instance.getPlayers(this) { response ->

            response?.let {

                for (index in 0 until it.length()) {

                    val playerJSON : JSONObject = it[index] as JSONObject
                    players.add(PlayerModel.parseJSON(playerJSON))
                }
                playerAdapter?.notifyDataSetChanged()
            }
        }

        /*addPlayerButton.setOnClickListener {

            val intent = Intent(this, PlayerNewActivity::class.java)
            intent.putExtra("Player ID", players.size)
            startActivityForResult(intent, 1002)
        }*/

        tournamentHomePlayerButton.setOnClickListener {

            val intent = Intent(this, TournamentFavoritesActivity::class.java)
            intent.putExtra("Username", username!!)
            startActivity(intent)
        }

        teamHomePlayerButton.setOnClickListener {

            val intent = Intent(this, TeamFavoritesActivity::class.java)
            intent.putExtra("Username", username!!)
            startActivity(intent)
        }

        settingsPlayerButton.setOnClickListener {

            val intent = Intent(this, SettingsActivity::class.java)
            intent.putExtra("Username", username!!)
            startActivity(intent)
        }
    }

    inner class PlayerAdapter : BaseAdapter() {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            val rowView = layoutInflater.inflate(R.layout.row_player, parent, false)

            val textViewPlayerName = rowView.findViewById<TextView>(R.id.playerNameRowTextView)
            val textViewPlayerPosition = rowView.findViewById<TextView>(R.id.playerPositionRowTextView)
            val textViewPlayerTeamName = rowView.findViewById<TextView>(R.id.playerTeamRowTextView)
            val textViewPlayerHeight = rowView.findViewById<TextView>(R.id.playerHeightRowTextView)
            val textViewPlayerAge = rowView.findViewById<TextView>(R.id.playerAgeRowTextView)

            textViewPlayerName.text = players[position].playerFirstName + " " + players[position].playerLastName
            textViewPlayerPosition.text = players[position].playerPosition
            textViewPlayerTeamName.text = players[position].playerTeamName
            textViewPlayerHeight.text = players[position].playerHeight.toString() + " " + "cm"
            textViewPlayerAge.text = players[position].playerAge.toString() + " " + "Years Old"

            /*rowView.setOnClickListener {

                val intent = Intent(this@PlayerFavoritesActivity, PlayerDetailActivity::class.java)
                intent.putExtra("Team ID", players[position].playerTeamID)
                intent.putExtra("Player ID", players[position].playerID)
                startActivity(intent)
            }*/

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
