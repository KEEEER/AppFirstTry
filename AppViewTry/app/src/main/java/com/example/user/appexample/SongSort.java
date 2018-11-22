package com.example.user.appexample;

import android.widget.*; 
import com.example.user.appexample.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;

class ByName implements Comparator<Song>{
	@Override
    public int compare(Song o1 , Song o2) {
    	if( o1.getName() == o2.getName() )
            return 0;
        if( o1.getName() == null )
            return 1;
        if( o2.getName() == null )
            return -1;
        return o1.getName().compareTo( o2.getName() );
    }	   
}

class ByPath implements Comparator<Song>{
	@Override
    public int compare(Song o1 , Song o2) {
    	if( o1.getPath() == o2.getPath() )
            return 0;
        if( o1.getPath() == null )
            return 1;
        if( o2.getPath() == null )
            return -1;
        return o1.getPath().compareTo( o2.getPath() );
    }	   
}

class ByEditDate implements Comparator<Song>{
	@Override
    public int compare(Song o1 , Song o2) {
    	return o1.getEditDate().compareTo( o2.getEditDate());
    }	   
}

class ByDuration implements Comparator<Song>{
	@Override
    public int compare(Song o1 , Song o2) {
    	return o1.getDuration() - o2.getDuration();
    }	   
}