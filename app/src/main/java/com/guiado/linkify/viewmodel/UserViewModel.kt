package com.guiado.linkify.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.guiado.linkify.BR

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