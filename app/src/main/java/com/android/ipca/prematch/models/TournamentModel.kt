package com.android.ipca.prematch.models

import org.json.JSONObject

class TournamentModel {

    var tournamentID : Int? = null
    var tournamentName: String? = null
    var startDate: String? = null
    var finishDate: String? = null
    var contactEmail: String? = null
    var contactPhone: Int? = null
    var teamsNumber: Int? = null
    var tournamentType: String? = null

    companion object {

        fun parseJSON (jsonArticle : JSONObject) : TournamentModel {

            val tournament = TournamentModel()

            tournament.tournamentID = jsonArticle.getInt("TOURNAMENT_ID")
            tournament.tournamentName = jsonArticle.getString("TOURNAMENT_NAME")
            tournament.startDate = jsonArticle.getString("START_DATE")
            tournament.finishDate = jsonArticle.getString("FINISH_DATE")
            tournament.contactEmail = jsonArticle.getString("TOURNAMENT_EMAIL")
            tournament.contactPhone = jsonArticle.getInt("TOURNAMENT_PHONE")
            tournament.teamsNumber = jsonArticle.getInt("TEAMS_NUMBER")
            tournament.tournamentType = jsonArticle.getString("TOURNAMENT_TYPE")

            return tournament
        }
    }
}