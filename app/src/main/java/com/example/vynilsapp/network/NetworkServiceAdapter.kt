package com.example.vynilsapp.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.vynilsapp.models.Album
import com.example.vynilsapp.models.CreateAlbumRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class NetworkServiceAdapter constructor(context: Context) {
    companion object {
        const val BASE_URL = "https://backvynils-q6yc.onrender.com/"
        var instance: NetworkServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter(context).also {
                    instance = it
                }
            }
    }

    private val requestQueue: RequestQueue by lazy {
        // applicationContext keeps you from leaking the Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }

    private val gson = Gson()

    fun getAlbums(onComplete: (resp: List<Album>) -> Unit, onError: (error: VolleyError) -> Unit) {
        requestQueue.add(getRequest("albums",
            { response ->
                try {
                    val type = object : TypeToken<List<Album>>() {}.type
                    val albums = gson.fromJson<List<Album>>(response, type)
                    onComplete(albums)
                } catch (e: Exception) {
                    onError(VolleyError(e.message))
                }
            },
            { onError(it) }))
    }

    fun createAlbum(albumRequest: CreateAlbumRequest, onComplete: (Album) -> Unit, onError: (VolleyError) -> Unit) {
        // Convertir el objeto a JSON
        val jsonBody = gson.toJson(albumRequest)
        val jsonObject = JSONObject(jsonBody)

        requestQueue.add(postRequest("albums", jsonObject,
            { response ->
                try {
                    // Convertir respuesta a objeto Album
                    val createdAlbum = gson.fromJson(response.toString(), Album::class.java)
                    onComplete(createdAlbum)
                } catch (e: Exception) {
                    onError(VolleyError(e.message))
                }
            },
            { onError(it) }))
    }

    private fun getRequest(path: String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL + path, responseListener, errorListener)
    }

    private fun postRequest(path: String, jsonBody: JSONObject, responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener): JsonObjectRequest {
        return JsonObjectRequest(Request.Method.POST, BASE_URL + path, jsonBody, responseListener, errorListener)
    }
}