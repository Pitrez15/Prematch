package com.android.ipca.prematch.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.ipca.prematch.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class TournamentMainActivity : AppCompatActivity() {

    private lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tournament_main)

        bottomNav = findViewById(R.id.bottomNavMenu)
        bottomNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        if (savedInstanceState == null) {

            val fragment = TournamentFavoritesFragment()
            supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment, fragment.javaClass.getSimpleName()).commit()
        }

        /*when (bottomNav.selectedItemId){

            R.id.menu_tournament_main -> {

                val fragment = TournamentFavoritesFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment, fragment.javaClass.simpleName)
                    .commit()
            }
            R.id.menu_team_main -> {
                val fragment = TeamFavoritesFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment, fragment.javaClass.simpleName)
                    .commit()
            }
            R.id.menu_player_main -> {
                val fragment = PlayerFavoritesFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment, fragment.javaClass.simpleName)
                    .commit()
            }
            R.id.menu_settings_main -> {
                val fragment = SettingsFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment, fragment.javaClass.simpleName)
                    .commit()
            }
        }*/
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {menuItem ->

        when (menuItem.itemId) {

            R.id.menu_tournament_main -> {
                val fragment = TournamentFavoritesFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment, fragment.javaClass.simpleName)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_team_main -> {
                val fragment = TeamFavoritesFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment, fragment.javaClass.simpleName)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_player_main -> {
                val fragment = PlayerFavoritesFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment, fragment.javaClass.simpleName)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_settings_main -> {
                val fragment = TournamentSearchFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment, fragment.javaClass.simpleName)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}
