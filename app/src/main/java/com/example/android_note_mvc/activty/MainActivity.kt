package com.example.android_note_mvc.activty

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_note_mvc.R
import com.example.android_note_mvc.adapter.NoteAdapter
import com.example.android_note_mvc.model.Note
import com.example.android_note_mvc.utils.Utils
import com.example.android_retrofit_kotlin.network.retrofit.RetrofitHttp
import com.getbase.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity() {
    lateinit var rv_notes: RecyclerView
    lateinit var bn_add: FloatingActionButton
    private lateinit var adapter: NoteAdapter
    private var posters: ArrayList<Note> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = NoteAdapter(this, posters)
        initViews()
    }

    private fun initViews() {
        rv_notes = findViewById(R.id.rv_notes)
        bn_add = findViewById(R.id.bn_add)

        rv_notes.setLayoutManager(GridLayoutManager(this, 1))
        rv_notes.setAdapter(adapter)


        bn_add.setOnClickListener {
            showCreateNoteDialog()
        }
        apiNoteList()
    }


    open fun showDeleteDialog(note: Note) {
        Utils.dialogSingle(
            this,
            getString(R.string.str_delete_note),
            object : Utils.DialogListener {
                override fun onCallback(isChosen: Boolean) {
                    if (isChosen) {
                        apiNoteDelete(note.id!!)
                    }
                }

                override fun onCallbackNote(note: Note) {
                }

            })
    }

    fun showNetworkDialog() {
        Utils.dialogNetwork(this, object : Utils.DialogListener {
            override fun onCallback(isChosen: Boolean) {
                if (isChosen) {
                    if (Utils.checkNetwork(this@MainActivity)) {
                        apiNoteList()
                    }
                }
            }

            override fun onCallbackNote(note: Note) {
            }
        })
    }

    fun showEditNoteDialog(note: Note) {
        Utils.dialogDouble(
            context = this,
            title = getString(R.string.str_edit),
            note = note,
            isEdit = true,
            bnName = getString(R.string.str_update),
            callback = object : Utils.DialogListener {
                override fun onCallback(isChosen: Boolean) {
                    if (isChosen) {
                        apiNoteUpdate(note)
                    }
                }

                override fun onCallbackNote(note: Note) {

                }
            })
    }

    fun showCreateNoteDialog() {

        Utils.dialogDouble(
            context = this,
            title = getString(R.string.str_create_note),
            bnName = getString(R.string.str_create_note),
            callback = object : Utils.DialogListener {
                override fun onCallback(isChosen: Boolean) {

                }

                override fun onCallbackNote(note: Note) {
                    apiNoteCreate(note)
                }

            }
        )
    }

    fun apiNoteCreate(note: Note) {
        showLoading(this)
        RetrofitHttp.noteService.createNote(note).enqueue(object : Callback<Note> {
            override fun onResponse(call: Call<Note>, response: Response<Note>) {
                dismissLoading()
                apiNoteList()
            }

            override fun onFailure(call: Call<Note>, t: Throwable) {
            }

        })
    }

    open fun apiNoteList() {
        showLoading(this)
        RetrofitHttp.noteService.listNote().enqueue(object : Callback<ArrayList<Note>> {
            override fun onResponse(
                call: Call<ArrayList<Note>>,
                response: Response<ArrayList<Note>>
            ) {
                if (response.body()!!.isNotEmpty()) {
                    posters.clear()
                    posters.addAll(response.body()!!)
                    adapter.notifyDataSetChanged()
                    dismissLoading()
                } else {
                    showCreateNoteDialog()
                }
            }

            override fun onFailure(call: Call<ArrayList<Note>>, t: Throwable) {
                dismissLoading()
                showNetworkDialog()
            }

        })
    }

    open fun apiNoteUpdate(note: Note) {
        showLoading(this)
        var id: Long = note.id!!

        RetrofitHttp.noteService.updateNote(id, note).enqueue(object : Callback<Note> {
            override fun onResponse(call: Call<Note>, response: Response<Note>) {
                dismissLoading()
                apiNoteList()
            }

            override fun onFailure(call: Call<Note>, t: Throwable) {
                dismissLoading()
            }

        })
    }


    open fun apiNoteDelete(id: Long) {
        showLoading(this)
        RetrofitHttp.noteService.deleteNote(id).enqueue(object : Callback<Note> {
            override fun onResponse(call: Call<Note>, response: Response<Note>) {
                dismissLoading()
                apiNoteList()
            }

            override fun onFailure(call: Call<Note>, t: Throwable) {
                dismissLoading()
            }

        })
    }

}
