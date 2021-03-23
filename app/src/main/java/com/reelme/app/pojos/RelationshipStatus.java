
package com.reelme.app.pojos;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class RelationshipStatus {

    @SerializedName("Relationship")
    private String mRelationship;

    public String getRelationship() {
        return mRelationship;
    }

    public void setRelationship(String relationship) {
        mRelationship = relationship;
    }

}
