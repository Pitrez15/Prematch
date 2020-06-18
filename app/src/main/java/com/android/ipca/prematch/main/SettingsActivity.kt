package com.android.ipca.prematch.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.android.ipca.prematch.R
import com.android.ipca.prematch.helpers.VolleyHelper
import com.android.ipca.prematch.login.LoginActivity
import com.android.ipca.prematch.models.GameModel
import com.android.ipca.prematch.models.UserModel
import kotlinx.android.synthetic.main.activity_player_favorites.*
import kotlinx.android.synthetic.main.activity_settings.*
import org.json.JSONObject

class SettingsActivity : AppCompatActivity() {

    var username : String? = null

    var users : MutableList<UserModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val bundle = intent.extras
        bundle?.let {

            username = it.getString("Username")
        }

        settingsSettingsButton.setBackgroundResource(R.drawable.button_border_selected)

        val userFirstName = findViewById<TextView>(R.id.userFirstNameTextView)
        val userLastName = findViewById<TextView>(R.id.userLastNameTextView)
        val userUsername = findViewById<TextView>(R.id.userUsernameTextView)
        val userEmail = findViewById<TextView>(R.id.userEmailTextView)

        VolleyHelper.instance.getUserByUsername(this, username!!) { response ->

            response?.let {

                for (index in 0 until it.length()) {

                    val userJSON : JSONObject = it[index] as JSONObject
                    users.add(UserModel.parseJSON(userJSON))

                    userFirstName.text = users[index].userFirstName
                    userLastName.text = users[index].userLastName
                    userUsername.text = users[index].username
                    userEmail.text = users[index].userEmail
                }
            }
        }

        settingsLogoutButton.setOnClickListener {

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        tournamentHomeSettingsButton.setOnClickListener {

            val intent = Intent(this, TournamentFavoritesActivity::class.java)
            intent.putExtra("Username", username!!)
            startActivity(intent)
        }

        teamHomeSettingsButton.setOnClickListener {

            val intent = Intent(this, TeamFavoritesActivity::class.java)
            intent.putExtra("Username", username!!)
            startActivity(intent)
        }

        playerHomeSettingsButton.setOnClickListener {

            val intent = Intent(this, PlayerFavoritesActivity::class.java)
            intent.putExtra("Username", username!!)
            startActivity(intent)
        }
    }
}
