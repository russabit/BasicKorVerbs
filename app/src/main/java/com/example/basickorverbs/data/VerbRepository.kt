package com.example.basickorverbs.data

import com.example.basickorverbs.domain.Verb
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class VerbRepository {

    val retrofit = Retrofit.Builder()
        .baseUrl("https://raw.githubusercontent.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val verbApiService = retrofit.create(VerbApiService::class.java)

    fun convertJsonToVerbList(jsonString: String): List<Verb> {

        val listType = object : TypeToken<List<Verb>>() {}.type

        return Gson().fromJson(jsonString, listType)
    }

}