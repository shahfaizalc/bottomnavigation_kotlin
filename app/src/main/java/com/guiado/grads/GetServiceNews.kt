package com.news.list.communication

import com.guiado.grads.model_sales.Authenticaiton
import com.guiado.grads.model_sales.CreateIdeas
import com.guiado.grads.model_sales.Createidearesponse
import com.guiado.grads.model_sales.QueryIdeas
import com.guiado.grads.model_sales.challenges.QueryChallenges
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.*

/**
 * Retrofit service
 */
interface GetServiceNews {

    @Headers(*["Content-type: application/json"])
    @POST(    "token?grant_type=password&client_id=3MVG9PE4xB9wtoY89nJ97OMsmiY5_piCFXtykr9nfXk2jgzuWQZl7JxYL3XOb8_0aibKGR9mCK1lsZTjhTLTP&client_secret=3F0580BF7553401AAD2DFFC1A5434DF99FD3FC6C0CC47EF96AE28B4B39B4D64D")
    fun sendPosts(@Body posts: Authenticaiton?, @Query("username")  username:String, @Query("password") password:String): Call<Authenticaiton?>?


    @Headers(*["Content-type: application/json"])
    @GET
    fun getQueryIdeas(@Url url:String,@Header("Authorization") bearer: String?): Deferred<QueryIdeas>


    @Headers(*["Content-type: application/json"])
    @POST(    "data/v48.0/sobjects/INC_IdeaDetails__c")
    fun createQueryIdeas(@Body posts: CreateIdeas?, @Header("Authorization") bearer: String?) : Call<CreateIdeas?>?

    @Headers(*["Content-type: application/json"])
    @GET
    fun getQueryChallenges(@Url url:String,@Header("Authorization") bearer: String?): Deferred<QueryChallenges>


}