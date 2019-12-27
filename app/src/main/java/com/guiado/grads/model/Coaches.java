package com.guiado.grads.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Coaches implements Serializable {

	@SerializedName("india")
	private List<CoachItem> india;

	public void setIndia(List<CoachItem> india){
		this.india = india;
	}

	public List<CoachItem> getIndia(){
		return india;
	}

	@Override
 	public String toString(){
		return 
			"Coaches{" +
			"india = '" + india + '\'' + 
			"}";
		}
}