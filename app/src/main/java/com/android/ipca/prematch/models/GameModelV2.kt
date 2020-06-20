package com.android.ipca.prematch.models

import org.json.JSONObject

class GameModelV2 {

    var gameID : Int? = null
    var homeTeamID : Int? = null
    var homeTeamName : String? = null
    var awayTeamID : Int? = null
    var awayTeamName : String? = null
    var tournamentID : Int? = null
    var gameStage : String? = null

    companion object {

        fun parseJSON (jsonArticle : JSONObject) : GameModel {

            val game = GameModel()

            game.gameID = jsonArticle.getInt("GAME_ID")
            game.homeTeamID = jsonArticle.getInt("HOME_TEAM_ID")
            game.homeTeamName = jsonArticle.getString("HOME_TEAM_NAME")
            game.awayTeamID = jsonArticle.getInt("AWAY_TEAM_ID")
            game.awayTeamName = jsonArticle.getString("AWAY_TEAM_NAME")
            game.tournamentID = jsonArticle.getInt("TOURNAMENT_ID")
            game.gameStage = jsonArticle.getString("STAGE")

            return game
        }
    }
}