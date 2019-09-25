package com.faizal.bottomnavigation.model

import android.os.Parcel
import android.os.Parcelable

class Address() : Parcelable {
    var pincode: String? = null
    var locationname: String? = null
    var streetName: String? = null
    var town: String? = null
    var city: String? = null
    var state: String? = null
    var country: String? = null
    var cityCode: String? = null
    var countryCode: String? = null

    constructor(parcel: Parcel) : this() {
        pincode = parcel.readString()
        locationname = parcel.readString()
        streetName = parcel.readString()
        town = parcel.readString()
        city = parcel.readString()
        state = parcel.readString()
        country = parcel.readString()
        cityCode = parcel.readString()
        countryCode = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        with(parcel){
            writeString(pincode)
            writeString(locationname)
            writeString(streetName)
            writeString(town)
            writeString(city)
            writeString(state)
            writeString(country)
            writeString(cityCode)
            writeString(countryCode)
        }

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Address> {
        override fun createFromParcel(parcel: Parcel): Address {
            return Address(parcel)
        }

        override fun newArray(size: Int): Array<Address?> {
            return arrayOfNulls(size)
        }
    }
}