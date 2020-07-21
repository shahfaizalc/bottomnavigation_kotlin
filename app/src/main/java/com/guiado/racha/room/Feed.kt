package com.guiado.racha.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "plants")
data class Feed(

    @PrimaryKey
    @ColumnInfo(name = "id") val plantId: String,

    var brief: String,

    var date: String,

    var imgurl: String,

    var growZoneNumber :String,

    var newsprovider: String,

    var newsurl: String

)