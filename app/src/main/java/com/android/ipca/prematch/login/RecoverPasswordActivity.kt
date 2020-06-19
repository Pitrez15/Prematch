package com.android.ipca.prematch.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.android.ipca.prematch.R
import com.android.ipca.prematch.helpers.VolleyHelper
import kotlinx.android.synthetic.main.activity_recover_password.*

class RecoverPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recover_password)

        val username = findViewById<EditText>(R.id.enterUsernameEditText)
        val userNewPassword = findViewById<EditText>(R.id.enterPasswordEditText)

        recoverPasswordButton.setOnClickListener {

            if (username.text.toString() == "" || userNewPassword.text.toString() == "") {

                Toast.makeText(applicationContext,getString(R.string.missing_information), Toast.LENGTH_SHORT).show()
            }
            else {

                VolleyHelper.instance.recoverUserPassword (

                    this@RecoverPasswordActivity, username.text.toString(), userNewPassword.text.toString()) {

                    if (it) {

                        val intent = Intent(this, LoginActivity::class.java)
                        Toast.makeText(applicationContext, getString(R.string.password_recovered), Toast.LENGTH_LONG).show()
                        startActivity(intent)
                    }
                    else {

                        Toast.makeText(applicationContext,getString(R.string.failed_recover),Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}