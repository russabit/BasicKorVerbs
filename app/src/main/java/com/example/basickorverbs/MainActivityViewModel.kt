package com.example.basickorverbs

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.basickorverbs.data.VerbRepository
import com.example.basickorverbs.domain.Verb
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MainActivityViewModel: ViewModel() {

    private val _dataList = MutableLiveData<List<Verb>>()
    val dataList: LiveData<List<Verb>> = _dataList

    fun getVerbListFromString(context: Context): List<Verb>? {

        val string = readJsonFileFromAssets(context, "jsonVerbList.json")

        return string?.let { VerbRepository().convertJsonToVerbList(it) }.also { _dataList.value = it }

    }

    fun callServer() = VerbRepository().verbApiService.getVerbs().enqueue(object : Callback<List<Verb>> {

        override fun onResponse(call: Call<List<Verb>>, response: Response<List<Verb>>) {
            if (response.isSuccessful) {
                _dataList.value = response.body() ?: emptyList()
            } else {
                // Обработка ошибки
                Log.e("API Error", response.message() ?: "Unknown error")
            }
        }

        override fun onFailure(call: Call<List<Verb>>, t: Throwable) {
            // Обработка ошибки
            Log.e("API Error", t.message ?: "Unknown error")
        }
    })

    fun readJsonFileFromAssets(context: Context, fileName: String): String? {
        return try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

}