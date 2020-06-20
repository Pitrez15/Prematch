package com.android.ipca.prematch.models

import org.json.JSONObject

class EventsModel {

    var eventID : Int? = null
    var eventName : String? = null

    companion object {

        fun parseJSON (jsonArticle : JSONObject) : EventsModel {

            val event = EventsModel()

            event.eventID = jsonArticle.getInt("EVENT_ID")
            event.eventName = jsonArticle.getString("DESIGNATION")

            return event
        }
    }
}