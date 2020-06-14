package com.android.ipca.prematch.models

import org.json.JSONObject

class PlayerModel {

    var playerID : Int? = null
    var playerFirstName : String? = null
    var playerLastName : String? = null
    var playerPosition : String? = null
    var playerTeamID : Int? = null
    var playerHeight : Int? = null
    var playerAge : Int? = null

    companion object {

        fun parseJSON (jsonArticle : JSONObject) : PlayerModel {

            val player = PlayerModel()

            player.playerID = jsonArticle.getInt("PLAYER_ID")
            player.playerFirstName = jsonArticle.getString("PLAYER_FIRST_NAME")
            player.playerLastName = jsonArticle.getString("PLAYER_LAST_NAME")
            player.playerPosition = jsonArticle.getString("POSITION")
            player.playerTeamID = jsonArticle.getInt("TEAM_ID")
            player.playerHeight = jsonArticle.getInt("PLAYER_HEIGHT")
            player.playerAge = jsonArticle.getInt("PLAYER_AGE")

            return player
        }
    }
}