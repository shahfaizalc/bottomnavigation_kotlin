package com.nioneer.nioneer.model

import java.io.Serializable
import com.google.gson.annotations.SerializedName
import com.itravis.ticketexchange.model.*

class SingleAttribute : Serializable {

    @SerializedName("figure")
    var figure: List<FigureItem>? = null

    @SerializedName("marital_status")
    var maritalStatus: List<MaritalStatusItem>? = null

    @SerializedName("gender")
    var gender: List<GenderItem>? = null

    @SerializedName("ethnicity")
    var ethnicity: List<EthnicityItem>? = null

    @SerializedName("religion")
    var religion: List<ReligionItem>? = null
}