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
import com.example.vynilsapp.models.Collector
import com.example.vynilsapp.models.Performer
import com.example.vynilsapp.models.CreateAlbumRequest
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import com.example.vynilsapp.models.Track

class NetworkServiceAdapter (context: Context) {
    companion object {
        const val BASE_URL = "http://3.136.119.70:3000/"
        private var instance: NetworkServiceAdapter? = null
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

    suspend fun getAlbum(id: Int) = suspendCoroutine { continuation ->
        requestQueue.add(
            getRequest(
                "albums/$id",
                { response ->
                    val item = JSONObject(response)
                    val album = Album(
                        albumId = id,
                        name = item.getString("name"),
                        cover = item.getString("cover"),
                        releaseDate = item.getString("releaseDate"),
                        description = item.getString("description"),
                        genre = item.getString("genre"),
                        recordLabel = item.getString("recordLabel")
                    )
                    continuation.resume(album)
                },
                {
                    continuation.resumeWithException(it)
                })
        )
    }

    suspend fun getAlbums() = suspendCoroutine<List<Album>> { continuation ->
        requestQueue.add(
            getRequest(
                "albums",
                { response ->
                    val resp = JSONArray(response)
                    val list = mutableListOf<Album>()
                    for (i in 0 until resp.length()) {
                        val item = resp.getJSONObject(i)
                        val album = Album(
                            albumId = item.getInt("id"),
                            name = item.getString("name"),
                            cover = item.getString("cover"),
                            releaseDate = item.getString("releaseDate"),
                            description = item.getString("description"),
                            genre = item.getString("genre"),
                            recordLabel = item.getString("recordLabel")
                        )
                        list.add(i, album)
                    }
                    continuation.resume(list)
                },
                {
                    continuation.resumeWithException(it)
                })
        )
    }

    suspend fun getPerformer(id: Int, typePerformer: String) = suspendCoroutine { continuation ->
            val endpoint = if (typePerformer == "Band") {
                "bands/$id"
            } else {
                "musicians/$id"
            }
            requestQueue.add(getRequest(endpoint,
                { response ->
                    val item = JSONObject(response)
                    val performer = Performer(
                        performerId = id,
                        name = item.getString("name"),
                        description = item.getString("description"),
                        creationDate = if (typePerformer == "Band") item.optString("creationDate", null) else null,
                        birthDate = if (typePerformer != "Band") item.optString("birthDate", null) else null,
                        image = item.getString("image")
                    )
                    continuation.resume(performer)
                },
                {
                    continuation.resumeWithException(it)
                }))
        }

    suspend fun getPerformers() = suspendCoroutine<List<Performer>> { continuation ->
        val list = mutableListOf<Performer>()
        var pendingRequests = 2
        var hasResumed = false

        fun handleCompletion() {
            if (pendingRequests == 0 && !hasResumed) {
                hasResumed = true
                continuation.resume(list)
            }
        }

        fun handleError(error: Throwable) {
            if (!hasResumed) {
                hasResumed = true
                continuation.resumeWithException(error)
            }
        }

        fun makeRequestAndProcess(endpoint: String, isBand: Boolean) {
            requestQueue.add(getRequest(
                endpoint,
                { response ->
                    processResponse(response, isBand, list, ::handleError)
                    pendingRequests--
                    handleCompletion()
                },
                {
                    pendingRequests--
                    handleError(it)
                }
            ))
        }

        makeRequestAndProcess("musicians", isBand = false)
        makeRequestAndProcess("bands", isBand = true)
    }


    private fun processResponse(response: String, isBand: Boolean, list: MutableList<Performer>, onError: (Throwable) -> Unit) {
        try {
            val resp = JSONArray(response)
            for (i in 0 until resp.length()) {
                val item = resp.getJSONObject(i)
                list.add(parsePerformer(item, isBand))
            }
        } catch (e: Exception) {
            onError(e)
        }
    }

    private fun parsePerformer(item: JSONObject, isBand: Boolean): Performer {
        return Performer(
            performerId = item.getInt("id"),
            name = item.getString("name"),
            description = item.getString("description"),
            creationDate = if (isBand) item.optString("creationDate", null) else null,
            birthDate = if (!isBand) item.optString("birthDate", null) else null,
            image = item.getString("image")
        )
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

    fun addTrackToAlbum(
        albumId: Int,
        trackName: String,
        trackDuration: String,
        onComplete: (Album) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        val requestBody = JSONObject().apply {
            put("name", trackName)
            put("duration", trackDuration)
        }

        requestQueue.add(
            postRequest(
                "albums/$albumId/tracks",
                requestBody,
                { response ->
                    try {
                        val updatedAlbum = gson.fromJson(response.toString(), Album::class.java)
                        onComplete(updatedAlbum)
                    } catch (e: Exception) {
                        onError(VolleyError("Failed to parse response: ${e.message}"))
                    }
                },
                { error -> onError(error) }
            )
        )
    }

    private fun getRequest(path: String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL + path, responseListener, errorListener)
    }

    private fun postRequest(path: String, jsonBody: JSONObject, responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener): JsonObjectRequest {
        return JsonObjectRequest(Request.Method.POST, BASE_URL + path, jsonBody, responseListener, errorListener)
    }

    suspend fun getCollectors() = suspendCoroutine<List<Collector>> { continuation ->
        requestQueue.add(
            getRequest(
                "collectors",
                { response ->
                    val resp = JSONArray(response)
                    val list = mutableListOf<Collector>()
                    for (i in 0 until resp.length()) {
                        val item = resp.getJSONObject(i)
                        val collector = Collector(
                            collectorId = item.getInt("id"),
                            name = item.getString("name"),
                            telephone = item.getString("telephone"),
                            email = item.getString("email")
                        )
                        list.add(i, collector)
                    }
                    continuation.resume(list)
                },
                {
                    continuation.resumeWithException(it)
                })
        )
    }

    suspend fun getCollector(id: Int) = suspendCoroutine { continuation ->
        requestQueue.add(
            getRequest(
                "collectors/$id",
                { response ->
                    val item = JSONObject(response)
                    val collector = Collector(
                        collectorId = id,
                        name = item.getString("name"),
                        telephone = item.getString("telephone"),
                        email = item.getString("email")
                    )
                    continuation.resume(collector)
                },
                {
                    continuation.resumeWithException(it)
                })
        )
    }
}