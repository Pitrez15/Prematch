package com.android.ipca.prematch.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.ipca.prematch.R
import kotlinx.android.synthetic.main.activity_player_favorites.*

class PlayerFavoritesActivity : AppCompatActivity() {

    //var players : MutableList<PlayerModel> = ArrayList<PlayerModel>()
    //var playerAdapter : PlayerFavoritesActivity.PlayerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_favorites)

        //playerAdapter = PlayerAdapter()
        //favoritePlayersListView.adapter = playerAdapter

        playerHomePlayerButton.setBackgroundResource(R.color.colorSecondary)

        addPlayerButton.setOnClickListener {

            val intent = Intent(this, PlayerNewActivity::class.java)
            startActivityForResult(intent, 1002)
        }

        tournamentHomePlayerButton.setOnClickListener {

            val intent = Intent(this, TournamentFavoritesActivity::class.java)
            startActivity(intent)
        }

        teamHomePlayerButton.setOnClickListener {

            val intent = Intent(this, TeamFavoritesActivity::class.java)
            startActivity(intent)
        }

        settingsPlayerButton.setOnClickListener {

            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
}
