
package com.guiado.grads.model_sales.newchallenge;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Newchallenge {

    @SerializedName("Description__c")
    private String mDescriptionC;
    @SerializedName("Name__c")
    private String mNameC;

    public String getDescriptionC() {
        return mDescriptionC;
    }

    public void setDescriptionC(String descriptionC) {
        mDescriptionC = descriptionC;
    }

    public String getNameC() {
        return mNameC;
    }

    public void setNameC(String nameC) {
        mNameC = nameC;
    }

}
