package com.guiado.akbhar.communication

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * interface get service
 */
interface GetServiceBlog {

    /**
     * Retrofit service
     * @param url : path
     */
    @GET
    fun retrieveBlogArticles(@Url url:String): Deferred<String>

}