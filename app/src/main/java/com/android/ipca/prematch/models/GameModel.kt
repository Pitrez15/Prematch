package com.android.ipca.prematch.models

import org.json.JSONObject


class GameModel {

    var gameID : Int? = null
    var homeTeamID : Int? = null
    var awayTeamID : Int? = null
    var tournamentID : Int? = null
    var goalsHomeTeam : Int? = null
    var goalsAwayTeam : Int? = null

    companion object {

        fun parseJSON (jsonArticle : JSONObject) : GameModel {

            val game = GameModel()

            game.gameID = jsonArticle.getInt("GAME_ID")
            game.homeTeamID = jsonArticle.getInt("HOME_TEAM_ID")
            game.awayTeamID = jsonArticle.getInt("AWAY_TEAM_ID")
            game.tournamentID = jsonArticle.getInt("TOURNAMENT_ID")
            game.goalsHomeTeam = jsonArticle.getInt("GOALS_HOME_TEAM")
            game.goalsAwayTeam = jsonArticle.getInt("GOALS_AWAY_TEAM")

            return game
        }
    }
}