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

    private var tournamentID : Int? = null
    private var gameID : Int? = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_new)

        backGameButton.setOnClickListener {

            val intent = Intent(this, TournamentDetailGamesActivity::class.java)
            intent.putExtra("Tournament ID", tournamentID!!.toInt())
            startActivity(intent)
        }

        confirmGameButton.setOnClickListener {

            if (enterGameHomeTeamEditText.text.toString() == "" || enterGameAwayTeamEditText.text.toString() == "" ||
                enterHomeTeamGoalsEditText.text.toString() == ""  || enterAwayTeamGoalsEditText.text.toString() == "") {

                Toast.makeText(applicationContext,"Game Information is Missing !", Toast.LENGTH_SHORT).show()
            }

            else {

                val intentResult = Intent()

                var gameHomeTeamID = findViewById<EditText>(R.id.enterGameHomeTeamEditText)
                var gameAwayTeamID = findViewById<EditText>(R.id.enterGameAwayTeamEditText)
                var gameHomeGoals = findViewById<EditText>(R.id.enterHomeTeamGoalsEditText)
                var gameAwayGoals = findViewById<EditText>(R.id.enterAwayTeamGoalsEditText)

                val bundle = intent.extras
                bundle?.let {

                    tournamentID = it.getInt("Tournament ID")
                    gameID = it.getInt("Game ID")
                }

                VolleyHelper.instance.createNewGame (

                    this@GameNewActivity,
                    gameID!!.plus(1), gameHomeTeamID!!.text.toString().toInt(), gameAwayTeamID!!.text.toString().toInt(),
                    tournamentID!!.toInt(), gameHomeGoals!!.text.toString().toInt(), gameAwayGoals!!.text.toString().toInt()) { response ->

                    if (response) {

                        val intent = Intent(this, TournamentDetailGamesActivity::class.java)

                        intent.putExtra("Tournament ID", tournamentID!!.toInt())
                        Toast.makeText(applicationContext,"Game Created !", Toast.LENGTH_SHORT).show()
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