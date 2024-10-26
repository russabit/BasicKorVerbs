package com.example.basickorverbs.data

import com.example.basickorverbs.domain.Verb
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class VerbRepository {

    fun convertJsonToVerbList(jsonString: String): List<Verb> {

        val listType = object : TypeToken<List<Verb>>() {}.type

        return Gson().fromJson(jsonString, listType)
    }

}