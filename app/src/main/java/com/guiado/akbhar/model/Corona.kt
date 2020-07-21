package com.guiado.akbhar.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

 class Corona : Serializable {

     @SerializedName("confirmed")
     val confirmed: String? = null

     @SerializedName("death")
     var death: String? = null

     @SerializedName("recovered")
     var recovered: String? = null

 }
