package com.android.ipca.prematch.main

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.android.ipca.prematch.R
import com.android.ipca.prematch.helpers.VolleyHelper
import kotlinx.android.synthetic.main.activity_tournament_new.*

private var tournamentID : Int? = 2
private var username : String? = null

class TournamentNewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tournament_new)

        val bundle = intent.extras
        bundle?.let {

            tournamentID = it.getInt("Tournament ID")
            username = it.getString("Username")
        }

        backTournamentButton.setOnClickListener {

            val intent = Intent(this, TournamentFavoritesActivity::class.java)
            intent.putExtra("Username", username!!)
            startActivity(intent)
        }

        confirmTournamentButton.setOnClickListener {

            if (enterTournamentNameEditText.text.toString() == "" || enterStartDateEditText.text.toString() == "" ||
                enterFinishDateEditText.text.toString() == "" || tournamentContactEmailEditText.toString() == "" ||
                tournamentContactPhoneEditText.text.toString() == "" || enterTeamNumberEditText.text.toString() == "" ||
                enterTournamentTypeEditText.text.toString() == "") {

                Toast.makeText(applicationContext,getString(R.string.missing_information),Toast.LENGTH_SHORT).show()
            }

            else {

                val intentResult = Intent()

                var tournamentName = findViewById<EditText>(R.id.enterTournamentNameEditText)
                var startDate = findViewById<EditText>(R.id.enterStartDateEditText)
                var finishDate = findViewById<EditText>(R.id.enterFinishDateEditText)
                var tournamentEmail = findViewById<EditText>(R.id.tournamentContactEmailEditText)
                var tournamentPhone = findViewById<EditText>(R.id.tournamentContactPhoneEditText)
                var teamsNumber = findViewById<EditText>(R.id.enterTeamNumberEditText)
                var tournamentType = findViewById<EditText>(R.id.enterTournamentTypeEditText)

                VolleyHelper.instance.createNewTournament(

                    this@TournamentNewActivity,
                    tournamentID!!.plus(1), tournamentName.text.toString(), startDate.text.toString(),
                    finishDate.text.toString(), tournamentEmail.text.toString(), tournamentPhone.text.toString().toInt(),
                    teamsNumber.text.toString().toInt(), tournamentType.text.toString()) { response ->

                        if (response) {

                            val intent = Intent(this, TournamentFavoritesActivity::class.java)
                            intent.putExtra("Username", username!!)
                            Toast.makeText(applicationContext,getString(R.string.tournament_created),Toast.LENGTH_SHORT).show()
                            startActivity(intent)
                        }

                        else {

                            Toast.makeText(applicationContext,getString(R.string.creation_failed),Toast.LENGTH_SHORT).show()
                        }
                    }

                setResult(Activity.RESULT_OK, intentResult)
                finish()
            }
        }
    }
}
