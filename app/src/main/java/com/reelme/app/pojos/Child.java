
package com.reelme.app.pojos;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Child {

    @SerializedName("children")
    private String mChildren;

    public String getChildren() {
        return mChildren;
    }

    public void setChildren(String children) {
        mChildren = children;
    }

}
