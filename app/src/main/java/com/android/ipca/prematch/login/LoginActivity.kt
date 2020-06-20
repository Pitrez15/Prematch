package com.android.ipca.prematch.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.android.ipca.prematch.R
import com.android.ipca.prematch.helpers.VolleyHelper
import com.android.ipca.prematch.main.TournamentFavoritesActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.enterUsernameEditText)
        val password = findViewById<EditText>(R.id.enterPasswordEditText)

        loginButton.setOnClickListener {

            VolleyHelper.instance.userLogin(

                this@LoginActivity,
                username.text.toString(),
                password.text.toString()) {

                if (it) {

                    val intent = Intent(this, TournamentFavoritesActivity::class.java)
                    intent.putExtra("Username", username.text.toString())
                    startActivity(intent)
                }
                else {

                    Toast.makeText(applicationContext,getString(R.string.incorrect_login), Toast.LENGTH_SHORT).show()
                }
            }
        }

        createAccountClickableTextView.setOnClickListener {

            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        recoverPasswordClickableTextView.setOnClickListener {

            val intent = Intent(this, RecoverPasswordActivity::class.java)
            startActivity(intent)
        }
    }
}