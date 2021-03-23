
package com.reelme.app.pojos;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ReligiousBelief {

    @SerializedName("Religious")
    private String mReligious;

    public String getReligious() {
        return mReligious;
    }

    public void setReligious(String religious) {
        mReligious = religious;
    }

}
