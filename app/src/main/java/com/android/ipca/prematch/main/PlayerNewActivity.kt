package com.android.ipca.prematch.main

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.android.ipca.prematch.R
import com.android.ipca.prematch.helpers.VolleyHelper
import kotlinx.android.synthetic.main.activity_player_new.*
import kotlinx.android.synthetic.main.activity_team_new.*

class PlayerNewActivity : AppCompatActivity() {

    var teamID : Int? = null
    var playerID : Int? = 2
    var tournamentID : Int? = null
    var teamsNumber : Int? = null
    var username : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_new)

        val bundle = intent.extras
        bundle?.let {

            teamID = it.getInt("Team ID")
            playerID = it.getInt("Player ID")
            tournamentID = it.getInt("Tournament ID")
            teamsNumber = it.getInt("Teams Number")
            username = it.getString("Username")
        }

        backPlayerButton.setOnClickListener {

            val intent = Intent(this, TeamDetailPlayersActivity::class.java)
            intent.putExtra("Team ID", teamID!!.toInt())
            intent.putExtra("Tournament ID", tournamentID!!.toInt())
            intent.putExtra("Teams Number", teamsNumber!!.toInt())
            intent.putExtra("Username", username!!)
            startActivity(intent)
        }

        confirmPlayerButton.setOnClickListener {

            if (enterPlayerFirstNameEditText.text.toString() == "" || enterPlayerLastNameEditText.text.toString() == "" ||
                enterPositionEditText.text.toString() == "" ) {

                Toast.makeText(applicationContext,getString(R.string.missing_information), Toast.LENGTH_SHORT).show()
            }

            else {

                val intentResult = Intent()

                var playerFirstName = findViewById<EditText>(R.id.enterPlayerFirstNameEditText)
                var playerLastName = findViewById<EditText>(R.id.enterPlayerLastNameEditText)
                var playerPosition = findViewById<EditText>(R.id.enterPositionEditText)
                var playerHeight = findViewById<EditText>(R.id.enterPlayerHeightEditText)
                var playerAge = findViewById<EditText>(R.id.enterPlayerAgeEditText)

                VolleyHelper.instance.createNewPlayer (

                    this@PlayerNewActivity,
                    playerID!!.plus(1), playerFirstName.text.toString(), playerLastName.text.toString(),
                    playerPosition.text.toString(), teamID!!.toInt(), playerHeight.text.toString().toInt(),
                    playerAge.text.toString().toInt(), tournamentID!!.toInt()) { response ->

                    if (response) {

                        Toast.makeText(applicationContext,getString(R.string.player_created), Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, TeamDetailPlayersActivity::class.java)
                        intent.putExtra("Team ID", teamID!!.toInt())
                        intent.putExtra("Tournament ID", tournamentID!!.toInt())
                        intent.putExtra("Teams Number", teamsNumber!!.toInt())
                        intent.putExtra("Username", username!!)
                        startActivity(intent)
                    }

                    else {

                        Toast.makeText(applicationContext,getString(R.string.failed_to_create_player), Toast.LENGTH_SHORT).show()
                    }
                }

                setResult(Activity.RESULT_OK, intentResult)
                finish()
            }
        }
    }
}
