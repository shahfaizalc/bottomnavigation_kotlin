package com.itravis.ticketexchange.model

import com.google.gson.annotations.SerializedName

import java.io.Serializable

class GenderItem : Serializable {

    @SerializedName("name")
    var name: String? = null

    @SerializedName("id")
    var id: String? = null
}