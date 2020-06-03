package com.android.ipca.prematch.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.android.ipca.prematch.R
import com.android.ipca.prematch.main.TournamentFavoritesActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton.setOnClickListener {

            val intent = Intent(this, TournamentFavoritesActivity::class.java)
            startActivity(intent)
        }

        createAccountClickableTextView.setOnClickListener {

            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}