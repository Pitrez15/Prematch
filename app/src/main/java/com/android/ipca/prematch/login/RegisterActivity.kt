package com.android.ipca.prematch.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.android.ipca.prematch.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        termsClickableTextView.setOnClickListener {

            val intent = Intent(this, TermsAndConditionsActivity::class.java)
            startActivity(intent)
        }
    }
}