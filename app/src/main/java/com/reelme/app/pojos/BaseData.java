
package com.reelme.app.pojos;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class BaseData {

    @SerializedName("Children")
    private List<Child> mChildren;
    @SerializedName("Gender")
    private List<Gender> mGender;
    @SerializedName("Hobbies")
    private List<Hobby> mHobbies;
    @SerializedName("Occupations")
    private List<Occupation> mOccupations;
    @SerializedName("Relationship Status")
    private List<RelationshipStatus> mRelationshipStatus;
    @SerializedName("Religious Beliefs")
    private List<ReligiousBelief> mReligiousBeliefs;

    public List<Child> getChildren() {
        return mChildren;
    }

    public void setChildren(List<Child> children) {
        mChildren = children;
    }

    public List<Gender> getGender() {
        return mGender;
    }

    public void setGender(List<Gender> gender) {
        mGender = gender;
    }

    public List<Hobby> getHobbies() {
        return mHobbies;
    }

    public void setHobbies(List<Hobby> hobbies) {
        mHobbies = hobbies;
    }

    public List<Occupation> getOccupations() {
        return mOccupations;
    }

    public void setOccupations(List<Occupation> occupations) {
        mOccupations = occupations;
    }

    public List<RelationshipStatus> getRelationshipStatus() {
        return mRelationshipStatus;
    }

    public void setRelationshipStatus(List<RelationshipStatus> relationshipStatus) {
        mRelationshipStatus = relationshipStatus;
    }

    public List<ReligiousBelief> getReligiousBeliefs() {
        return mReligiousBeliefs;
    }

    public void setReligiousBeliefs(List<ReligiousBelief> religiousBeliefs) {
        mReligiousBeliefs = religiousBeliefs;
    }

}
