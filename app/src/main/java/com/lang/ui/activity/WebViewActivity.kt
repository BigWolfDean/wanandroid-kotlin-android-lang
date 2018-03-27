package com.lang.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
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
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

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
        swipeRefreshLayout = findViewById(R.id.web_view_swipe_refresh)

        swipeRefreshLayout.isEnabled = false
        swipeRefreshLayout.isRefreshing = true
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {

        webView.settings.javaScriptEnabled = true
        webView.settings.setSupportZoom(true)
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true
        webView.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN

        webView.webViewClient = WebViewClient()
        webView.webChromeClient = MyWebClient()
        webView.loadUrl(intent.extras.getString("url"))
    }


    inner class MyWebClient : WebChromeClient() {

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            if (newProgress == 100) {
                swipeRefreshLayout.isRefreshing = false
            }

        }
    }

}
