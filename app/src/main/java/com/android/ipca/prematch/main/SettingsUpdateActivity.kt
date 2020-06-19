package com.android.ipca.prematch.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.android.ipca.prematch.R
import com.android.ipca.prematch.helpers.VolleyHelper
import com.android.ipca.prematch.login.LoginActivity
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_settings_update.*

class SettingsUpdateActivity : AppCompatActivity() {

    var username : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_update)

        val bundle = intent.extras
        bundle?.let {

            username = it.getString("Username")
        }

        val userNewFirstName = findViewById<EditText>(R.id.enterNewFirstNameEditText)
        val userNewLastName = findViewById<EditText>(R.id.enterNewLastNameEditText)
        val userNewEmail = findViewById<EditText>(R.id.enterNewEmailEditText)

        settingsUpdateAccountButton.setOnClickListener {

            if (userNewFirstName.text.toString() == "" || userNewLastName.text.toString() == "" || userNewEmail.text.toString() == "") {

                Toast.makeText(applicationContext,getString(R.string.missing_information), Toast.LENGTH_SHORT).show()
            }

            else {

                VolleyHelper.instance.updateUser (

                    this@SettingsUpdateActivity, userNewFirstName.text.toString(), userNewLastName.text.toString(),
                    userNewEmail.text.toString(), username!!) {

                    if (it) {

                        Toast.makeText(applicationContext, getString(R.string.registry_confirmed), Toast.LENGTH_LONG).show()

                        val intent = Intent(this, SettingsActivity::class.java)
                        intent.putExtra("Username", username!!)
                        startActivity(intent)
                    }
                    else {

                        Toast.makeText(applicationContext,getString(R.string.faield_registration),Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}