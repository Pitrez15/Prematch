package com.android.ipca.prematch.helpers

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.jetbrains.anko.doAsync
import org.json.JSONArray
import org.json.JSONObject
import kotlin.contracts.contract

class VolleyHelper {

    private var queue : RequestQueue? = null

    fun createNewUser (context : Context, firstName : String, lastName : String, username : String,
                       userEmail : String, password : String, userEvent : ((Boolean) -> Unit)) {

        doAsync {

            queue = Volley.newRequestQueue(context)

            val jsonObject = JSONObject()

            jsonObject.put("FIRST_NAME", firstName)
            jsonObject.put("LAST_NAME", lastName)
            jsonObject.put("USERNAME", username)
            jsonObject.put("EMAIL", userEmail)
            jsonObject.put("PASSWORD", password)

            val jsonObjectRequest = object : JsonObjectRequest (

                Method.POST,
                BASE_API + REGISTER,
                jsonObject,
                Response.Listener {

                    userEvent.invoke(true)
                    Log.d("VolleyHelper", it.toString())
                },
                Response.ErrorListener {

                    Log.d("VolleyHelper", it.toString())
                }
            ) {

                override fun getHeaders() : MutableMap<String, String> {

                    val map : MutableMap<String, String> = mutableMapOf()

                    map.put("Content-Type", "application/json")

                    return map
                }
            }
            queue!!.add(jsonObjectRequest)
        }
    }

    fun userLogin (context : Context, username : String, password : String, loginEvent : ((Boolean) -> Unit)) {

        doAsync {

            queue = Volley.newRequestQueue(context)

            val jsonObject = JSONObject()
            jsonObject.put("USERNAME", username)
            jsonObject.put("PASSWORD", password)

            val jsonObjectRequest = JsonObjectRequest (

                Request.Method.POST,
                BASE_API + LOGIN,
                jsonObject,
                Response.Listener {

                    Log.d("VolleyHelper", it.toString())
                    if (it.getBoolean("auth") == true) {

                        token = it.getString("token")
                        loginEvent.invoke(true)
                    }
                    else {

                        loginEvent.invoke(false)
                    }
                },
                Response.ErrorListener {
                    Log.d("VolleyHelper", it.toString())
                    loginEvent.invoke(false)
                }
            )
            queue!!.add(jsonObjectRequest)
        }
    }

    fun getTournaments (context: Context, tournamentsEvent : ((JSONArray?) -> Unit)) {

        doAsync {

            queue = Volley.newRequestQueue(context)

            val stringRequest = object : StringRequest(

                Method.GET,
                BASE_API + GET_TOURNAMENTS,
                Response.Listener {

                    tournamentsEvent.invoke(JSONArray(it))
                },
                Response.ErrorListener {

                    Log.d("VolleyHelper", it.toString())
                    tournamentsEvent.invoke(null)
                }
            ) {

                override fun getHeaders(): MutableMap<String, String> {

                    val map : MutableMap<String, String> = mutableMapOf()
                    map.put("Content-Type", "application/json")
                    return map
                }
            }

            queue!!.add(stringRequest)
        }
    }

    fun getTournamentByID (context: Context, tournamentID : Int, tournamentsEvent: ((JSONArray?) -> Unit)) {

        doAsync {

            queue = Volley.newRequestQueue(context)

            val stringRequest = object : StringRequest(

                Method.GET,
                BASE_API + GET_TOURNAMENTS + "/" + tournamentID,
                Response.Listener {

                    tournamentsEvent.invoke(JSONArray(it))
                },
                Response.ErrorListener {

                    Log.d("Volley Helper", it.toString())
                    tournamentsEvent.invoke(null)
                }
            ) {

                override fun getHeaders(): MutableMap<String, String> {

                    val map : MutableMap<String, String> = mutableMapOf()
                    map.put("Content-Type", "application/json")
                    return map
                }
            }

            queue?.add(stringRequest)
        }
    }

    fun createNewTournament (context: Context, tournamentID : Int, tournamentName : String,
                             startDate : String, finishDate : String, tournamentEmail : String,
                             tournamentPhone : Int, teamsNumber : Int, tournamentType : String,
                             tournamentsEvent : ((Boolean) -> Unit)) {

        doAsync {

            queue = Volley.newRequestQueue(context)

            val jsonObject = JSONObject()

            jsonObject.put("TOURNAMENT_ID", tournamentID)
            jsonObject.put("TOURNAMENT_NAME", tournamentName)
            jsonObject.put("START_DATE", startDate)
            jsonObject.put("FINISH_DATE", finishDate)
            jsonObject.put("TOURNAMENT_EMAIL", tournamentEmail)
            jsonObject.put("TOURNAMENT_PHONE", tournamentPhone)
            jsonObject.put("TEAMS_NUMBER", teamsNumber)
            jsonObject.put("TOURNAMENT_TYPE", tournamentType)

            val jsonObjectRequest = object : JsonObjectRequest(

                Method.POST,
                BASE_API + NEW_TOURNAMENT,
                jsonObject,
                Response.Listener {

                    tournamentsEvent.invoke(true)
                    Log.d("VolleyHelper", it.toString())
                },
                Response.ErrorListener {
                    Log.d("VolleyHelper", it.toString())
                }
            ) {

                override fun getHeaders(): MutableMap<String, String> {

                    val map : MutableMap<String, String> = mutableMapOf()
                    map.put("Content-Type", "application/json")
                    return map
                }
            }

            queue!!.add(jsonObjectRequest)
        }
    }


    fun getTeams (context: Context, teamsEvent : ((JSONArray?) -> Unit)) {

        doAsync {

            queue = Volley.newRequestQueue(context)

            val stringRequest = object : StringRequest(

                Method.GET,
                BASE_API + GET_TEAMS,
                Response.Listener {

                    teamsEvent.invoke(JSONArray(it))
                },
                Response.ErrorListener {

                    Log.d("VolleyHelper", it.toString())
                    teamsEvent.invoke(null)
                }
            ) {

                override fun getHeaders(): MutableMap<String, String> {

                    val map : MutableMap<String, String> = mutableMapOf()
                    map.put("Content-Type", "application/json")
                    return map
                }
            }

            queue!!.add(stringRequest)
        }
    }


    fun getPlayers (context: Context, playersEvent : ((JSONArray?) -> Unit)) {

        doAsync {

            queue = Volley.newRequestQueue(context)

            val stringRequest = object : StringRequest(

                Method.GET,
                BASE_API + GET_PLAYERS,
                Response.Listener {

                    playersEvent.invoke(JSONArray(it))
                },
                Response.ErrorListener {

                    Log.d("VolleyHelper", it.toString())
                    playersEvent.invoke(null)
                }
            ) {

                override fun getHeaders(): MutableMap<String, String> {

                    val map : MutableMap<String, String> = mutableMapOf()
                    map.put("Content-Type", "application/json")
                    return map
                }
            }

            queue!!.add(stringRequest)
        }
    }

    companion object {

        const val BASE_API = "http://192.168.1.66:3000"
        const val REGISTER = "/user/registry"
        const val LOGIN = "/user/login"
        const val GET_TOURNAMENTS = "/api/tournaments"
        const val NEW_TOURNAMENT = "/api/new_tournament"
        const val GET_TEAMS = "/api/teams"
        const val GET_PLAYERS = "/api/players"

        var token = ""

        private var mInstance : VolleyHelper? = VolleyHelper()
        val instance : VolleyHelper

        @Synchronized get() {

            if (null == mInstance){

                mInstance = VolleyHelper()
            }
            return mInstance!!
        }
    }
}