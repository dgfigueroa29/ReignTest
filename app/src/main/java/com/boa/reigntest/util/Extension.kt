package com.boa.reigntest.util

import android.content.Context
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.webkit.WebSettings
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.boa.reigntest.R
import java.lang.ref.WeakReference
import java.util.*

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun Fragment?.receiveSafeString(key: String): String = if (this?.requireArguments() != null) {
    try {
        if (this.requireArguments().containsKey(key)) {
            this.requireArguments().getString(key, "")
        } else {
            ""
        }
    } catch (e: Exception) {
        ""
    }
} else {
    ""
}

fun RecyclerView?.build(context: WeakReference<Context>) {
    val layoutManager = LinearLayoutManager(context.get())
    layoutManager.orientation = LinearLayoutManager.VERTICAL
    this?.hasFixedSize()
    this?.layoutManager = layoutManager
    this?.addItemDecoration(
        DividerItemDecoration(
            context.get(),
            layoutManager.orientation
        )
    )
    this?.itemAnimator = object : DefaultItemAnimator() {
        override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
            dispatchAddFinished(holder)
            dispatchAddStarting(holder)
            return false
        }
    }
}

@Suppress("DEPRECATION")
fun Context.toast(message: String) {
    Handler().post {
        val toast: Toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }
}

@Suppress("SetJavaScriptEnabled", "DEPRECATION")
fun WebSettings?.build() {
    this?.javaScriptEnabled = true
    this?.mediaPlaybackRequiresUserGesture = false
    this?.setRenderPriority(WebSettings.RenderPriority.HIGH)
    this?.loadWithOverviewMode = true
    this?.useWideViewPort = true
    this?.setSupportZoom(false)
    this?.builtInZoomControls = false
    this?.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
    this?.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
}

fun Long.toStringTime(context: Context): String {
    val time = Date()

    if (this < 1000000000000L) {
        time.time = this * 1000L
    } else {
        time.time = this
    }

    val now = Calendar.getInstance(Locale.getDefault())
    val itemTime = Calendar.getInstance(Locale.getDefault())
    itemTime.timeInMillis = time.time

    val minutes = now.get(Calendar.MINUTE) - itemTime.get(Calendar.MINUTE)
    val hoursOld = now.get(Calendar.HOUR) - itemTime.get(Calendar.HOUR)
    val daysOld = now.get(Calendar.DATE) - itemTime.get(Calendar.DATE)

    return when {
        minutes == 0 -> context.getString(R.string.seconds_ago)
        hoursOld == 0 -> context.getString(R.string.minutes_ago, minutes)
        daysOld == 0 -> context.getString(R.string.hours_ago, hoursOld)
        daysOld == 1 -> context.getString(R.string.day_ago)
        else -> context.getString(R.string.days_ago, daysOld)
    }
}