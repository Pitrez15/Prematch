package com.android.ipca.prematch.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import com.android.ipca.prematch.R
import com.android.ipca.prematch.helpers.VolleyHelper
import com.android.ipca.prematch.models.TournamentModel
import kotlinx.android.synthetic.main.activity_tournament_detail.*
import org.json.JSONObject

class TournamentDetailActivity : AppCompatActivity() {

    var tournamentID : Int? = null
    var teamsNumber : Int? = null
    var username : String? = null

    var tournament : MutableList<TournamentModel> = ArrayList()
    var tournamentAdapter : TournamentAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tournament_detail)

        val bundle = intent.extras
        bundle?.let {

            tournamentID = it.getInt("Tournament ID")
            teamsNumber = it.getInt("Teams Number")
            username = it.getString("Username")
        }

        tournamentAdapter = TournamentAdapter()
        tournamentDetailsListView.adapter = tournamentAdapter

        VolleyHelper.instance.getTournamentByID(this, tournamentID!!.toInt()) {response ->

            response?.let {

                for(index in 0 until it.length()){

                    val tournamentsJSON : JSONObject = it[index] as JSONObject
                    tournament.add(TournamentModel.parseJSON(tournamentsJSON))
                }
                tournamentAdapter?.notifyDataSetChanged()
            }
        }

        tournamentDetailTeamsButton.setOnClickListener {

            val intent = Intent(this, TournamentDetailTeamsActivity::class.java)
            intent.putExtra("Tournament ID", tournamentID!!.toInt())
            intent.putExtra("Teams Number", teamsNumber!!.toInt())
            intent.putExtra("Username", username!!)
            startActivity(intent)
        }

        tournamentDetailGamesButton.setOnClickListener {

            val intent = Intent(this, TournamentDetailGamesActivity::class.java)
            intent.putExtra("Tournament ID", tournamentID!!.toInt())
            intent.putExtra("Teams Number", teamsNumber!!.toInt())
            intent.putExtra("Username", username!!)
            startActivity(intent)
        }

        tournamentDetailStatsButton.setOnClickListener {

            val intent = Intent(this, TournamentDetailStatsActivity::class.java)
            intent.putExtra("Tournament ID", tournamentID!!.toInt())
            intent.putExtra("Teams Number", teamsNumber!!.toInt())
            intent.putExtra("Username", username!!)
            startActivity(intent)
        }

        tournamentDetailBackButton.setOnClickListener {

            val intent = Intent(this, TournamentFavoritesActivity::class.java)
            intent.putExtra("Username", username!!)
            startActivity(intent)
        }

        tournamentDetailDeleteButton.setOnClickListener {

            val intent = Intent(this@TournamentDetailActivity, TournamentFavoritesActivity::class.java)
            intent.putExtra("Username", username!!)

            VolleyHelper.instance.deleteGameByTournamentID(this@TournamentDetailActivity, tournamentID!!.toInt()) {

                if (it) {

                    Toast.makeText(applicationContext,"Games Deleted !", Toast.LENGTH_SHORT).show()
                }
                else {

                    Toast.makeText(applicationContext,"Failed to Delete Games !", Toast.LENGTH_SHORT).show()
                }
            }

            VolleyHelper.instance.deletePlayerByTournamentID(this@TournamentDetailActivity, tournamentID!!.toInt()) {

                if (it) {

                    Toast.makeText(applicationContext,"Players Deleted !", Toast.LENGTH_SHORT).show()
                }
                else {

                    Toast.makeText(applicationContext,"Failed to Delete Players !", Toast.LENGTH_SHORT).show()
                }
            }

            VolleyHelper.instance.deleteTeamByTournamentID(this@TournamentDetailActivity, tournamentID!!.toInt()) {

                if (it) {

                    Toast.makeText(applicationContext,"Teams Deleted !", Toast.LENGTH_SHORT).show()
                }
                else {

                    Toast.makeText(applicationContext,"Failed to Delete Teams !", Toast.LENGTH_SHORT).show()
                }
            }

            VolleyHelper.instance.deleteTournamentByID(this@TournamentDetailActivity, tournamentID!!.toInt()) {

                if (it) {

                    Toast.makeText(applicationContext,"Tournament Deleted !", Toast.LENGTH_SHORT).show()
                }
                else {

                    Toast.makeText(applicationContext,"Failed to Delete Tournament !", Toast.LENGTH_SHORT).show()
                }
            }

            startActivity(intent)
        }
    }

    inner class TournamentAdapter : BaseAdapter() {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            val rowTournamentDetail = layoutInflater.inflate(R.layout.row_tournament_detail, parent, false)

            val tournamentName = findViewById<TextView>(R.id.detailTournamentTextView)
            val startDate = rowTournamentDetail.findViewById<TextView>(R.id.startDateTextView)
            val finishDate = rowTournamentDetail.findViewById<TextView>(R.id.finishDateTextView)
            val tournamentType = rowTournamentDetail.findViewById<TextView>(R.id.tournamentTypeTextView)
            val teamsNumber = rowTournamentDetail.findViewById<TextView>(R.id.teamsNumberTextView)
            val contactEmail = rowTournamentDetail.findViewById<TextView>(R.id.contactEmailTextView)
            val contactPhone = rowTournamentDetail.findViewById<TextView>(R.id.contactPhoneTextView)

            tournamentName.text = tournament[position].tournamentName
            startDate.text = tournament[position].startDate
            finishDate.text = tournament[position].finishDate
            tournamentType.text = tournament[position].tournamentType
            teamsNumber.text = tournament[position].teamsNumber.toString()
            contactEmail.text = tournament[position].contactEmail
            contactPhone.text = tournament[position].contactPhone.toString()

            return rowTournamentDetail
        }

        override fun getItem(position: Int): Any {

            return tournament[position]
        }

        override fun getItemId(position: Int): Long {

            return 0
        }

        override fun getCount(): Int {

            return tournament.size
        }
    }
}