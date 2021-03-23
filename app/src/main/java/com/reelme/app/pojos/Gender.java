
package com.reelme.app.pojos;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Gender {

    @SerializedName("Gender")
    private String mGender;

    public String getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        mGender = gender;
    }

}
