package com.example.android_retrofit_kotlin.network.retrofit.services

import com.example.android_note_mvc.model.Note
import retrofit2.Call
import retrofit2.http.*
import java.util.ArrayList

interface NoteService {

    @GET("/notes")
    fun listNote():Call<ArrayList<Note>>

    @GET("/notes/{id}")
    fun singleNote(@Path("id") id: Long): Call<Note>

    @POST("/notes")
    fun createNote(@Body post: Note): Call<Note>

    @PUT("/notes/{id}")
    fun updateNote(@Path("id") id: Long, @Body post: Note): Call<Note>

    @DELETE("/notes/{id}")
    fun deleteNote(@Path("id") id: Long): Call<Note>
    @DELETE("/notes")
    fun deleteAllNotes():Call<ArrayList<Note>>
}