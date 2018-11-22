package com.example.user.appexample;

import android.widget.*; 
import com.example.user.appexample.*;
import java.util.Collections;

class Song{
	private byte[] graphic;
	private String name;
	private String artist;
	private String path;
	private Long editDate;
	private int duration;

	Song(byte[] data , String name , String artist , String path , Long editDate , int duration){
		this.graphic = data;
		this.name = name;
		this.artist = artist;
		this.path = path;
		this.editDate = editDate;
		this.duration = duration;
	}
	public byte[] getGraphic(){
		return graphic;
	}
	public String getName(){
		return name;
	}
	public String getArtist(){
		return artist;
	}
	public String getPath(){
		return path;
	}
	public Long getEditDate(){
		return editDate;
	}
	public int getDuration(){
		return duration;
	}

	public void setGraphic(byte[] b){
		graphic = b;
	}
	public void setName(String s){
		name = s;
	}
	public void setArtist(String s){
		artist = s;
	}
	public void setPath(String s){
		path = s;
	}
	public void setEditDate(Long l){
		editDate = l;
	}
	public void setDuration(int i){
		duration = i;
	}


}
