package com.guiado.linkify.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CitiesIndia implements Serializable {

	@SerializedName("india")
	private List<IndiaItem> india;

	public void setIndia(List<IndiaItem> india){
		this.india = india;
	}

	public List<IndiaItem> getIndia(){
		return india;
	}

	@Override
 	public String toString(){
		return 
			"CitiesIndia{" + 
			"india = '" + india + '\'' + 
			"}";
		}
}