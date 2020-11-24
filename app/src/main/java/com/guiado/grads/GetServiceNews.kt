package com.guiado.grads

import com.guiado.grads.model_sales.Authenticaiton
import com.guiado.grads.model_sales.CreateIdeas
import com.guiado.grads.model_sales.QueryIdeas
import com.guiado.grads.model_sales.challenges.QueryChallenges
import com.guiado.grads.model_sales.goal.Goals
import com.guiado.grads.model_sales.newchallenge.Newchallenge
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.*

/**
 * Retrofit service
 */
interface GetServiceNews {

    @Headers(*["Content-type: application/json"])
    @POST(    "token?grant_type=password&client_id=3MVG9PE4xB9wtoY89nJ97OMsmiXXMFpgIGTHzHK0A8WCdzGz9wxxDJKA0U_FX.bD3SKDvi1.ZRvJs8qFhCVKs&client_secret=0DE5F7FD5A6ACF7253F78946F9CA961D70F7C76E50B06648F4C6C103EB7208DE")
    fun sendPosts(@Body posts: Authenticaiton?, @Query("username")  username:String, @Query("password") password:String): Call<Authenticaiton?>?


    @Headers(*["Content-type: application/json"])
    @GET
    fun getQueryIdeas(@Url url:String,@Header("Authorization") bearer: String?): Deferred<QueryIdeas>


    @Headers(*["Content-type: application/json"])
    @POST(    "data/v49.0/sobjects/ICP_Idea__c/")
    fun createQueryIdeas(@Body posts: CreateIdeas?, @Header("Authorization") bearer: String?) : Call<CreateIdeas?>?

    @Headers(*["Content-type: application/json"])
    @GET
    fun getQueryChallenges(@Url url:String,@Header("Authorization") bearer: String?): Deferred<QueryChallenges>


    @Headers(*["Content-type: application/json"])
    @POST(    "data/v49.0/sobjects/ICP_Challenge__c/")
    fun createQueryChallenge(@Body posts: Newchallenge?, @Header("Authorization") bearer: String?) : Call<Newchallenge?>?

    @Headers(*["Content-type: application/json"])
    @GET
    fun getQueryGoals(@Url url:String,@Header("Authorization") bearer: String?): Deferred<Goals>



}