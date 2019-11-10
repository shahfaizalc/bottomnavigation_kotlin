package com.faizal.bottomnavigation.viewmodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.os.Handler
import com.faizal.bottomnavigation.BR
import java.util.*

class UserViewModel : BaseObservable() {

    @get:Bindable
    var userIds: List<String> = emptyList()
        private set(value) {
            field = value
            notifyPropertyChanged(BR.userIds)
        }

    init{
        addValues()
    }

    private fun addValues() {
        userIds = listOf("Iran", "Iraq")
    }
}