package com.example.android_note_mvc.activty

import android.os.Bundle
import android.widget.TextView
import com.example.android_note_mvc.R
import com.example.android_note_mvc.model.Note
import com.example.android_retrofit_kotlin.network.retrofit.RetrofitHttp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : BaseActivity() {
    lateinit var tv_title: TextView
    lateinit var tv_body: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initViews()
    }

    private fun initViews() {
        tv_title = findViewById(R.id.tv_title)
        tv_body = findViewById(R.id.tv_body)
        var noteID = intent.getLongExtra("id",2)
        apiSingleNote(noteID)
    }

    fun apiSingleNote(id: Long) {
        showLoading(this)
        RetrofitHttp.noteService.singleNote(id).enqueue(object : Callback<Note> {
            override fun onResponse(call: Call<Note>, response: Response<Note>) {
                dismissLoading()
                val note = response.body()
                tv_title.text = note!!.title
                tv_body.text = note!!.body
            }

            override fun onFailure(call: Call<Note>, t: Throwable) {
                dismissLoading()
            }

        })
    }
}