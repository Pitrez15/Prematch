package com.android.ipca.prematch.models

import org.json.JSONObject

class TeamModel {

    var teamID : Int? = null
    var teamName : String? = null
    var teamInitials : String? = null
    var teamCity : String? = null
    var teamPrimaryColor : String? = null
    var teamSecondaryColor : String? = null
    var teamContactEmail : String? = null
    var teamContactPhone : Int? = null
    var teamTournamentID : Int? = null

    companion object {

        fun parseJSON (jsonArticle : JSONObject) : TeamModel {

            val team = TeamModel()

            team.teamID = jsonArticle.getInt("TEAM_ID")
            team.teamName = jsonArticle.getString("TEAM_NAME")
            team.teamInitials = jsonArticle.getString("TEAM_INITIALS")
            team.teamCity = jsonArticle.getString("TEAM_CITY")
            team.teamPrimaryColor = jsonArticle.getString("TEAM_PRIMARY_COLOR")
            team.teamSecondaryColor = jsonArticle.getString("TEAM_SECONDARY_COLOR")
            team.teamContactEmail = jsonArticle.getString("TEAM_EMAIL")
            team.teamContactPhone = jsonArticle.getInt("TEAM_PHONE")
            team.teamTournamentID = jsonArticle.getInt("TOURNAMENT_ID")

            return team
        }
    }
}