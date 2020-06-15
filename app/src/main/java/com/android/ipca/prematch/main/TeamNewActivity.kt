package com.android.ipca.prematch.main

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.android.ipca.prematch.R
import com.android.ipca.prematch.helpers.VolleyHelper
import kotlinx.android.synthetic.main.activity_team_new.*

private var teamID : Int? = 2
private var tournamentID : Int? = null

class TeamNewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_new)

        backTeamButton.setOnClickListener {

            val intent = Intent(this, TournamentDetailTeamsActivity::class.java)
            intent.putExtra("Tournament ID", tournamentID!!.toInt())
            startActivity(intent)
        }

        confirmTeamButton.setOnClickListener {

            if (enterTeamNameEditText.text.toString() == "" || enterTeamInitialsEditText.text.toString() == "" ||
                enterTeamCityEditText.text.toString() == "" || enterPrimaryColorEditText.toString() == "" ||
                enterSecondaryColorEditText.text.toString() == "" || enterTeamContactEmailEditText.text.toString() == "" ||
                enterTeamContactPhoneEditText.text.toString() == "") {

                Toast.makeText(applicationContext,"Team Information is Missing !", Toast.LENGTH_SHORT).show()
            }

            else {

                val intentResult = Intent()

                var teamName = findViewById<EditText>(R.id.enterTeamNameEditText)
                var teamInitials = findViewById<EditText>(R.id.enterTeamInitialsEditText)
                var teamCity = findViewById<EditText>(R.id.enterTeamCityEditText)
                var teamPrimaryColor = findViewById<EditText>(R.id.enterPrimaryColorEditText)
                var teamSecondaryColor = findViewById<EditText>(R.id.enterSecondaryColorEditText)
                var teamEmail = findViewById<EditText>(R.id.enterTeamContactEmailEditText)
                var teamPhone = findViewById<EditText>(R.id.enterTeamContactPhoneEditText)

                val bundle = intent.extras
                bundle?.let {

                    teamID = it.getInt("Team ID")
                    tournamentID = it.getInt("Tournament ID")
                }

                VolleyHelper.instance.createNewTeam (

                    this@TeamNewActivity,
                    teamID!!.plus(1), teamName.text.toString(), teamInitials.text.toString(),
                    teamCity.text.toString(), teamPrimaryColor.text.toString(), teamSecondaryColor.text.toString(),
                    teamEmail.text.toString(), teamPhone.text.toString().toInt(), tournamentID!!) { response ->

                        if (response) {

                            val intent = Intent(this, TournamentDetailTeamsActivity::class.java)
                            intent.putExtra("Tournament ID", tournamentID!!.toInt())
                            Toast.makeText(applicationContext,"Team Created !",Toast.LENGTH_SHORT).show()
                            startActivity(intent)
                        }

                        else {

                            Toast.makeText(applicationContext,"Failed to Create Team !",Toast.LENGTH_SHORT).show()
                        }
                    }

                setResult(Activity.RESULT_OK, intentResult)
                finish()
            }
        }
    }
}
