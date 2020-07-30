
package com.guiado.grads.model_sales;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class CreateIdeas {

    @SerializedName("Challenge__c")
    private String mChallengeC;
    @SerializedName("INC_Details__c")
    private String mINCDetailsC;
    @SerializedName("INC_Features__c")
    private String mINCFeaturesC;
    @SerializedName("INC_Implementation_Approach__c")
    private String mINCImplementationApproachC;
    @SerializedName("INC_Name__c")
    private String mINCNameC;
    @SerializedName("Status__c")
    private String mStatusC;

    public String getChallengeC() {
        return mChallengeC;
    }

    public void setChallengeC(String challengeC) {
        mChallengeC = challengeC;
    }

    public String getINCDetailsC() {
        return mINCDetailsC;
    }

    public void setINCDetailsC(String iNCDetailsC) {
        mINCDetailsC = iNCDetailsC;
    }

    public String getINCFeaturesC() {
        return mINCFeaturesC;
    }

    public void setINCFeaturesC(String iNCFeaturesC) {
        mINCFeaturesC = iNCFeaturesC;
    }

    public String getINCImplementationApproachC() {
        return mINCImplementationApproachC;
    }

    public void setINCImplementationApproachC(String iNCImplementationApproachC) {
        mINCImplementationApproachC = iNCImplementationApproachC;
    }

    public String getINCNameC() {
        return mINCNameC;
    }

    public void setINCNameC(String iNCNameC) {
        mINCNameC = iNCNameC;
    }

    public String getStatusC() {
        return mStatusC;
    }

    public void setStatusC(String statusC) {
        mStatusC = statusC;
    }

}
