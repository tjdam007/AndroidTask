package com.tjdam007.androidtask.utils

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.tjdam007.androidtask.R
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


const val SERVER_DATE_FORMAT = "EEE MMM dd HH:mm:ss z yyyy"
const val DATA="Data"
const val HEADER="Header"
//Extension Function

fun getDays(dateString: String): String {
    val postedDate = SimpleDateFormat(SERVER_DATE_FORMAT, Locale.US).apply {
        setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"))
    }.parse(dateString)
    val calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"))
    val currentDate = calendar.time
    return when (val x = TimeUnit.MILLISECONDS.toDays(currentDate.time - postedDate.time).toInt()) {
        0 -> "Today"
        1 -> "Yesterday"
        else -> x.toString().plus(" days ago")
    }
}


fun getChipBg(context: Context): ColorStateList {
    val states = arrayOf(
        intArrayOf(android.R.attr.state_checked),
        intArrayOf(-android.R.attr.state_checked),
        intArrayOf(android.R.attr.state_enabled)
    )
    val colors = intArrayOf(ContextCompat.getColor(context, R.color.chip_selected), Color.WHITE, Color.WHITE)
    return ColorStateList(states, colors)
}

fun getFilterChipColor(context: Context): ColorStateList {
    val states = arrayOf(
        intArrayOf(android.R.attr.state_checked),
        intArrayOf(-android.R.attr.state_checked),//uncheck
        intArrayOf(android.R.attr.state_enabled)
    )

    val colors = intArrayOf(
        ContextCompat.getColor(context, R.color.mediumPurple),
        ContextCompat.getColor(context, R.color.lightSlateGray),
        ContextCompat.getColor(context, R.color.lightSlateGray)
    )

    return ColorStateList(states, colors)
}

fun getChipStorkColor(context: Context): ColorStateList {
    val states = arrayOf(
        intArrayOf(android.R.attr.state_checked),
        intArrayOf(-android.R.attr.state_checked),//uncheck
        intArrayOf(android.R.attr.state_enabled)
    )

    val colors = intArrayOf(
        ContextCompat.getColor(context, R.color.chip_selected),
        ContextCompat.getColor(context, R.color.lightSlateGray),
        ContextCompat.getColor(context, R.color.lightSlateGray)
    )

    return ColorStateList(states, colors)
}

fun getChipTextColor(context: Context): ColorStateList {
    val states = arrayOf(
        intArrayOf(android.R.attr.state_checked),
        intArrayOf(-android.R.attr.state_checked),
        intArrayOf(android.R.attr.state_enabled)
    )

    val colors = intArrayOf(
        ContextCompat.getColor(context, R.color.chip_text_selected),
        ContextCompat.getColor(context, R.color.textColor),
        ContextCompat.getColor(context, R.color.textColor)
    )

    return ColorStateList(states, colors)
}

fun Activity.showToast(res:Int){
    Toast.makeText(this,res,Toast.LENGTH_SHORT)
        .show()
}

