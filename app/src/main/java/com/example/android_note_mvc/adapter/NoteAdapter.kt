package com.example.android_note_mvc.adapter

import android.app.ActivityOptions
import android.app.Dialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.android_note_mvc.R
import com.example.android_note_mvc.activty.DetailActivity
import com.example.android_note_mvc.activty.MainActivity
import com.example.android_note_mvc.model.Note

class NoteAdapter(var activity: MainActivity, var notes: ArrayList<Note>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return PosterViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val note = notes[position]
        if (holder is PosterViewHolder) {
            val ll_note = holder.ll_note
            val tv_title = holder.tv_title
            val tv_body = holder.tv_body
            val tv_date = holder.tv_date
            val iv_more = holder.iv_more
            tv_title.setText(note.title!!)
            tv_body.setText(note.body)
            tv_date.setText(note.date)


            ll_note.setOnClickListener {
                callDetailActivity(note.id, ll_note)
            }

            ll_note.setOnLongClickListener {
                activity.showDeleteDialog(note)
                false
            }

            iv_more.setOnClickListener {
                var popupMenu = PopupMenu(activity, it)
                popupMenu.inflate(R.menu.menu)
                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.editText -> {
                            activity.showEditNoteDialog(note)
                            true
                        }
                        R.id.delete -> {
                            activity.showDeleteDialog(note)
                            true
                        }
                        else -> true
                    }
                }
                popupMenu.show()
                val popup = PopupMenu::class.java.getDeclaredField("mPopup")
                popup.isAccessible = true
                val menu = popup.get(popupMenu)
                menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(menu, true)
            }
        }
    }

    inner class PosterViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var ll_note: LinearLayout
        var tv_title: TextView
        var tv_body: TextView
        var tv_date: TextView
        var iv_more: ImageView

        init {
            ll_note = view.findViewById(R.id.ll_note)
            tv_title = view.findViewById(R.id.tv_title)
            tv_body = view.findViewById(R.id.tv_body)
            tv_date = view.findViewById(R.id.tv_date)
            iv_more = view.findViewById(R.id.iv_more)
        }
    }


    private fun callDetailActivity(id: Long?, ll_note: LinearLayout) {
        var intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra("id", id)
        val options = ActivityOptions.makeSceneTransitionAnimation(activity, ll_note, "robot")
        activity.startActivity(
            intent
        )
    }


}