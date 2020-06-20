package com.android.ipca.prematch.models

import org.json.JSONObject

class GameEventsModel {

    var gameID : Int? = null
    var eventID : Int? = null
    var eventPlayerID : Int? = null
    //var eventPlayerName : String? = null
    var eventGameTime : Int? = null
    var eventTeamID : Int? = null
    //var eventTeamName : String? = null
    var gameEventID : Int? = null

    companion object {

        fun parseJSON (jsonArticle : JSONObject) : GameEventsModel {

            val gameEvents = GameEventsModel()

            gameEvents.gameID = jsonArticle.getInt("GAME_ID")
            gameEvents.eventID = jsonArticle.getInt("EVENT_ID")
            gameEvents.eventPlayerID = jsonArticle.getInt("PLAYER_ID")
            //gameEvents.eventPlayerName = jsonArticle.getInt("AWAY_TEAM_ID")
            gameEvents.eventGameTime = jsonArticle.getInt("GAME_TIME")
            gameEvents.eventTeamID = jsonArticle.getInt("TEAM_ID")
            //gameEvents.eventTeamName = jsonArticle.getString("STAGE")
            gameEvents.gameEventID = jsonArticle.getInt("GAME_TIME_ID")

            return gameEvents
        }
    }
}