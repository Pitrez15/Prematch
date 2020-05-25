package com.android.ipca.prematch.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.ipca.prematch.R
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.android.ipca.prematch.databinding.ActivityLoginMainBinding
import com.android.ipca.prematch.login.LoginMainActivity


class LoginMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityLoginMainBinding>(this, R.layout.activity_login_main)
    }
}
