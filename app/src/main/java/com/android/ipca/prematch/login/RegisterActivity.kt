package com.android.ipca.prematch.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.android.ipca.prematch.R
import com.android.ipca.prematch.helpers.VolleyHelper
import com.android.ipca.prematch.login.LoginActivity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val firstName = findViewById<EditText>(R.id.enterFirstNameEditText)
        val lastName = findViewById<EditText>(R.id.enterLastNameEditText)
        val username = findViewById<EditText>(R.id.enterUsernameEditText)
        val userEmail = findViewById<EditText>(R.id.enterEmailEditText)
        val userPassword = findViewById<EditText>(R.id.enterPasswordEditText)

        createAccountButton.setOnClickListener {

            /*VolleyHelper.instance.createNewUser (

                this@RegisterActivity, firstName.text.toString(), lastName.text.toString(),
                username.text.toString(), userEmail.text.toString(),
                userPassword.text.toString()) {

                if (it) {

                    val intent = Intent(this, LoginActivity::class.java)
                    Toast.makeText(applicationContext, "Registry Confirmed!", Toast.LENGTH_LONG).show()
                    startActivity(intent)
                }
                else {

                    Toast.makeText(applicationContext,"Failed Registration!",Toast.LENGTH_SHORT).show()
                }
            }*/

            if (firstName.text.toString() == "" || lastName.text.toString() == "" || username.text.toString() == "" ||
                userEmail.text.toString() == "" || userPassword.text.toString() == "" || !termsCheckBox.isChecked) {

                Toast.makeText(applicationContext,"Missing Information or Terms unchecked!",Toast.LENGTH_SHORT).show()
            }

            else {

                VolleyHelper.instance.createNewUser (

                    this@RegisterActivity, firstName.text.toString(), lastName.text.toString(),
                                                    username.text.toString(), userEmail.text.toString(),
                                                    userPassword.text.toString()) {

                    if (it) {

                        val intent = Intent(this, LoginActivity::class.java)
                        Toast.makeText(applicationContext, "Registry Confirmed!", Toast.LENGTH_LONG).show()
                        startActivity(intent)
                    }
                    else {

                        Toast.makeText(applicationContext,"Failed Registration!",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        termsClickableTextView.setOnClickListener {

            val intent = Intent(this, TermsAndConditionsActivity::class.java)
            startActivity(intent)
        }
    }
}