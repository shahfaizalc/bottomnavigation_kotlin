package com.guiado.akbhar.utils

import android.net.http.SslError
import android.webkit.*
import com.guiado.akbhar.listeners.WebViewCallback

/**
 * A class to handle the WebViewClient
 */
class MyWebViewClient(internal var param: WebViewCallback) : WebViewClient() {



    /**
     * On page successfully finished to load. Will respond success call back
     */
    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        param.onSuccess()
    }

    /**
     * on page load failure. Error will be responded with onError call back
     * @param view: WebView?
     * @param request: WebResourceRequest
     * @param error : WebResourceError
     */
    override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
        super.onReceivedError(view, request, error)
        param.onError(error.toString())
    }

    /**
     * on page load failure. Error will be responded with onError call back
     * @param view : WebView
     * @param request: WebResourceRequest
     * @param errorResponse: WebSourceResponse
     */
    override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?)
    {
        super.onReceivedHttpError(view, request, errorResponse)
        param.onError("Error Code " + errorResponse!!.statusCode)
    }

    /**
     * on page load failure. Error will be responded with onError call back
     * @param view WebView
     * @param handler SslErrorHandler
     * @param error SslError
     */
    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
        super.onReceivedSslError(view, handler, error)
        param.onError(error.toString())
    }

    /**
     * shouldOverrideUrlLoading retuns true always
     */
    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        return true
    }
}