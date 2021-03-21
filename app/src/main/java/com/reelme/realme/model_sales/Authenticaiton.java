
package com.reelme.realme.model_sales;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Authenticaiton {

    @SerializedName("access_token")
    private String mAccessToken;
    @SerializedName("id")
    private String mId;
    @SerializedName("instance_url")
    private String mInstanceUrl;
    @SerializedName("issued_at")
    private String mIssuedAt;
    @SerializedName("signature")
    private String mSignature;
    @SerializedName("token_type")
    private String mTokenType;

    public String getAccessToken() {
        return mAccessToken;
    }

    public void setAccessToken(String accessToken) {
        mAccessToken = accessToken;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getInstanceUrl() {
        return mInstanceUrl;
    }

    public void setInstanceUrl(String instanceUrl) {
        mInstanceUrl = instanceUrl;
    }

    public String getIssuedAt() {
        return mIssuedAt;
    }

    public void setIssuedAt(String issuedAt) {
        mIssuedAt = issuedAt;
    }

    public String getSignature() {
        return mSignature;
    }

    public void setSignature(String signature) {
        mSignature = signature;
    }

    public String getTokenType() {
        return mTokenType;
    }

    public void setTokenType(String tokenType) {
        mTokenType = tokenType;
    }

}
