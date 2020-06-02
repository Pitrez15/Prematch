package com.android.ipca.prematch.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.ipca.prematch.R
import kotlinx.android.synthetic.main.activity_player_favorites.*
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        settingsSettingsButton.setBackgroundResource(R.color.colorSecondary)

        tournamentHomeSettingsButton.setOnClickListener {

            val intent = Intent(this, TournamentFavoritesActivity::class.java)
            startActivity(intent)
        }

        teamHomeSettingsButton.setOnClickListener {

            val intent = Intent(this, TeamFavoritesActivity::class.java)
            startActivity(intent)
        }

        playerHomeSettingsButton.setOnClickListener {

            val intent = Intent(this, PlayerFavoritesActivity::class.java)
            startActivity(intent)
        }
    }
}
