
package com.guiado.akbhar.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Quote {

    @SerializedName("arabic")
    private String mArabic;
    @SerializedName("english")
    private String mEnglish;

    public String getArabic() {
        return mArabic;
    }

    public void setArabic(String arabic) {
        mArabic = arabic;
    }

    public String getEnglish() {
        return mEnglish;
    }

    public void setEnglish(String english) {
        mEnglish = english;
    }

}
