package com.dicoding.courseschedule.util

import android.app.Activity
import androidx.appcompat.app.AlertDialog

fun Activity.showOkBackDialog(title: String, message: String, isFinishActivity: Boolean) {
    AlertDialog.Builder(this).apply {
        setTitle(title)
        setMessage(message)
        setPositiveButton("OK") { p0, _ ->
            p0.dismiss()
            if (isFinishActivity) this@showOkBackDialog.finish()
        }
    }.create().show()
}