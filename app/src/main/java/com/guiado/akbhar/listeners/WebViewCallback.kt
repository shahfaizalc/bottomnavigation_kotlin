package com.guiado.akbhar.listeners

/**
 * Interface to notify the webview onLoad success or failure
 */
interface WebViewCallback {

    /**
     * On  webview onLoad success
     */
    fun onSuccess()

    /**
     * On webview onLoad failed
     * @param err : Error  message
     */
    fun onError(err: String)
}