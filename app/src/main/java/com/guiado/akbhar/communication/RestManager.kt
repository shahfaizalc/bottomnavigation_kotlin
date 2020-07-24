package com.guiado.akbhar.communication



import android.content.Context
import android.util.Log
import com.guiado.akbhar.communication.HasNetwork.Companion.hasNetwork
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.TimeUnit

/**
 *  Class to provide cache
 */
class RestManager {

    private val TAG = "RetrofitManager"
    private val HEADER_CACHE_CONTROL = "Cache-Control"
    private val HEADER_PRAGMA = "Pragma"
    private val CACHE_SIZE = 10 * 1024 * 1024; //10MB
    private val TIMEOUT: Long = 30 //Seconds

    fun getCachedRetrofit(context: Context): OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(provideOfflineCacheInterceptor())
            .addNetworkInterceptor(provideNetworkInterceptor(context))
            .cache(provideCache(context)).build()


    private fun provideCache(context: Context): Cache {
        lateinit var mCache: Cache

        try {
            mCache = Cache(File(context.getCacheDir(), "http-cache"), CACHE_SIZE.toLong())
        } catch (e: Exception) {
            Log.e(TAG, "Could not create Cache!")
        }

        return mCache
    }

    private fun provideNetworkInterceptor(context: Context) = Interceptor { chain ->
        val response = chain.proceed(chain.request())
        val cacheControl: CacheControl

        when (hasNetwork(context)) {
            true -> cacheControl = buildCacheControlSeconds()
            false -> cacheControl = buildCacheControlDays()
        }

        response.newBuilder()
                .removeHeader(HEADER_PRAGMA)
                .removeHeader(HEADER_CACHE_CONTROL)
                .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                .build()
    }


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

    private fun buildCacheControlDays() = CacheControl.Builder()
            .maxStale(7, TimeUnit.DAYS)
            .build()

    private fun buildCacheControlSeconds() = CacheControl.Builder()
            .maxAge(60, TimeUnit.SECONDS)
            .build()

}