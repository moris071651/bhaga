package com.tumba.bhaga.utils

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.net.toUri

fun openLink(context: Context, link: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, link.toUri()).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "Cannot open link", Toast.LENGTH_SHORT).show()
    }
}
