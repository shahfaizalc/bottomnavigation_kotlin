
package com.guiado.akbhar.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Covid19 {

    @SerializedName("Countries")
    private List<Country> mCountries;
    @SerializedName("Date")
    private String mDate;
    @SerializedName("Global")
    private Global mGlobal;

    public List<Country> getCountries() {
        return mCountries;
    }

    public void setCountries(List<Country> countries) {
        mCountries = countries;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public Global getGlobal() {
        return mGlobal;
    }

    public void setGlobal(Global global) {
        mGlobal = global;
    }

}
