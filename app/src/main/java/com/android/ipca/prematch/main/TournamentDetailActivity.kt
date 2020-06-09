package com.android.ipca.prematch.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.ipca.prematch.R
import kotlinx.android.synthetic.main.activity_tournament_detail.*

private var TOURNAMENT_NAME: String? = null
private var START_DATE: String? = null
private var FINISH_DATE: String? = null
private var CONTACT_EMAIL: String? = null
private var CONTACT_PHONE: String? = null
private var TEAMS_NUMBER: String? = null
private var TOURNAMENT_TYPE: String? = null

class TournamentDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tournament_detail)

        val bundle = intent.extras
        bundle?.let {

            TOURNAMENT_NAME = it.getString("Tournament Name")
            START_DATE = it.getString("Start Date")
            FINISH_DATE = it.getString("Finish Date")
            CONTACT_EMAIL = it.getString("Contact Email")
            CONTACT_PHONE = it.getString("Contact Phone")
            TEAMS_NUMBER = it.getString("Teams Number")
            TOURNAMENT_TYPE = it.getString("Tournament Type")
        }

        detailTournamentTextView.text = TOURNAMENT_NAME.toString()
        startDateTextView.text = START_DATE.toString()
        finishDateTextView.text = FINISH_DATE.toString()
        tournamentTypeTextView.text = TOURNAMENT_TYPE.toString()
        teamsNumberTextView.text = TEAMS_NUMBER.toString()
        contactEmailTextView.text = CONTACT_EMAIL.toString()
        contactPhoneTextView.text = CONTACT_PHONE.toString()

        tournamentDetailTeamsButton.setOnClickListener {

            val intent = Intent(this, TournamentDetailTeamsActivity::class.java)
            intent.putExtra("Teams Number", TEAMS_NUMBER)
            startActivity(intent)
        }
    }
}