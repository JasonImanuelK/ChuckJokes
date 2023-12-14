package com.hans.chuckjokes.core.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hans.chuckjokes.core.data.source.remote.network.ApiResponse
import com.hans.chuckjokes.core.data.source.remote.network.ApiService
import com.hans.chuckjokes.core.data.source.remote.response.JokesResponse
import com.hans.chuckjokes.core.data.source.remote.response.ListJokesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource private constructor(private val apiService: ApiService) {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(service: ApiService): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(service)
            }

    }

    fun getRandomJokes(): LiveData<ApiResponse<List<JokesResponse>>> {
        val resultData = MutableLiveData<ApiResponse<List<JokesResponse>>>()

        //get data from remote api
        val client = apiService.getRandomJokes()

        client.enqueue(object : Callback<JokesResponse> {
            override fun onResponse(
                call: Call<JokesResponse>,
                response: Response<JokesResponse>
            ) {
                val jokesResponse = response.body()

                resultData.value = if (jokesResponse != null) {
                    ApiResponse.Success(listOf(jokesResponse))
                } else {
                    ApiResponse.Empty
                }
            }

            override fun onFailure(call: Call<JokesResponse>, t: Throwable) {
                resultData.value = ApiResponse.Error(t.message.toString())
                Log.e("RemoteDataSource", t.message.toString())
            }
        })

        return resultData
    }

    fun getSearchJokes(query: String): LiveData<ApiResponse<List<JokesResponse>>> {
        val resultData = MutableLiveData<ApiResponse<List<JokesResponse>>>()

        val call = apiService.getSearchJokes(query)
        Log.e("Hasil ", query)
        call.enqueue(object : Callback<ListJokesResponse> {
            override fun onResponse(
                call: Call<ListJokesResponse>,
                response: Response<ListJokesResponse>
            ) {
                if (response.isSuccessful) {
                    val jokesResponse = response.body()?.result
                    Log.e("HASIL JOKES ", jokesResponse.toString())
//                    resultData.value = if (jokesResponse != null) ApiResponse.Success(jokesResponse) else ApiResponse.Empty
                    resultData.value = if (jokesResponse != null && jokesResponse.isNotEmpty()) {
                        ApiResponse.Success(jokesResponse)
                    } else {
                        ApiResponse.Empty
                    }
                    Log.v("HASIL RESULT ", resultData.value.toString())
                } else {
                    resultData.value = ApiResponse.Error(response.message())
                    Log.d("RemoteDataSource", "Status Code: ${response.code()}")
                    Log.e("RemoteDataSource", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ListJokesResponse>, t: Throwable) {
                resultData.value = ApiResponse.Error(t.message.toString())
                Log.e("ON FAILURE", t.message.toString())
            }
        })
        Log.v("HILANG GAK ", resultData.value.toString())
        return resultData
    }
}