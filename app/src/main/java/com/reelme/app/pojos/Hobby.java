
package com.reelme.app.pojos;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Hobby {

    @SerializedName("Hobbies")
    private String mHobbies;

    public String getHobbies() {
        return mHobbies;
    }

    public void setHobbies(String hobbies) {
        mHobbies = hobbies;
    }

}
