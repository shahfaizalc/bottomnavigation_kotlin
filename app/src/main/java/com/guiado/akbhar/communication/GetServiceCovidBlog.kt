package com.guiado.akbhar.communication

import com.guiado.akbhar.model.Covid19
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * interface get service
 */
interface GetServiceCovidBlog {

    /**
     * Retrofit service
     * @param url : path
     */
    @GET
    fun retrieveBlogArticles(@Url url:String):Deferred<Covid19>

}