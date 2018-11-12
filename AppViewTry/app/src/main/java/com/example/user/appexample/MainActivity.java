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
	private MusicPlayerView mediaPlayer;
	private TextView tx;
	private String path = "/storage/emulated/0/Download/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		mediaPlayer = (MusicPlayerView)findViewById(R.id.music);
		mediaPlayer.setAbsolutePath(path , 0);

	}


	public void onClick(View view){
		
	
	}
}
