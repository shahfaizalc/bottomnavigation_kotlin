
package com.guiado.grads.model_sales;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Record {

    @SerializedName("attributes")
    private Attributes mAttributes;
    @SerializedName("Challenge__c")
    private String mChallengeC;
    @SerializedName("INC_Challenge_Title__c")
    private String mINCChallengeTitleC;
    @SerializedName("INC_Details__c")
    private String mINCDetailsC;
    @SerializedName("INC_Features__c")
    private String mINCFeaturesC;
    @SerializedName("INC_Implementation_Approach__c")
    private String mINCImplementationApproachC;
    @SerializedName("INC_Name__c")
    private String mINCNameC;
    @SerializedName("Id")
    private String mId;
    @SerializedName("Rating__c")
    private Integer mRatingC;
    @SerializedName("Status__c")
    private String mStatusC;

    public Attributes getAttributes() {
        return mAttributes;
    }

    public void setAttributes(Attributes attributes) {
        mAttributes = attributes;
    }

    public String getChallengeC() {
        return mChallengeC;
    }

    public void setChallengeC(String challengeC) {
        mChallengeC = challengeC;
    }

    public String getINCChallengeTitleC() {
        return mINCChallengeTitleC;
    }

    public void setINCChallengeTitleC(String iNCChallengeTitleC) {
        mINCChallengeTitleC = iNCChallengeTitleC;
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

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public Integer getRatingC() {
        return mRatingC;
    }

    public void setRatingC(Integer ratingC) {
        mRatingC = ratingC;
    }

    public String getStatusC() {
        return mStatusC;
    }

    public void setStatusC(String statusC) {
        mStatusC = statusC;
    }

}
