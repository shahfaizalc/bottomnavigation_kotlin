package com.guiado.akbhar.model

import android.os.Parcel
import android.os.Parcelable


data class Category(val category: List<CategoryItem>) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(CategoryItem)!!) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(category)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Category> {
        override fun createFromParcel(parcel: Parcel): Category {
            return Category(parcel)
        }

        override fun newArray(size: Int): Array<Category?> {
            return arrayOfNulls(size)
        }
    }
}