package com.reelme.app.util

import com.reelme.app.model.Sale
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Class to Get data service
 */
interface GetDataService {

    @get:GET("men.json")
    val saleMen: Observable<List<Sale>>

    @get:GET("women.json")
    val saleWomen: Observable<List<Sale>>

    @get:GET("all.json")
    val saleAll: Observable<List<Sale>>
}