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


    //---------------USER--------------

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


    fun getUserByUsername (context: Context, username : String, usersEvent: ((JSONArray?) -> Unit)) {

        doAsync {

            queue = Volley.newRequestQueue(context)

            val stringRequest = object : StringRequest(

                Method.GET,
                BASE_API + GET_USER + "/" + username,
                Response.Listener {

                    usersEvent.invoke(JSONArray(it))
                },
                Response.ErrorListener {

                    Log.d("Volley Helper", it.toString())
                    usersEvent.invoke(null)
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


    fun recoverUserPassword (context: Context, username: String, password: String, recoverEvent : ((Boolean) -> Unit)) {

        doAsync {

            queue = Volley.newRequestQueue(context)

            val jsonObject = JSONObject()
            jsonObject.put("PASSWORD", password)

            val jsonObjectRequest = object : JsonObjectRequest(

                Method.PUT,
                BASE_API + RECOVER + "/" + username,
                jsonObject,
                Response.Listener {

                    recoverEvent.invoke(true)
                    Log.d("VolleyHelper", it.toString())
                },
                Response.ErrorListener {

                    Log.d("VolleyHelper", it.toString())
                }
            ){
                override fun getHeaders(): MutableMap<String, String> {

                    val map : MutableMap<String, String> = mutableMapOf()
                    map.put("Content-Type","application/json")

                    return map
                }
            }
            queue!!.add(jsonObjectRequest)
        }
    }


    //---------------Tournaments--------------

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


    fun deleteTournamentByID (context: Context, tournamentID : Int, tournamentEvent : ((Boolean) -> Unit)) {

        doAsync {

            queue = Volley.newRequestQueue(context)

            val stringRequest = object : StringRequest(

                Method.DELETE,
                BASE_API + DELETE_TOURNAMENT + "/" + tournamentID,
                Response.Listener {

                    tournamentEvent.invoke(true)
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

            queue!!.add(stringRequest)
        }
    }

    //---------------Teams--------------

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


    fun getTeamsByTournamentID (context: Context, tournamentID : Int, teamsEvent : ((JSONArray?) -> Unit)) {

        doAsync {

            queue = Volley.newRequestQueue(context)

            val stringRequest = object : StringRequest(

                Method.GET,
                BASE_API + GET_TEAMS_TOURNAMENT_ID + "/" + tournamentID,
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


    fun getTeamByID (context: Context, teamID : Int, teamsEvent : ((JSONArray?) -> Unit)) {

        doAsync {

            queue = Volley.newRequestQueue(context)

            val stringRequest = object : StringRequest(

                Method.GET,
                BASE_API + GET_TEAM_TEAM_ID + "/" + teamID,
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


    fun createNewTeam (context: Context, teamID : Int, teamName : String,
                       teamInitials : String, teamCity : String, teamPrimaryColor : String,
                       teamSecondaryColor : String, teamEmail : String, teamPhone : Int, tournamentID : Int,
                       tournamentsEvent : ((Boolean) -> Unit)) {

        doAsync {

            queue = Volley.newRequestQueue(context)

            val jsonObject = JSONObject()

            jsonObject.put("TEAM_ID", teamID)
            jsonObject.put("TEAM_NAME", teamName)
            jsonObject.put("TEAM_INITIALS", teamInitials)
            jsonObject.put("TEAM_CITY", teamCity)
            jsonObject.put("TEAM_PRIMARY_COLOR", teamPrimaryColor)
            jsonObject.put("TEAM_SECONDARY_COLOR", teamSecondaryColor)
            jsonObject.put("TEAM_EMAIL", teamEmail)
            jsonObject.put("TEAM_PHONE", teamPhone)
            jsonObject.put("TOURNAMENT_ID", tournamentID)

            val jsonObjectRequest = object : JsonObjectRequest(

                Method.POST,
                BASE_API + NEW_TEAM,
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


    fun deleteTeamByID (context: Context, teamID : Int, teamEvent : ((Boolean) -> Unit)) {

        doAsync {

            queue = Volley.newRequestQueue(context)

            val stringRequest = object : StringRequest(

                Method.DELETE,
                BASE_API + DELETE_TEAM + "/" + teamID,
                Response.Listener {

                    teamEvent.invoke(true)
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

            queue!!.add(stringRequest)
        }
    }


    fun deleteTeamByTournamentID (context: Context, tournamentID : Int, teamEvent : ((Boolean) -> Unit)) {

        doAsync {

            queue = Volley.newRequestQueue(context)

            val stringRequest = object : StringRequest(

                Method.DELETE,
                BASE_API + DELETE_TEAM_TOURNAMENT + "/" + tournamentID,
                Response.Listener {

                    teamEvent.invoke(true)
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

            queue!!.add(stringRequest)
        }
    }


    //---------------Players--------------

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


    fun getPlayersByTeamID (context: Context, teamID : Int, teamsEvent : ((JSONArray?) -> Unit)) {

        doAsync {

            queue = Volley.newRequestQueue(context)

            val stringRequest = object : StringRequest(

                Method.GET,
                BASE_API + GET_PLAYERS_TEAM_ID + "/" + teamID,
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


    fun getPlayerByID (context: Context, playerID : Int, playersEvent : ((JSONArray?) -> Unit)) {

        doAsync {

            queue = Volley.newRequestQueue(context)

            val stringRequest = object : StringRequest(

                Method.GET,
                BASE_API + GET_PLAYER_PLAYER_ID + "/" + playerID,
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


    fun createNewPlayer (context: Context, playerID : Int, playerFirstName : String,
                       playerLastName : String, playerPosition : String, teamID : Int, playerHeight : Int, playerAge : Int,tournamentID: Int, playerEvent : ((Boolean) -> Unit)) {

        doAsync {

            queue = Volley.newRequestQueue(context)

            val jsonObject = JSONObject()

            jsonObject.put("PLAYER_ID", playerID)
            jsonObject.put("PLAYER_FIRST_NAME", playerFirstName)
            jsonObject.put("PLAYER_LAST_NAME", playerLastName)
            jsonObject.put("POSITION", playerPosition)
            jsonObject.put("TEAM_ID", teamID)
            jsonObject.put("PLAYER_HEIGHT", playerHeight)
            jsonObject.put("PLAYER_AGE", playerAge)
            jsonObject.put("TOURNAMENT_ID", tournamentID)

            val jsonObjectRequest = object : JsonObjectRequest(

                Method.POST,
                BASE_API + NEW_PLAYER,
                jsonObject,
                Response.Listener {

                    playerEvent.invoke(true)
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


    fun deletePlayerByID (context: Context, playerID : Int, gamesEvent : ((Boolean) -> Unit)) {

        doAsync {

            queue = Volley.newRequestQueue(context)

            val stringRequest = object : StringRequest(

                Method.DELETE,
                BASE_API + DELETE_PLAYER + "/" + playerID,
                Response.Listener {

                    gamesEvent.invoke(true)
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

            queue!!.add(stringRequest)
        }
    }


    fun deletePlayerByTournamentID (context: Context, tournamentID : Int, gamesEvent : ((Boolean) -> Unit)) {

        doAsync {

            queue = Volley.newRequestQueue(context)

            val stringRequest = object : StringRequest(

                Method.DELETE,
                BASE_API + DELETE_PLAYER_TOURNAMENT + "/" + tournamentID,
                Response.Listener {

                    gamesEvent.invoke(true)
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

            queue!!.add(stringRequest)
        }
    }

    //---------------Games--------------

    fun getGames (context: Context, gamesEvent : ((JSONArray?) -> Unit)) {

        doAsync {

            queue = Volley.newRequestQueue(context)

            val stringRequest = object : StringRequest(

                Method.GET,
                BASE_API + GET_GAMES,
                Response.Listener {

                    gamesEvent.invoke(JSONArray(it))
                },
                Response.ErrorListener {

                    Log.d("VolleyHelper", it.toString())
                    gamesEvent.invoke(null)
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


    fun getGameByID (context: Context, gameID : Int, gamesEvent : ((JSONArray?) -> Unit)) {

        doAsync {

            queue = Volley.newRequestQueue(context)

            val stringRequest = object : StringRequest(

                Method.GET,
                BASE_API + GET_GAME_GAME_ID + "/" + gameID,
                Response.Listener {

                    gamesEvent.invoke(JSONArray(it))
                },
                Response.ErrorListener {

                    Log.d("VolleyHelper", it.toString())
                    gamesEvent.invoke(null)
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


    fun getGamesByTournamentID (context: Context, tournamentID : Int, gamesEvent : ((JSONArray?) -> Unit)) {

        doAsync {

            queue = Volley.newRequestQueue(context)

            val stringRequest = object : StringRequest(

                Method.GET,
                BASE_API + GET_GAMES_TOURNAMENT_ID + "/" + tournamentID,
                Response.Listener {

                    gamesEvent.invoke(JSONArray(it))
                },
                Response.ErrorListener {

                    Log.d("VolleyHelper", it.toString())
                    gamesEvent.invoke(null)
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


    fun getGamesByTeamID (context: Context, teamID : Int, gamesEvent : ((JSONArray?) -> Unit)) {

        doAsync {

            queue = Volley.newRequestQueue(context)

            val stringRequest = object : StringRequest(

                Method.GET,
                BASE_API + GET_GAMES_TEAM_ID + "/" + teamID,
                Response.Listener {

                    gamesEvent.invoke(JSONArray(it))
                },
                Response.ErrorListener {

                    Log.d("VolleyHelper", it.toString())
                    gamesEvent.invoke(null)
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


    fun createNewGame (context: Context, gameID : Int, homeTeamID : Int, awayTeamID : Int,
                       tournamentID: Int, goalsHomeTeam : Int, goalsAwayTeam : Int, gameStage : String, gamesEvent : ((Boolean) -> Unit)) {

        doAsync {

            queue = Volley.newRequestQueue(context)

            val jsonObject = JSONObject()

            jsonObject.put("GAME_ID", gameID)
            jsonObject.put("HOME_TEAM_ID", homeTeamID)
            jsonObject.put("AWAY_TEAM_ID", awayTeamID)
            jsonObject.put("TOURNAMENT_ID", tournamentID)
            jsonObject.put("GOALS_HOME_TEAM", goalsHomeTeam)
            jsonObject.put("GOALS_AWAY_TEAM", goalsAwayTeam)
            jsonObject.put("STAGE", gameStage)

            val jsonObjectRequest = object : JsonObjectRequest(

                Method.POST,
                BASE_API + NEW_GAME,
                jsonObject,
                Response.Listener {

                    gamesEvent.invoke(true)
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


    fun deleteGameByID (context: Context, gameID : Int, gamesEvent : ((Boolean) -> Unit)) {

        doAsync {

            queue = Volley.newRequestQueue(context)

            val stringRequest = object : StringRequest(

                Method.DELETE,
                BASE_API + DELETE_GAME + "/" + gameID,
                Response.Listener {

                    gamesEvent.invoke(true)
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

            queue!!.add(stringRequest)
        }
    }


    fun deleteGameByTournamentID (context: Context, tournamentID : Int, gamesEvent : ((Boolean) -> Unit)) {

        doAsync {

            queue = Volley.newRequestQueue(context)

            val stringRequest = object : StringRequest(

                Method.DELETE,
                BASE_API + DELETE_GAME_TOURNAMENT + "/" + tournamentID,
                Response.Listener {

                    gamesEvent.invoke(true)
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

            queue!!.add(stringRequest)
        }
    }


    companion object {

        const val BASE_API = "http://192.168.1.66:3000"

        const val REGISTER = "/user/registry"
        const val LOGIN = "/user/login"
        const val GET_USER = "/user/username"
        const val RECOVER = "/user/recover"

        const val GET_TOURNAMENTS = "/api/tournaments"
        const val NEW_TOURNAMENT = "/api/new_tournament"
        const val DELETE_TOURNAMENT = "/api/delete_tournament"

        const val GET_TEAMS = "/api/teams"
        const val GET_TEAMS_TOURNAMENT_ID = "/api/teams/tournament"
        const val GET_TEAM_TEAM_ID = "/api/teams"
        const val NEW_TEAM = "/api/new_team"
        const val DELETE_TEAM = "/api/delete_team"
        const val DELETE_TEAM_TOURNAMENT = "/api/delete_team/tournament"

        const val GET_PLAYERS = "/api/players"
        const val GET_PLAYERS_TEAM_ID = "/api/players/teams"
        const val GET_PLAYER_PLAYER_ID = "/api/players"
        const val NEW_PLAYER = "/api/new_player"
        const val DELETE_PLAYER = "/api/delete_player"
        const val DELETE_PLAYER_TOURNAMENT = "/api/delete_player/tournament"

        const val GET_GAMES = "/api/games"
        const val GET_GAME_GAME_ID = "/api/games"
        const val GET_GAMES_TOURNAMENT_ID = "/api/games/tournament"
        const val GET_GAMES_TEAM_ID = "/api/games/team"
        const val NEW_GAME = "/api/new_game"
        const val DELETE_GAME = "/api/delete_game"
        const val DELETE_GAME_TOURNAMENT = "/api/delete_game/tournament"

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