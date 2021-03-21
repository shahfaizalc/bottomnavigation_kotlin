
package com.reelme.app.model_sales.goal;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Record {

    @SerializedName("attributes")
    private Attributes mAttributes;
    @SerializedName("Business_Cluster__c")
    private String mBusinessClusterC;
    @SerializedName("Description__c")
    private Object mDescriptionC;
    @SerializedName("Id")
    private String mId;
    @SerializedName("Name")
    private String mName;
    @SerializedName("Priority__c")
    private String mPriorityC;

    public Attributes getAttributes() {
        return mAttributes;
    }

    public void setAttributes(Attributes attributes) {
        mAttributes = attributes;
    }

    public String getBusinessClusterC() {
        return mBusinessClusterC;
    }

    public void setBusinessClusterC(String businessClusterC) {
        mBusinessClusterC = businessClusterC;
    }

    public Object getDescriptionC() {
        return mDescriptionC;
    }

    public void setDescriptionC(Object descriptionC) {
        mDescriptionC = descriptionC;
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

    public String getPriorityC() {
        return mPriorityC;
    }

    public void setPriorityC(String priorityC) {
        mPriorityC = priorityC;
    }

}
