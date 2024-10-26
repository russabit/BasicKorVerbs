package com.example.basickorverbs.data

import com.example.basickorverbs.domain.Verb
import retrofit2.Call
import retrofit2.http.GET

interface VerbApiService {
    @GET("/russabit/BasicKorVerbs/refs/heads/main/app/dataString")
    fun getVerbs(): Call<List<Verb>>
}