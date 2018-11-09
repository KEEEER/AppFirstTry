package com.example.user.appexample;
import android.net.Uri ;
import android.content.Context;

import android.graphics.Canvas;

import android.os.Build;

import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import android.util.AttributeSet;

import android.view.View;
import android.widget.ImageView; 

 
public class MusicPlayerImageButton extends ImageView{

    public MusicPlayerImageButton(Context context) {
        super(context);
		
    }
	/*public MusicPlayerImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
		Uri otherPath = Uri.parse("android.resource://"+context.getPackageName()+"/drawable/play");
		setImageURI(otherPath);
    }

    public MusicPlayerImageButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }*/
	
}