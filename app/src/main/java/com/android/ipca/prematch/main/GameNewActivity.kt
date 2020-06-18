package com.android.ipca.prematch.main

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.android.ipca.prematch.R
import com.android.ipca.prematch.helpers.VolleyHelper
import kotlinx.android.synthetic.main.activity_game_new.*

class GameNewActivity : AppCompatActivity() {

    var gameID : Int? = 2
    var tournamentID : Int? = null
    var teamsNumber : Int? = null
    var username : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_new)

        val bundle = intent.extras
        bundle?.let {

            gameID = it.getInt("Game ID")
            tournamentID = it.getInt("Tournament ID")
            teamsNumber = it.getInt("Teams Number")
            username = it.getString("Username")
        }

        backGameButton.setOnClickListener {

            val intent = Intent(this, TournamentDetailGamesActivity::class.java)
            intent.putExtra("Tournament ID", tournamentID!!.toInt())
            intent.putExtra("Teams Number", teamsNumber!!.toInt())
            intent.putExtra("Username", username!!)
            startActivity(intent)
        }

        confirmGameButton.setOnClickListener {

            if (enterGameHomeTeamEditText.text.toString() == "" || enterGameAwayTeamEditText.text.toString() == "" ||
                enterHomeTeamGoalsEditText.text.toString() == ""  || enterAwayTeamGoalsEditText.text.toString() == "" ||
                    enterStageEditText.text.toString() == "") {

                Toast.makeText(applicationContext,"Game Information is Missing !", Toast.LENGTH_SHORT).show()
            }

            else {

                val intentResult = Intent()

                var gameHomeTeamID = findViewById<EditText>(R.id.enterGameHomeTeamEditText)
                var gameAwayTeamID = findViewById<EditText>(R.id.enterGameAwayTeamEditText)
                var gameHomeGoals = findViewById<EditText>(R.id.enterHomeTeamGoalsEditText)
                var gameAwayGoals = findViewById<EditText>(R.id.enterAwayTeamGoalsEditText)
                var gameStage = findViewById<EditText>(R.id.enterStageEditText)

                VolleyHelper.instance.createNewGame (

                    this@GameNewActivity,
                    gameID!!.plus(1), gameHomeTeamID!!.text.toString().toInt(), gameAwayTeamID!!.text.toString().toInt(),
                    tournamentID!!.toInt(), gameHomeGoals!!.text.toString().toInt(), gameAwayGoals!!.text.toString().toInt(), gameStage!!.text.toString()) { response ->

                    if (response) {

                        Toast.makeText(applicationContext,"Game Created !", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, TournamentDetailGamesActivity::class.java)
                        intent.putExtra("Tournament ID", tournamentID!!.toInt())
                        intent.putExtra("Teams Number", teamsNumber!!.toInt())
                        intent.putExtra("Username", username!!)
                        startActivity(intent)
                    }

                    else {

                        Toast.makeText(applicationContext,"Failed to Game Player !", Toast.LENGTH_SHORT).show()
                    }
                }

                setResult(Activity.RESULT_OK, intentResult)
                finish()
            }
        }

    }
}