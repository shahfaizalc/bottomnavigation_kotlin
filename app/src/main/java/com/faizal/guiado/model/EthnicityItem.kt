package com.itravis.ticketexchange.model

import com.google.gson.annotations.SerializedName

import java.io.Serializable

class EthnicityItem : Serializable {

    @SerializedName("name")
    var name: String? = null

    @SerializedName("id")
    var id: String? = null
}