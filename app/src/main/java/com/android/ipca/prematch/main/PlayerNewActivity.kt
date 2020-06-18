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

    private var teamID : Int? = null
    private var tournamentID : Int? = null
    private var playerID : Int? = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_new)

        val bundle = intent.extras
        bundle?.let {

            teamID = it.getInt("Team ID")
            playerID = it.getInt("Player ID")
            tournamentID = it.getInt("Tournament ID")
        }

        backPlayerButton.setOnClickListener {

            val intent = Intent(this, PlayerFavoritesActivity::class.java)

            startActivity(intent)
        }

        confirmPlayerButton.setOnClickListener {

            if (enterPlayerFirstNameEditText.text.toString() == "" || enterPlayerLastNameEditText.text.toString() == "" ||
                enterPositionEditText.text.toString() == "" ) {
                intent.putExtra("Team ID", teamID!!.toInt())
                Toast.makeText(applicationContext,"Player Information is Missing !", Toast.LENGTH_SHORT).show()
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
                    playerPosition.text.toString(), teamID!!.toInt(), playerHeight.text.toString().toInt(), playerAge.text.toString().toInt(), tournamentID!!.toInt()) { response ->

                    if (response) {

                        val intent = Intent(this, TeamDetailPlayersActivity::class.java)

                        intent.putExtra("Team ID", teamID!!.toInt())
                        Toast.makeText(applicationContext,"Player Created !", Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                    }

                    else {

                        Toast.makeText(applicationContext,"Failed to Create Player !", Toast.LENGTH_SHORT).show()
                    }
                }

                setResult(Activity.RESULT_OK, intentResult)
                finish()
            }
        }
    }
}
