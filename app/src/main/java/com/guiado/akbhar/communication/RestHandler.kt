package com.guiado.akbhar.communication

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

/**
 * Class to create Retrofit service
 */
class RestHandler {

    /**
     * Retrofit service
     * @param  baseUrl :domain url
     * @param context : Context
     */
    fun getServiceBlogArticles(baseUrl: String, context: Context)= Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(GetServiceBlog::class.java)
}