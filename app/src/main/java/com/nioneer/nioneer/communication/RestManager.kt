package com.nioneer.nioneer.communication

import android.content.Context
import android.util.Log
import com.nioneer.nioneer.rss.utils.HasNetwork.Companion.isConnected
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.TimeUnit

/**
 *  Class to provide offline cache
 */
class RestManager {

    /**
     * TAG: class name
     */
    private val TAG = "RetrofitManager"

    /**
     * Cache control header
     */
    private val HEADER_CACHE_CONTROL = "Cache-Control"

    /**
     * Cache header pragma
     */
    private val HEADER_PRAGMA = "Pragma"

    /**
     * Cache  size
     */
    private val CACHE_SIZE = 10 * 1024 * 1024; //10MB

    /**
     * Max age
     */
    private val MAX_AGE = 60;

    /**
     * Max Stale
     */
    private val MAX_STALE = 7;

    /**
     * File directory child
     */
     private val DIRECTORY_CHILD = "http-cache"




    /**
     * Build the response cache for offline support
     * @param context Context
     */
    fun getCachedRetrofit(context: Context) = OkHttpClient.Builder()
                .addInterceptor(provideOfflineCacheInterceptor())
                .addNetworkInterceptor(provideNetworkInterceptor(context))
                .cache(provideCache(context)).build()


    /**
     * provide files directory for cache
     * @param context Context
     */
    private fun provideCache(context: Context): Cache {
        lateinit var mCache: Cache

        try {
            mCache = Cache(File(context.getCacheDir(), DIRECTORY_CHILD), CACHE_SIZE.toLong())
        } catch (e: Exception) {
            Log.e(TAG, "Could not create Cache!")
        }

        return mCache
    }

    /**
     * Network interceptor to with network state
     * @param context : Context
     */
    private fun provideNetworkInterceptor(context: Context) = Interceptor { chain ->
        val cacheControl: CacheControl

        when (isConnected(context)) {
            true -> cacheControl = buildCacheControlSeconds()
            false -> cacheControl = buildCacheControlDays()
        }

        val response = chain.proceed(chain.request())
        response.newBuilder()
                .removeHeader(HEADER_PRAGMA)
                .removeHeader(HEADER_CACHE_CONTROL)
                .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                .build()
    }


    /**
     * Cache interceptor for offline state
     */
    private fun provideOfflineCacheInterceptor() = Interceptor { chain ->
        var request = chain.request()
        val cacheControl = buildCacheControlDays()

        request = request.newBuilder()
                .removeHeader(HEADER_PRAGMA)
                .removeHeader(HEADER_CACHE_CONTROL)
                .cacheControl(cacheControl)
                .build()

        chain.proceed(request)
    }

    /**
     *  Cache maximum stale period in days
     */
    private fun buildCacheControlDays() = CacheControl.Builder()
            .maxStale(MAX_STALE, TimeUnit.DAYS)
            .build()

    /**
     * Cache maxAge period  in seconds
     */
    private fun buildCacheControlSeconds() = CacheControl.Builder()
            .maxAge(MAX_AGE, TimeUnit.SECONDS)
            .build()

}