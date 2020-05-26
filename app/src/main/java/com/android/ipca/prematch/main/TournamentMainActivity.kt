package com.android.ipca.prematch.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
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

            //val fragment = TournamentFavoritesFragment()
            //supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment, fragment.javaClass.getSimpleName()).commit()
            findNavController(R.id.nav_host_fragment).navigate(R.id.tournamentFavoritesFragment)
        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {menuItem ->

        //var fragment : Fragment = TournamentFavoritesFragment()

        when (menuItem.itemId) {

            R.id.menu_tournament_main -> {
                //fragment = TournamentFavoritesFragment()
                findNavController(R.id.nav_host_fragment).navigate(R.id.tournamentFavoritesFragment)
            }
            R.id.menu_team_main -> {
                //fragment = TeamFavoritesFragment()
                findNavController(R.id.nav_host_fragment).navigate(R.id.teamFavoritesFragment)
            }
            R.id.menu_player_main -> {
                //fragment = PlayerFavoritesFragment()
                findNavController(R.id.nav_host_fragment).navigate(R.id.playerFavoritesFragment)
            }
            R.id.menu_settings_main -> {
                //fragment = TournamentSearchFragment()
                findNavController(R.id.nav_host_fragment).navigate(R.id.settingsFragment)
            }
        }

        /*supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .commit()*/

        true
    }
}
