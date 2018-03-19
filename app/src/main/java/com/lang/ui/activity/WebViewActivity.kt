package com.lang.ui.activity

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.lang.R
import com.lang.util.initToolbar
import com.lang.util.setToolbarTitle
import kotlinx.android.synthetic.main.include_toolbar.*

class WebViewActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        initView()
        initToolbar(toolbar)
        setToolbarTitle(toolbar_title, intent.extras.getString("title"))
        initWebView()
    }

    private fun initView() {
        webView = findViewById(R.id.web_view)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {

        webView.settings.javaScriptEnabled = true
        webView.settings.setSupportZoom(true)
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true
        webView.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN

        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()
        webView.loadUrl(intent.extras.getString("url"))
    }


}
