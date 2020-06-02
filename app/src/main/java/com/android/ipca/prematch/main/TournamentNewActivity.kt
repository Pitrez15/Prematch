package com.android.ipca.prematch.main

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.ipca.prematch.R
import kotlinx.android.synthetic.main.activity_tournament_new.*

class TournamentNewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tournament_new)

        backTournamentButton.setOnClickListener {

            val intent = Intent(this, TournamentFavoritesActivity::class.java)
            startActivity(intent)
        }

        confirmTournamentButton.setOnClickListener {

            if(enterTournamentNameEditText.text.toString() == "" || enterTournamentCityEditText.text.toString() == "" ||
                enterStartDateEditText.text.toString() == "" || enterFinishDateEditText.text.toString() == "" ||
                tournamentContactEmailEditText.toString() == "" || tournamentContactPhoneEditText.text.toString() == "" ||
                enterTeamNumberEditText.text.toString() == "" || enterTournamentTypeEditText.text.toString() == "") {

                Toast.makeText(applicationContext,"Tournament Information is Missing !",Toast.LENGTH_SHORT).show()
            }

            else {

                val intentResult = Intent(this, TournamentFavoritesActivity::class.java)

                intentResult.putExtra(TOURNAMENT_NAME, enterTournamentNameEditText.text.toString())
                intentResult.putExtra(TOURNAMENT_CITY, enterTournamentCityEditText.text.toString())
                intentResult.putExtra(START_DATE, enterStartDateEditText.text.toString())
                intentResult.putExtra(FINISH_DATE, enterFinishDateEditText.text.toString())
                intentResult.putExtra(CONTACT_EMAIL, tournamentContactEmailEditText.text.toString())
                intentResult.putExtra(CONTACT_PHONE, tournamentContactPhoneEditText.text.toString())
                intentResult.putExtra(TEAMS_NUMBER, enterTeamNumberEditText.text.toString())
                intentResult.putExtra(TOURNAMENT_TYPE, enterTournamentTypeEditText.text.toString())

                setResult(Activity.RESULT_OK, intentResult)
                finish()
            }
        }
    }

    companion object {

        var TOURNAMENT_NAME: String? = "Tournament Name"
        var TOURNAMENT_CITY: String? = "Tournament City"
        var START_DATE: String? = "Start Date"
        var FINISH_DATE: String? = "Finish Date"
        var CONTACT_EMAIL: String? = "Contact Email"
        var CONTACT_PHONE: String? = "Contact Phone"
        var TEAMS_NUMBER: String? = "Teams Number"
        var TOURNAMENT_TYPE: String? = "Tournament Type"
    }
}
