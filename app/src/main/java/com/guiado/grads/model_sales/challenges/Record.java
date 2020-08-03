
package com.guiado.grads.model_sales.challenges;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Record {

    @SerializedName("attributes")
    private Attributes mAttributes;
    @SerializedName("Description__c")
    private String mDescriptionC;
    @SerializedName("Id")
    private String mId;
    @SerializedName("Name__c")
    private String mNameC;

    public Attributes getAttributes() {
        return mAttributes;
    }

    public void setAttributes(Attributes attributes) {
        mAttributes = attributes;
    }

    public String getDescriptionC() {
        return mDescriptionC;
    }

    public void setDescriptionC(String descriptionC) {
        mDescriptionC = descriptionC;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getNameC() {
        return mNameC;
    }

    public void setNameC(String nameC) {
        mNameC = nameC;
    }

}
