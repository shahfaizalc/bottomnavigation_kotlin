package com.reelme.app.util

import android.util.Log
import androidx.lifecycle.MutableLiveData

import com.reelme.app.listeners.ResultListener
import com.reelme.app.model.Sale

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SalesItemsRepository {

    /**
     * TAG
     */
    private val TAG = "SalesItemsRepository"

    /**
     * funtion to provide list of SalesItems
     */
    fun getMutableLiveData(resultListener: ResultListener, requestType: RequestType): MutableLiveData<List<Sale>> {
        //Sales list
        val mutableLiveData = MutableLiveData<List<Sale>>()

        //retrofit service
//        val service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService::class.java)
//
//        val responseObservable = getResponseType(requestType, service)
//        if (null == responseObservable) {
//            resultListener.onError("unknown host url")
//            return mutableLiveData
//        }
//        responseObservable.subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(getObserver(resultListener, mutableLiveData))
        return mutableLiveData
    }

    private fun getObserver(resultListener: ResultListener, liveData: MutableLiveData<List<Sale>>): Observer<List<Sale>> {
        return object : Observer<List<Sale>> {
            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(saleList: List<Sale>) {
                liveData.value = saleList
                resultListener.onSuccess()
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "initRecycler: initRequest onFailure " + e.localizedMessage)
                resultListener.onError(e.localizedMessage)
            }

            override fun onComplete() {
                Log.d(TAG, "initRecycler: initRequest onComplete")
            }
        }
    }

    private fun getResponseType(all: RequestType, service: GetDataService): Observable<List<Sale>>? {

        var observable: Observable<List<Sale>>? = null
        when (all) {
            RequestType.ALL -> observable = service.saleAll
            RequestType.MEN -> observable = service.saleMen
            RequestType.WOMEN -> observable = service.saleWomen
        }
        return observable
    }
}
