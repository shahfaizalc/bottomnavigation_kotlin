package com.news.list.communication

import com.guiado.grads.Authenticaiton
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Retrofit service
 */
interface GetServiceNews {

    @Headers(*["Content-type: application/json"])
    @POST(    "token?grant_type=password&client_id=3MVG9PE4xB9wtoY89nJ97OMsmiY5_piCFXtykr9nfXk2jgzuWQZl7JxYL3XOb8_0aibKGR9mCK1lsZTjhTLTP&client_secret=3F0580BF7553401AAD2DFFC1A5434DF99FD3FC6C0CC47EF96AE28B4B39B4D64D&username=incubatorintegrationuser@philips.com.poc&password=Philips.1234"
    )
    fun sendPosts(@Body posts: Authenticaiton?): Call<Authenticaiton?>?
}