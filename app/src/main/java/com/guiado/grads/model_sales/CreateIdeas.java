
package com.guiado.grads.model_sales;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class CreateIdeas {

    @SerializedName("Benefit__c")
    private String mBenefitC;
    @SerializedName("Impact__c")
    private String mImpactC;
    @SerializedName("Innovation_Challenge__c")
    private String mInnovationChallengeC;
    @SerializedName("Name")
    private String mName;
    @SerializedName("Status__c")
    private String mStatusC;
    @SerializedName("Targeted_Business_group__c")
    private String mTargetedBusinessGroupC;

    public String getDescription__c() {
        return Description__c;
    }

    public void setDescription__c(String description__c) {
        Description__c = description__c;
    }

    @SerializedName("Description__c")
    private String Description__c;

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

    public String getInnovationChallengeC() {
        return mInnovationChallengeC;
    }

    public void setInnovationChallengeC(String innovationChallengeC) {
        mInnovationChallengeC = innovationChallengeC;
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

    public String getTargetedBusinessGroupC() {
        return mTargetedBusinessGroupC;
    }

    public void setTargetedBusinessGroupC(String targetedBusinessGroupC) {
        mTargetedBusinessGroupC = targetedBusinessGroupC;
    }

}
