package com.example.user.appexample;

import android.widget.*; 
import com.example.user.appexample.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;

class ByName implements Comparator<Song>{
	@Override
    public int compare(Song o1 , Song o2) {
    	if( o1.Name == o2.Name )
            return 0;
        if( o1.Name == null )
            return 1;
        if( o2.Name == null )
            return -1;
        return o1.Name.compareTo( o2.Name );
    }	   
}

class ByPath implements Comparator<Song>{
	@Override
    public int compare(Song o1 , Song o2) {
    	if( o1.Path == o2.Path )
            return 0;
        if( o1.Path == null )
            return 1;
        if( o2.Path == null )
            return -1;
        return o1.Path.compareTo( o2.Path );
    }	   
}

class ByEditDate implements Comparator<Song>{
	@Override
    public int compare(Song o1 , Song o2) {
    	return o1.EditDate.compareTo( o2.EditDate );
    }	   
}

class ByDuration implements Comparator<Song>{
	@Override
    public int compare(Song o1 , Song o2) {
    	return o1.Duration - o2.Duration;
    }	   
}