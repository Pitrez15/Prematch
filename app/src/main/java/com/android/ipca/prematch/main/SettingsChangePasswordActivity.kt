package com.android.ipca.prematch.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.android.ipca.prematch.R
import com.android.ipca.prematch.helpers.VolleyHelper
import com.android.ipca.prematch.login.LoginActivity
import kotlinx.android.synthetic.main.activity_settings_change_password.*
import kotlinx.android.synthetic.main.activity_settings_update.*

class SettingsChangePasswordActivity : AppCompatActivity() {

    var username : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_change_password)

        val bundle = intent.extras
        bundle?.let {

            username = it.getString("Username")
        }

        val userNewPassword = findViewById<EditText>(R.id.enterNewPasswordEditText)

        settingsConfirmChangePasswordButton.setOnClickListener {

            if (userNewPassword.text.toString() == "" ) {

                Toast.makeText(applicationContext,getString(R.string.missing_information), Toast.LENGTH_SHORT).show()
            }

            else {

                VolleyHelper.instance.recoverUserPassword (

                    this@SettingsChangePasswordActivity, username!!, userNewPassword.text.toString()) {

                    if (it) {

                        Toast.makeText(applicationContext, getString(R.string.change_confirmed), Toast.LENGTH_LONG).show()

                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    }
                    else {

                        Toast.makeText(applicationContext,getString(R.string.change_failed), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}