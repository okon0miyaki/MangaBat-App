package com.okon0miyaki.mywebview

import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private val historyList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.statusBarColor = android.graphics.Color.TRANSPARENT
        window.navigationBarColor = android.graphics.Color.TRANSPARENT

        webView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true

        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                saveUrlToHistory(url)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                url?.let { saveUrlToHistory(it) }
            }
        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onJsAlert(
                view: WebView,
                url: String,
                message: String,
                result: JsResult
            ): Boolean {
                result.confirm()
                return true
            }
        }

        webView.loadUrl("https://h.mangabat.com/mangabat")

        loadHistoryFromPreferences()
    }

    fun saveUrlToHistory(url: String?) {
        url?.let {
            if (!historyList.contains(it)) {
                historyList.add(it)
                saveHistoryToPreferences()
            }
        }
    }

    fun saveHistoryToPreferences() {
        val sharedPreferences = getSharedPreferences("WebViewHistory", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putStringSet("history", historyList.toSet())
        editor.apply()
    }

    fun loadHistoryFromPreferences() {
        val sharedPreferences = getSharedPreferences("WebViewHistory", MODE_PRIVATE)
        val savedHistory = sharedPreferences.getStringSet("history", emptySet()) ?: emptySet()
        historyList.clear()
        historyList.addAll(savedHistory)
    }

    override fun onResume() {
        super.onResume()
        loadHistoryFromPreferences()
    }

    override fun onPause() {
        super.onPause()
        saveHistoryToPreferences()
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}