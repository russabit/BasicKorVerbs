package com.example.basickorverbs

import com.example.basickorverbs.domain.Verb
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

object TestDataLoader {

    fun loadVerbs(): List<Verb> {

        val json = File(
            "src/main/assets/jsonVerbList.json"
        ).readText()

        return Gson().fromJson(
            json,
            object : TypeToken<List<Verb>>() {}.type
        )
    }
}