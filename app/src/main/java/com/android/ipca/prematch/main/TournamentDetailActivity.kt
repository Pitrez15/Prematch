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

        var tournamentName : String? = null

        VolleyHelper.instance.getTournamentByID(this, tournamentID!!.toInt()) {response ->

            response?.let {

                for(index in 0 until it.length()){

                    val tournamentsJSON : JSONObject = it[index] as JSONObject
                    tournament.add(TournamentModel.parseJSON(tournamentsJSON))
                    tournamentName = tournament[index].tournamentName
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

                    Toast.makeText(applicationContext,getString(R.string.game_deleted), Toast.LENGTH_SHORT).show()
                }
                else {

                    Toast.makeText(applicationContext,getString(R.string.failed_to_delete_game), Toast.LENGTH_SHORT).show()
                }
            }

            VolleyHelper.instance.deletePlayerByTournamentID(this@TournamentDetailActivity, tournamentID!!.toInt()) {

                if (it) {

                    Toast.makeText(applicationContext,getString(R.string.player_deleted), Toast.LENGTH_SHORT).show()
                }
                else {

                    Toast.makeText(applicationContext,getString(R.string.failed_to_delete_player), Toast.LENGTH_SHORT).show()
                }
            }

            VolleyHelper.instance.deleteTeamByTournamentID(this@TournamentDetailActivity, tournamentID!!.toInt()) {

                if (it) {

                    Toast.makeText(applicationContext,getString(R.string.team_deleted), Toast.LENGTH_SHORT).show()
                }
                else {

                    Toast.makeText(applicationContext,getString(R.string.failed_to_delete_team), Toast.LENGTH_SHORT).show()
                }
            }

            VolleyHelper.instance.deleteTournamentByID(this@TournamentDetailActivity, tournamentID!!.toInt()) {

                if (it) {

                    Toast.makeText(applicationContext,getString(R.string.tournament_deleted), Toast.LENGTH_SHORT).show()
                }
                else {

                    Toast.makeText(applicationContext,getString(R.string.failed_to_delete_tournament), Toast.LENGTH_SHORT).show()
                }
            }

            startActivity(intent)
        }

        tournamentDetailShareButton.setOnClickListener {

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_SUBJECT, tournamentID!!)
            intent.putExtra(Intent.EXTRA_TEXT, "Go Follow Tournament " + tournamentName + " at Prematch App!")
            startActivity(Intent.createChooser(intent, "Tournament Share"))
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