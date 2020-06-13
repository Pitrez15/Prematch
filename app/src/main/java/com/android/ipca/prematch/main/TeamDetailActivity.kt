package com.android.ipca.prematch.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.android.ipca.prematch.R
import com.android.ipca.prematch.helpers.VolleyHelper
import com.android.ipca.prematch.models.TeamModel
import kotlinx.android.synthetic.main.activity_team_detail.*
import org.json.JSONObject

class TeamDetailActivity : AppCompatActivity() {

    var teamID : Int? = null

    var team : MutableList<TeamModel> = ArrayList()
    var teamAdapter : TeamAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)

        teamAdapter = TeamAdapter()
        teamDetailsListView.adapter = teamAdapter

        val bundle = intent.extras
        bundle?.let {

            teamID = it.getInt("Team ID")
        }

        VolleyHelper.instance.getTeamByID(this, teamID!!.toInt()) { response ->

            response?.let {

                for(index in 0 until it.length()){

                    val teamJSON : JSONObject = it[index] as JSONObject
                    team.add(TeamModel.parseJSON(teamJSON))
                }
                teamAdapter?.notifyDataSetChanged()
            }
        }
    }

    inner class TeamAdapter : BaseAdapter() {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            val rowView = layoutInflater.inflate(R.layout.row_team_detail, parent, false)

            val textViewTeamName = findViewById<TextView>(R.id.detailTeamTextView)
            val textViewTeamInitials = rowView.findViewById<TextView>(R.id.teamInitialsTextView)
            val textViewTeamCity = rowView.findViewById<TextView>(R.id.teamCityTextView)
            val textViewTeamPrimaryColor = rowView.findViewById<TextView>(R.id.teamPrimaryColorTextView)
            val textViewTeamSecondaryColor = rowView.findViewById<TextView>(R.id.teamSecondaryColorTextView)
            val textViewTeamEmailColor = rowView.findViewById<TextView>(R.id.teamEmailTextView)
            val textViewTeamPhoneColor = rowView.findViewById<TextView>(R.id.teamContactPhoneTextView)

            textViewTeamName.text = team[position].teamName
            textViewTeamInitials.text = team[position].teamInitials
            textViewTeamCity.text = team[position].teamCity
            textViewTeamPrimaryColor.text = team[position].teamPrimaryColor
            textViewTeamSecondaryColor.text = team[position].teamSecondaryColor
            textViewTeamEmailColor.text = team[position].teamContactEmail
            textViewTeamPhoneColor.text = team[position].teamContactPhone.toString()

            return rowView
        }

        override fun getItem(position: Int): Any {

            return team[position]
        }

        override fun getItemId(position: Int): Long {

            return 0
        }

        override fun getCount(): Int {

            return team.size
        }


    }
}