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

    companion object {

        const val BASE_API = "http://192.168.1.66:3000"
        const val REGISTER = "/user/registry"
        const val LOGIN = "/user/login"

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