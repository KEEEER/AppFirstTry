package com.example.user.appexample;

import android.widget.*; 
import com.example.user.appexample.*;
import java.util.Collections;

class Song{
//	public byte[] Data;
	public String Name;
	public String Artist;
	public String Path;
	public Long EditDate;
	public int Duration;

	Song(String Name , String Artist , String Path , Long EditDate , int Duration){
		this.Name = Name;
		this.Artist = Artist;
		this.Path = Path;
		this.EditDate = EditDate;
		this.Duration = Duration;
	}
	Song(){

	}
}
