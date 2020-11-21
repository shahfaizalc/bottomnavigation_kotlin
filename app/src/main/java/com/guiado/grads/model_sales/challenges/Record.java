
package com.guiado.grads.model_sales.challenges;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Record {

    @SerializedName("attributes")
    private Attributes mAttributes;
    @SerializedName("Id")
    private String mId;
    @SerializedName("Name")
    private String mName;

    public Attributes getAttributes() {
        return mAttributes;
    }

    public void setAttributes(Attributes attributes) {
        mAttributes = attributes;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

}
