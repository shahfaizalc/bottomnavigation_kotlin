
package com.reelme.app.pojos;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Occupation {

    @SerializedName("Occupations")
    private String mOccupations;

    public String getOccupations() {
        return mOccupations;
    }

    public void setOccupations(String occupations) {
        mOccupations = occupations;
    }

}
