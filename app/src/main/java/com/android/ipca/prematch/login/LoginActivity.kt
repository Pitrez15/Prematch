package com.android.ipca.prematch.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.findNavController
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

            //val intent = Intent(this, TournamentFavoritesActivity::class.java)
            //startActivity(intent)

            VolleyHelper.instance.userLogin(

                this@LoginActivity,
                username.text.toString(),
                password.text.toString()) {

                if (it) {

                    val intent = Intent(this@LoginActivity, TournamentFavoritesActivity::class.java)
                    intent.putExtra("username", username.text.toString())
                    startActivity(intent)
                }
                else {

                    Toast.makeText(applicationContext,"Incorrect Login!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        createAccountClickableTextView.setOnClickListener {

            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}