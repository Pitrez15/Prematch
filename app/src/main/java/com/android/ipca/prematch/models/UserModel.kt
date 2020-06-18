package com.android.ipca.prematch.models

import org.json.JSONObject

class UserModel {

    var userFirstName : String? = null
    var userLastName : String? = null
    var username : String? = null
    var password : String? = null
    var userEmail : String? = null

    companion object {

        fun parseJSON (jsonArticle : JSONObject) : UserModel {

            val user = UserModel()

            user.userFirstName = jsonArticle.getString("FIRST_NAME")
            user.userLastName = jsonArticle.getString("LAST_NAME")
            user.username = jsonArticle.getString("USERNAME")
            user.password = jsonArticle.getString("PASSWORD")
            user.userEmail = jsonArticle.getString("EMAIL")

            return user
        }
    }
}