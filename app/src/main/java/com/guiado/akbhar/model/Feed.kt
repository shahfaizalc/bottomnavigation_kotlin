package com.guiado.akbhar.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

 class Feed : Serializable {

     @SerializedName("plantId")
     val plantId: String? = null

     @SerializedName("title")
     var title: String? = null

     @SerializedName("brief")
     var brief: String? = null

     @SerializedName("date")
     var date: String? = null

     @SerializedName("homeurl")
     var homeurl: String? = null

     @SerializedName("imgurl")
     var imgurl: String? = null

     @SerializedName("growZoneNumber")
     var growZoneNumber: String? = null

     @SerializedName("newsprovider")
     var newsprovider: String? = null

     @SerializedName("newsurl")
     var newsurl: String? = null

 }
