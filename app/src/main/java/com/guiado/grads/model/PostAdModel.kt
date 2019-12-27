package com.guiado.grads.model

import android.os.Parcel
import android.os.Parcelable


class PostAdModel() : Parcelable {
    var userId : String? = null
    var title: String? = null
    var description: String? = null
    var showTime: String? = null
    var showDate: String? = null
    var categorySelect: Int = 0
    var price: Int = 0
    var discount: Int = 0
    var ticketCount: Int = 0
    var balanceTicket: Int = 0
    var status: Int = 0
    var showPhoneNumber: Int = 8
    var priceFixed: Boolean = false
    var address: Address? = null
    var adCreationTime:Long =0

    constructor(parcel: Parcel) : this() {
        userId = parcel.readString()
        title = parcel.readString()
        description = parcel.readString()
        showTime = parcel.readString()
        showDate = parcel.readString()
        categorySelect = parcel.readInt()
        price = parcel.readInt()
        discount = parcel.readInt()
        ticketCount = parcel.readInt()
        balanceTicket = parcel.readInt()
        status = parcel.readInt()
        showPhoneNumber = parcel.readInt()
        priceFixed = parcel.readByte() != 0.toByte()
      //  address = parcel.readParcelable(Address::class.java.classLoader)
        adCreationTime = parcel.readLong()

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userId)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(showTime)
        parcel.writeString(showDate)
        parcel.writeInt(categorySelect)
        parcel.writeInt(price)
        parcel.writeInt(discount)
        parcel.writeInt(ticketCount)
        parcel.writeInt(balanceTicket)
        parcel.writeInt(status)
        parcel.writeInt(showPhoneNumber)
        parcel.writeByte(if (priceFixed) 1 else 0)
       // parcel.writeParcelable(address, flags)
        parcel.writeLong(adCreationTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PostAdModel> {
        override fun createFromParcel(parcel: Parcel): PostAdModel {
            return PostAdModel(parcel)
        }

        override fun newArray(size: Int): Array<PostAdModel?> {
            return arrayOfNulls(size)
        }
    }


}

