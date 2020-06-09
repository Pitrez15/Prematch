package com.android.ipca.prematch.main

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.ipca.prematch.R
import kotlinx.android.synthetic.main.activity_team_new.*

class TeamNewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_new)

        /*backTeamButton.setOnClickListener {

            val intent = Intent(this, TournamentDetailTeamsActivity::class.java)
            startActivity(intent)
        }*/

        confirmTeamButton.setOnClickListener {

            if(enterTeamNameEditText.text.toString() == "" || enterTeamInitialsEditText.text.toString() == "" ||
                enterTeamCityEditText.text.toString() == "" || enterPrimaryColorEditText.toString() == "" ||
                enterSecondaryColorEditText.text.toString() == "" || enterTeamContactEmailEditText.text.toString() == "" ||
                enterTeamContactPhoneEditText.text.toString() == "") {

                Toast.makeText(applicationContext,"Team Information is Missing !", Toast.LENGTH_SHORT).show()
            }

            else {

                val intentResult = Intent(this, TournamentDetailTeamsActivity::class.java)

                intentResult.putExtra(TEAM_NAME, enterTeamNameEditText.text.toString())
                intentResult.putExtra(TEAM_INITIALS, enterTeamInitialsEditText.text.toString())
                intentResult.putExtra(TEAM_CITY, enterTeamCityEditText.text.toString())
                intentResult.putExtra(TEAM_PRIMARY_COLOR, enterPrimaryColorEditText.text.toString())
                intentResult.putExtra(TEAM_SECONDARY_COLOR, enterSecondaryColorEditText.text.toString())
                intentResult.putExtra(TEAM_CONTACT_EMAIL, enterTeamContactEmailEditText.text.toString())
                intentResult.putExtra(TEAM_CONTACT_PHONE, enterTeamContactPhoneEditText.text.toString())

                setResult(Activity.RESULT_OK, intentResult)
                finish()
            }
        }
    }

    companion object {

        var TEAM_NAME: String? = "Team Name"
        var TEAM_INITIALS: String? = "Team Initials"
        var TEAM_CITY: String? = "Team City"
        var TEAM_PRIMARY_COLOR: String? = "Primary Color"
        var TEAM_SECONDARY_COLOR: String? = "Secondary Color"
        var TEAM_CONTACT_EMAIL: String? = "Team Email"
        var TEAM_CONTACT_PHONE: String? = "Team Phone"
    }
}
