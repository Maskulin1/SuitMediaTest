package com.reihan.suitmediatask.api

import com.reihan.suitmediatask.model.UserData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {
    @GET("api/users")
    fun mSearch(
        @QueryMap parameters: HashMap<String, String>
    ): Call<UserData>
}