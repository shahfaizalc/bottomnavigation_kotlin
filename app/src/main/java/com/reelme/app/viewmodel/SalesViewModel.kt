package com.reelme.app.viewmodel


import android.util.Log
import android.view.View
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.reelme.app.adapter.SaleItemsAdapter
import com.reelme.app.util.RequestType
import com.reelme.app.util.ViewModelCallback
import com.reelme.app.listeners.ResultListener
import com.reelme.app.model.Sale
import com.reelme.app.util.SalesItemsRepository
import com.reelme.app.BR

class SalesViewModel : ViewModelCallback(), ResultListener {

    /**
     * TAG
     */
    private val TAG = "SalesViewModel"

    /**
     * SalesItems repository
     */
    private val salesItemsRepository: SalesItemsRepository

    /**
     * SaleItemsAdapter
     */
    var saleItemsAdapter: SaleItemsAdapter? = null
    var linearLayoutManager: StaggeredGridLayoutManager? = null


    init {
        salesItemsRepository = SalesItemsRepository()
    }

    /**
     * Progress bar visibility
     */
    @get:Bindable
    var progressBarVisible = View.VISIBLE
        set(progressBarVisible) {
            field = progressBarVisible
            notifyPropertyChanged(BR.progressBarVisible)
        }

    /**
     * UI state on error
     */
    @get:Bindable
    var onError = View.GONE
        set(onError) {
            field = onError
            notifyPropertyChanged(BR.onError)
        }

    fun getAllSalesList(requestType: RequestType): MutableLiveData<List<Sale>>
            = salesItemsRepository.getMutableLiveData(this,  requestType)

    override fun onError(err: String) {
        Log.d(TAG, "onError: onError")
        progressBarVisible = View.GONE
        onError = View.VISIBLE
    }

    override fun onSuccess() {
        Log.d(TAG, "onSuccess: onSuccess")
        progressBarVisible = View.GONE
        onError = View.GONE
    }
}
