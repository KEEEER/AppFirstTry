package com.example.user.appexample;

import com.example.user.appexample.*;
import android.content.Context;
import android.net.Uri;
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
import android.widget.Toast;
import android.media.MediaMetadataRetriever;
import android.view.View;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import java.lang.Runnable;

public class MainActivity extends AppCompatActivity {
	private MusicPlayerView mediaPlayer;
	private TextView tx;
	private TextView tx1;
	private ListView lv;

	private String path1="http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4";
	private String path = "/storage/emulated/0/Download/";
	private String pathTest = "/storage/emulated/0/Download/music01.mp3";
	private String str = "http://poisondog.updog.co/tmp/Giuni%20Russo%20Johnny%20Guitar%20Live.mp3";
	private String str1 = "http://poisondog.updog.co/tmp/Electric_Light_Orchestra_Mr_Blue_Sky.mp3";
	private String str2 = "https://www.hrupin.com/wp-content/uploads/mp3/testsong_20_sec.mp3";
	private String str3 = "http://searchgurbani.com/audio/sggs/1.mp3";
	private ArrayList<Song> songs = new ArrayList<>();
	private FormatTransformer formatTransformer = new FormatTransformer();
	private Handler handler = new Handler();
	private ArrayList<String> info = new ArrayList<>();
	private ArrayAdapter adapter;
//	private VideoView mVideoView;
//	private MediaController mMediaController;
	MediaPlayer mps = new MediaPlayer();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	//	MP3MetadataLoader mpl = new MP3MetadataLoader(pathTest);
		MP3MetadataLoader mpll = new MP3MetadataLoader(str3);		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	/*	tx = findViewById(R.id.nameTextView);
		lv = findViewById(R.id.songListView1);
		tx.setText(String.valueOf(mpl.getDuration()));

		tx1 = findViewById(R.id.nameTextView1);
		tx1.setText(mpll.getDuration());
		
		adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1);
		adapter.addAll(mpl.getAll());
		lv.setAdapter(adapter);*/
		mediaPlayer = (MusicPlayerView)findViewById(R.id.music);
		songs.addAll(formatTransformer.FindInFolder(path));	
		songs.add(formatTransformer.PathToSong(pathTest));
		songs.add(formatTransformer.URLToSong(str));
		songs.add(mpll.getSong());
		Collections.sort(songs , new ByDuration());
		mediaPlayer.setList(songs);

	}		
}

