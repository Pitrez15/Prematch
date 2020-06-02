package com.android.ipca.prematch.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.ipca.prematch.R
import kotlinx.android.synthetic.main.activity_team_new.*
import kotlinx.android.synthetic.main.activity_tournament_new.*

class TeamNewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_new)

        backTeamButton.setOnClickListener {

            val intent = Intent(this, TeamFavoritesActivity::class.java)
            startActivity(intent)
        }
    }
}
