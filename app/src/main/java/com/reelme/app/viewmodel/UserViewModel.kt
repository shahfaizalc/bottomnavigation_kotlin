package com.reelme.app.viewmodel

import android.os.Handler
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import java.util.*
import com.reelme.app.BR
import com.reelme.app.model.Sale


class UserViewModel : BaseObservable() {

    @get:Bindable
    var userIds: MutableList<Sale> = mutableListOf()
        private set(value) {
            field = value
            notifyPropertyChanged(BR.userIds)
        }

    @get:Bindable
    var changedPositions: Set<Int> = mutableSetOf()
        private set(value) {
            field = value
            notifyPropertyChanged(BR.changedPositions)
        }

    private val updateInterval = 1000L
    private val updateHandler = Handler()
    private val random = Random()



    private fun updateList() {

        var sale = Sale()
        userIds.add(0,sale)
        userIds.add(1,sale)


    }

    fun startUpdates() {
        initList()
    }

    private fun initList() {
       // userIds = MutableList<Sale>()
        userIds = mutableListOf<Sale>()
        updateList()

    }


    fun stopUpdates() {
    }
}