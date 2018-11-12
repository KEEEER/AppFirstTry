package com.example.user.appexample;

import android.widget.*; 
import com.example.user.appexample.*;
import java.util.Collections;

class Song{
	//public byte[] data;
	public String Name;
	public String Path;
	public Long EditDate;
	public int Duration;

	Song(String Name , String Path , Long EditDate , int Duration){
		this.Name = Name;
		this.Path = Path;
		this.EditDate = EditDate;
		this.Duration = Duration;
	}	
	

}


/* File 

	String getName()
	long   lastModified()  
	
*/