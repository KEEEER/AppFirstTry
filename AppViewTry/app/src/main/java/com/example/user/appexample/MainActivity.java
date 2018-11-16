package com.example.user.appexample;

import com.example.user.appexample.*;

import android.support.v7.app.AppCompatActivity;
import android.widget.*; 
import android.content.Context;
import android.os.Bundle;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View.*;
import android.media.MediaPlayer;
import android.content.Context;
import android.media.AudioManager;

import java.util.Collections;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;

public class MainActivity extends AppCompatActivity {
	private MusicPlayerView mediaPlayer;
	private TextView tx;
	private String path1="http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4";
	private String path = "/storage/emulated/0/Download/";
	private String pathTest = "/storage/emulated/0/Download/music01.mp3";
	private String str = "http://poisondog.updog.co/tmp/Giuni%20Russo%20Johnny%20Guitar%20Live.mp3";
	private ArrayList<Song> songs = new ArrayList<>();
	private FormatTransformer formatTransformer = new FormatTransformer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    /*    MediaPlayer mps = new MediaPlayer();
        mps.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
 			mps.setDataSource(pathTest);
 			mps.prepare();
 			mps.start();
        }catch(Exception e){

   		}*/
		mediaPlayer = (MusicPlayerView)findViewById(R.id.music);
		songs.addAll(formatTransformer.FindInFolder(path));	
		songs.add(formatTransformer.PathToSong(pathTest));
		songs.add(formatTransformer.URLToSong(str));
		Collections.sort(songs , new ByDuration());
		mediaPlayer.setList(songs);

	}		
}
