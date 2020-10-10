package com.boa.reigntest.ui.detail

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.KeyEvent
import android.view.View.OnKeyListener
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.boa.reigntest.R
import com.boa.reigntest.base.BaseActivity
import com.boa.reigntest.util.ARGUMENT_DETAIL
import com.boa.reigntest.util.build
import com.boa.reigntest.util.toast
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.io.File

class DetailActivity : BaseActivity<DetailViewStatus, DetailViewModel>() {
    private var previewUrl = ""

    override fun initViewModel(): DetailViewModel = getViewModel()

    override fun getLayoutResource(): Int = R.layout.activity_detail

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showLoading()
        detailWebView.settings.build()
        detailWebView.webViewClient = MyWebClient()
        previewUrl = intent.getStringExtra(ARGUMENT_DETAIL) ?: ""
        detailWebView.setOnKeyListener(OnKeyListener { _, keyCode, event ->
            try {
                if (event.action != KeyEvent.ACTION_DOWN) {
                    return@OnKeyListener true
                }

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    onBackPressed()
                    return@OnKeyListener true
                }
            } catch (e: Exception) {
            }
            false
        })

        viewModel.url = previewUrl
        viewModel.initialize()
        val permissionsList: MutableList<String> = ArrayList()

        for (permission in arrayOf(Manifest.permission.INTERNET)) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                    permissionsList.add(permission)
                }
            }
        }

        if (permissionsList.isNotEmpty()) {
            val callBack = 0
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.INTERNET),
                callBack
            )
        }

        detailBackButton.setOnClickListener { onBackPressed() }
    }

    override fun onViewStatusUpdated(viewStatus: DetailViewStatus) {
        if (viewStatus.url.isNotEmpty()) {
            detailWebView.loadUrl(viewStatus.url)
        }
    }

    override fun onBackPressed() {
        this@DetailActivity.finish()
    }

    private inner class MyWebClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            return false
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            hideLoading()
            super.onPageFinished(view, url)
        }

        override fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?
        ) {
            view.apply {
                File(this@DetailActivity.cacheDir, "org.chromium.android_webview").let {
                    if (error?.errorCode == -2 && (!it.exists() || it.listFiles()?.size ?: 0 < 5)) {
                        this@DetailActivity.toast(getString(R.string.no_internet))
                    }
                }
            }

            super.onReceivedError(view, request, error)
        }
    }

    @Suppress("DEPRECATION")
    override fun onDestroy() {
        super.onDestroy()
        detailWebView.clearHistory()
        detailWebView.clearView()
        detailWebView.removeAllViews()
        detailWebView.destroy()
    }
}