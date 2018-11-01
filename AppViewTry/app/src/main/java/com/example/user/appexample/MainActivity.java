package com.example.user.appexample;

import com.example.user.appexample.*;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.graphics.Color;

import android.widget.Toast; 

import android.content.Context;
import android.os.Build;

import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;



public class MainActivity extends AppCompatActivity {
	private Context context;
	private MusicPlayerBackGroundView backGround;
	private MusicPlayerPlayPauseView play;
	int posX;
	int posY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
		context=this;
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		backGround = (MusicPlayerBackGroundView) findViewById(R.id.BackGroundTest);
		play = findViewById(R.id.PlayPause);
		posX = backGround.getStartX() + (int)(backGround.getBackGroundWidth() * 0.8f);
		posY = backGround.getStartY() + (int)(backGround.getBackGroundHeight() * 0.8f);
	    play.setXY(posX , posY);
		
	}


	public void onClick(View view){
		
		//play.setRadius(1000);
	
	}
}
