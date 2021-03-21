
package com.reelme.app.model_sales.newidea;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Createidearesponse {

    @SerializedName("errors")
    private List<Object> mErrors;
    @SerializedName("id")
    private String mId;
    @SerializedName("success")
    private Boolean mSuccess;

    public List<Object> getErrors() {
        return mErrors;
    }

    public void setErrors(List<Object> errors) {
        mErrors = errors;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public Boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Boolean success) {
        mSuccess = success;
    }

}
