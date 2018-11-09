package com.example.user.appexample;

import com.example.user.appexample.*;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.graphics.Color;

import android.widget.*; 

import android.content.Context;
import android.os.Build;

import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.media.MediaPlayer;
import java.io.IOException;



public class MainActivity extends AppCompatActivity {
	private Context context;
	private MusicPlayerBackGroundView backGround;
	private MusicPlayerPlayPauseView play;
	private MusicPlayerView mpv;
	private MediaPlayer mp;
	private TextView tx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
	}


	public void onClick(View view){
		
	
	}
}
