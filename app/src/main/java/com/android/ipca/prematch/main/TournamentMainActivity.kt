package com.android.ipca.prematch.main

import android.content.ClipData
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.view.menu.ActionMenuItem
import androidx.appcompat.view.menu.MenuItemImpl
import androidx.appcompat.view.menu.MenuView
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager.widget.ViewPager
import com.android.ipca.prematch.R
import com.android.ipca.prematch.databinding.ActivityTournamentMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_tournament_main.*
import java.lang.reflect.Array.newInstance
import kotlin.random.Random.Default.Companion

class TournamentMainActivity : AppCompatActivity() {

    private lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityTournamentMainBinding>(this, R.layout.activity_tournament_main)

        bottomNav = binding.bottomNavMenu
        bottomNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        if (savedInstanceState == null) {

            val fragment = TournamentFavoritesFragment()
            supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment, fragment.javaClass.getSimpleName()).commit()
        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {menuItem ->

        when (menuItem.itemId) {

            R.id.menu_tournament_main -> {
                val fragment = TournamentFavoritesFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment, fragment.javaClass.getSimpleName())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_team_main -> {
                val fragment = TeamFavoritesFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment, fragment.javaClass.getSimpleName())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_player_main -> {
                val fragment = PlayerFavoritesFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment, fragment.javaClass.getSimpleName())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_settings_main -> {
                val fragment = TournamentSearchFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment, fragment.javaClass.getSimpleName())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


}
