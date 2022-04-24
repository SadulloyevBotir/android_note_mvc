package com.example.android_note_mvc.utils

import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.example.android_note_mvc.R
import com.example.android_note_mvc.model.Note
import com.google.android.material.textfield.TextInputEditText

object Utils {


    fun dialogDouble(
        context: Context,
        title: String?,
        note: Note? = null,
        bnName: String,
        isEdit: Boolean = false,
        callback: DialogListener
    ) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.view_dialog_double)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(true)
        val tv_title = dialog.findViewById<TextView>(R.id.tv_title)
        val et_title = dialog.findViewById<TextInputEditText>(R.id.et_title)
        val et_body = dialog.findViewById<TextInputEditText>(R.id.et_body)
        val bn_single = dialog.findViewById<Button>(R.id.bn_single)
        tv_title.text = title
        et_title.hint = context.getString(R.string.str_hint_title)
        et_body.hint = context.getString(R.string.str_hint_body)
        bn_single.text = bnName

        if (isEdit) {
            et_body.text!!.insert(0, note!!.body)
            et_title.text!!.insert(0, note!!.title)
            bn_single.setOnClickListener {
                if (et_body.text!!.isNotEmpty() && et_title.text!!.isNotEmpty()) {
                    note!!.title = et_title.text!!.trim().toString()
                    note!!.body = et_body.text!!.trim().toString()
                    dialog.dismiss()
                    callback.onCallback(true)
                    callback.onCallbackNote(note)
                } else {
                    et_title.setError(context.getString(R.string.str_hint_title))
                    et_body.setError(context.getString(R.string.str_hint_body))
                }
            }
        } else {
            bn_single.setOnClickListener {
                if (et_body.text!!.isNotEmpty() && et_title.text!!.isNotEmpty()) {
                    var title = et_title.text!!.trim().toString()
                    var body = et_body.text!!.trim().toString()
                    dialog.dismiss()
                    var note = Note(title, body)
                    callback.onCallback(true)
                    callback.onCallbackNote(note)
                } else {
                    et_title.setError(context.getString(R.string.str_hint_title))
                    et_body.setError(context.getString(R.string.str_hint_body))
                }
            }

        }
        dialog.show()
    }


    fun dialogSingle(context: Context, title: String, callback: DialogListener) {
        val dialog = Dialog(context, R.style.dialog_style)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.view_dialog_single)
        dialog.setCanceledOnTouchOutside(true)
        var tv_title = dialog.findViewById<TextView>(R.id.tv_title)
        var bn_done = dialog.findViewById<Button>(R.id.bn_done)
        var bn_cancel = dialog.findViewById<Button>(R.id.bn_cancel)
        tv_title.text = title
        bn_done.text = context.getText(R.string.str_confirm)
        bn_cancel.text = context.getString(R.string.str_cancel)


        bn_done.setOnClickListener {
            callback.onCallback(true)
            dialog.dismiss()
        }
        bn_cancel.setOnClickListener {
            callback.onCallback(false)
            dialog.dismiss()
        }

        dialog.show()

    }

    fun checkNetwork(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        var result = false
        if (activeNetwork != null) {
            result = activeNetwork.isConnectedOrConnecting
        }
        return result

    }


    fun dialogNetwork(context: Context, callback: DialogListener) {
        val dialog = Dialog(context, R.style.dialog_style)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.custom_network_connection)
        dialog.setCanceledOnTouchOutside(true)
        var bn_refresh = dialog.findViewById<Button>(R.id.bn_refresh)


        bn_refresh.setOnClickListener {
            if (checkNetwork(context)) {
                callback.onCallback(true)
                dialog.dismiss()
            }

        }

        dialog.show()

    }

    interface DialogListener {
        fun onCallback(isChosen: Boolean)
        fun onCallbackNote(note: Note)
    }
}