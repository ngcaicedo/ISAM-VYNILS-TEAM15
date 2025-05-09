package com.example.vynilsapp.network

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.vynilsapp.models.Album
import com.example.vynilsapp.models.Performer
import com.example.vynilsapp.models.CreateAlbumRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class NetworkServiceAdapter constructor(context: Context) {
    companion object {
        const val BASE_URL = "http://3.136.119.70:3000/"
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

    fun getAlbum(id: String, onComplete: (Album) -> Unit, onError: (VolleyError) -> Unit) {
        requestQueue.add(getRequest("albums/$id",
            { response ->
                try {
                    val album = gson.fromJson(response, Album::class.java)
                    onComplete(album)
                } catch (e: Exception) {
                    onError(VolleyError(e.message))
                }
            },
            { onError(it) }))
    }

    fun getAlbums(onComplete: (List<Album>) -> Unit, onError: (VolleyError) -> Unit) {
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

    fun getPerformer(id: String, typePerformer: String, onComplete: (Performer) -> Unit, onError: (VolleyError) -> Unit) {
        val endpoint = when (typePerformer) {
            "Band" -> "bands/$id"
            "Musician" -> "musicians/$id"
            else -> {
                onError(VolleyError("Invalid type: $typePerformer"))
                Log.i("PerformerFragment", "Error PerformerType: ${typePerformer}")
                return
            }
        }
        Log.i("PerformerFragment", "NetworkServiceAdapter - typePerformer: ${typePerformer} | performerId: ${id}")


        requestQueue.add(getRequest(endpoint,
            { response ->
                try {
                    val performer = gson.fromJson(response, Performer::class.java)
                    onComplete(performer)
                    Log.i("PerformerFragment", "NetworkServiceAdapter - Performer: ${performer}")
                } catch (e: Exception) {
                    onError(VolleyError(e.message))
                }
            },
            { onError(it) }))
    }

    fun getPerformers(onComplete: (resp: List<Performer>) -> Unit, onError: (error: VolleyError) -> Unit) {
        val allPerformers = mutableListOf<Performer>()
        var errorCount = 0

        fun handleRequestCompletion() {
            if (errorCount < 2) {
                onComplete(allPerformers)
            } else {
                onError(VolleyError("Failed to load musicians and bands"))
            }
        }

        fun makePerformerRequest(endpoint: String) {
            requestQueue.add(getRequest(endpoint,
                { response ->
                    try {
                        val performers = gson.fromJson<List<Performer>>(
                            response,
                            object : TypeToken<List<Performer>>() {}.type
                        )
                        allPerformers.addAll(performers)
                    } catch (e: Exception) {
                        errorCount++
                    }
                    handleRequestCompletion()
                },
                {_ ->
                    errorCount++
                    handleRequestCompletion()
                }
            ))
        }

        makePerformerRequest("musicians")
        makePerformerRequest("bands")
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