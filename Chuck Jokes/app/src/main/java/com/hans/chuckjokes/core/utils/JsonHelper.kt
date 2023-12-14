package com.hans.chuckjokes.core.utils

import android.content.Context
import com.hans.chuckjokes.R
import com.hans.chuckjokes.core.data.source.remote.response.JokesResponse
import org.json.JSONObject
import java.io.IOException

class JsonHelper(private val context: Context) {

    private fun parsingFileToString(): String? {
        val jsonString: String
        try {
            jsonString = context.resources.openRawResource(R.raw.jokes).bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return null
    }

    fun loadData(): List<JokesResponse> {
        val list = ArrayList<JokesResponse>()
        val responseObject = JSONObject(parsingFileToString().toString())
        val listArray = responseObject.getJSONArray("places")
        for (i in 0 until listArray.length()) {
            val course = listArray.getJSONObject(i)

            val icon_url = course.getString("icon_url")
            val id = course.getString("id")
            val url = course.getString("url")
            val value = course.getString("value")

            val courseResponse = JokesResponse(
                icon_url = icon_url,
                id = id,
                url = url,
                value = value
            )
            list.add(courseResponse)
        }
        return list
    }
}