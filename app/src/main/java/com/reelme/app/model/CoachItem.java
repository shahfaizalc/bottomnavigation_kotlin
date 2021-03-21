package com.reelme.app.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CoachItem implements Serializable {

	@SerializedName("categorycode")
	private String categorycode;

	@SerializedName("categoryname")
	private String categoryname;



	@Override
 	public String toString(){
		return 
			"CoachItem{" +
			"categoryname = '" + categoryname + '\'' +
			",categorycode = '" + categorycode + '\'' +
			"}";
		}

	public String getCategorycode() {
		return categorycode;
	}

	public void setCategorycode(String categorycode) {
		this.categorycode = categorycode;
	}

	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}
}