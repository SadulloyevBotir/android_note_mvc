package com.example.android_retrofit_kotlin.network.retrofit

import com.example.android_retrofit_kotlin.network.retrofit.services.NoteService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHttp {
    public var IS_TESTER = true
    private val SERVER_DEVELOPMENT = "https://6261ac25327d3896e27ea69e.mockapi.io"
    private val SERVER_PRODUCTION = "https://6261ac25327d3896e27ea69e.mockapi.io"

    val retrofit =
        Retrofit.Builder().baseUrl(server()).addConverterFactory(GsonConverterFactory.create())
            .build()

    fun server(): String {
        if (IS_TESTER) return SERVER_DEVELOPMENT
        return SERVER_PRODUCTION
    }

    val noteService: NoteService = retrofit.create(NoteService::class.java)
}