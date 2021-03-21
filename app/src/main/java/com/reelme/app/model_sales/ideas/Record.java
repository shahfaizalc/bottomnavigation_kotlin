
package com.reelme.app.model_sales.ideas;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Record {

    @SerializedName("attributes")
    private Attributes mAttributes;
    @SerializedName("Benefit__c")
    private String mBenefitC;
    @SerializedName("Impact__c")
    private String mImpactC;
    @SerializedName("Name")
    private String mName;
    @SerializedName("Status__c")
    private String mStatusC;
    @SerializedName("Description__c")
    private String Description__c;

    public String getDescription__c() {
        return Description__c;
    }

    public void setDescription__c(String description__c) {
        Description__c = description__c;
    }



    public Attributes getAttributes() {
        return mAttributes;
    }

    public void setAttributes(Attributes attributes) {
        mAttributes = attributes;
    }

    public String getBenefitC() {
        return mBenefitC;
    }

    public void setBenefitC(String benefitC) {
        mBenefitC = benefitC;
    }

    public String getImpactC() {
        return mImpactC;
    }

    public void setImpactC(String impactC) {
        mImpactC = impactC;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getStatusC() {
        return mStatusC;
    }

    public void setStatusC(String statusC) {
        mStatusC = statusC;
    }

}
