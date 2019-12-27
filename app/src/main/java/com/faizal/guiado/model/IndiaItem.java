package com.faizal.guiado.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class IndiaItem implements Serializable {

	@SerializedName("citycode")
	private String citycode;

	@SerializedName("cityname")
	private String cityname;

	public void setCitycode(String citycode){
		this.citycode = citycode;
	}

	public String getCitycode(){
		return citycode;
	}

	public void setCityname(String cityname){
		this.cityname = cityname;
	}

	public String getCityname(){
		return cityname;
	}

	@Override
 	public String toString(){
		return 
			"IndiaItem{" + 
			"citycode = '" + citycode + '\'' + 
			",cityname = '" + cityname + '\'' + 
			"}";
		}
}