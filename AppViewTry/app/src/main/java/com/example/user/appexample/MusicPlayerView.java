package com.example.user.appexample;
import android.net.Uri;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.widget.*;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater; 
import android.content.Context;
import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaMetadataRetriever;
import android.media.AudioManager;

import java.io.IOException;
import java.io.File;
import java.text.SimpleDateFormat; 
import java.util.Date; 
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.Map;

public class MusicPlayerView extends LinearLayout{
	private static int songNumber = 0;
	private static int songCounts = 0;
	private static int currentProgress = 0;
	private static String strSource = "NODATA";
	private static String strName = "Pick one!";
	private static String strInfo = "Pick one!";
	private static boolean hasBeenCreated = false;
	private String timer;

	private ImageView coverImage;
	private ImageView playImageView;
	private ImageView stopImageView;
	private ImageView setImageView;
	private ImageView loopImageView;
	private ImageView randomImageView;
	private TextView songNameText;
	private TextView songInfoText;
	private TextView songTimeText;
	private TextView songModeText;
	private ArrayAdapter adapter;
	private ListView songListView;
	private SeekBar seek;
	private VideoView video;
	private static byte[] data; //Cover data
	
	Random ran = new Random();
	private static MediaMetadataRetriever retriever = new MediaMetadataRetriever();
	private static ModeSelect mode = new ModeSelect();
	private static MediaPlayer mp = new MediaPlayer();

	private SimpleDateFormat timeFormat = new SimpleDateFormat(" mm:ss");
	private ArrayList<Song> songInfo = new ArrayList<>();

	private Handler handler = new Handler();
	private Date date = new Date();
	
	public MusicPlayerView(Context context) {
		super(context);
		initLayout();
	}
	public MusicPlayerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initLayout();
		setImageView = (ImageView)findViewById(R.id.Iset);
		playImageView = (ImageView)findViewById(R.id.Iplay);
		stopImageView = (ImageView)findViewById(R.id.Istop);
		coverImage = (ImageView)findViewById(R.id.Icover);
		loopImageView = (ImageView)findViewById(R.id.Iloop);
		randomImageView = (ImageView)findViewById(R.id.Irandom);
		songNameText = (TextView)findViewById(R.id.nameTextView);
		songInfoText = (TextView)findViewById(R.id.infoTextView);
		songTimeText = (TextView)findViewById(R.id.timeTextView);
		songModeText = (TextView)findViewById(R.id.modeTextView);
		songListView = (ListView)findViewById(R.id.songListView);	
		seek = (SeekBar)findViewById(R.id.seek);

		setImageView.setImageResource(R.drawable.set);
		playImageView.setImageResource(R.drawable.play);
		stopImageView.setImageResource(R.drawable.stop);
		coverImage.setImageResource(R.drawable.android);
		loopImageView.setImageResource(R.drawable.loop);
		randomImageView.setImageResource(R.drawable.random);
		stopImageView.setOnClickListener(stopListener);
		playImageView.setOnClickListener(playListener);   
		setImageView.setOnClickListener(setListener);   
		loopImageView.setOnClickListener(loopListener);
		randomImageView.setOnClickListener(randomListener);
		songListView.setOnItemClickListener(onListClick);
		mp.setOnCompletionListener(onCompletion);
		setTextView();
		if(hasBeenCreated){
			setLastCurrentStatus();
			seek.setMax(mp.getDuration());
		}	
		seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
			@Override 
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if (fromUser && !songInfo.isEmpty()){
					currentProgress = progress;				
				}
			} 
			@Override 
			public void onStartTrackingTouch(SeekBar seekBar) {
				if(!songInfo.isEmpty())
					handler.removeCallbacks(statusReflesher);
			}
			@Override 
			public void onStopTrackingTouch(SeekBar seekBar) { 
				if(!songInfo.isEmpty()){
					mp.seekTo(currentProgress);
					handler.post(statusReflesher);	
				}	
			} 
		});
	}

	public MusicPlayerView(Context context,AttributeSet attrs,int defStyleAttr){
		super(context, attrs , defStyleAttr);
		initLayout();
	}
	private void initLayout(){
		View view = inflate(getContext(), R.layout.music_view_layout, null);  	
		addView(view);
	}

	@Override
	protected void onDetachedFromWindow(){
		super.onDetachedFromWindow();
		handler.removeCallbacks(statusReflesher);
		hasBeenCreated = true;
	}

	private Runnable statusReflesher = new Runnable(){	
		@Override
		public void run() {
			date.setTime(mp.getCurrentPosition());
			timer = timeFormat.format(date);
			songTimeText.setText(timer);
			seek.setProgress(mp.getCurrentPosition());
			handler.postDelayed(statusReflesher , 1000);
		}
	};
	public MediaPlayer.OnCompletionListener onCompletion = new MediaPlayer.OnCompletionListener(){
		@Override
		public void onCompletion(MediaPlayer mp) {
			songNumber = mode.determineNextSong(songNumber , songCounts);
        	loadSongResource();
        	setSongStatus();
 	   }
	};

	private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
			songNumber = position;
			loadSongResource();		
			setSongStatus();
		}
	};
	private ImageView.OnClickListener playListener = new ImageView.OnClickListener() {
	  	@Override
	  	public void onClick(View v) {	
	  		if(!songInfo.isEmpty()){		  		
		  		setSongStatus();
		 	}
	  	}
	};
	private ImageView.OnClickListener stopListener = new ImageView.OnClickListener() {
	  	@Override
	  	public void onClick(View v) {
	  		if(!songInfo.isEmpty()){
	  			mp.pause();
		  		mp.seekTo(0); 
				seek.setProgress(0);
				initTime();										
		  		setSongStatus();
		  	}
		}
	};
	private ImageView.OnClickListener setListener = new ImageView.OnClickListener() {
	  	@Override
	  	public void onClick(View v) {
	  		if(!songInfo.isEmpty()){
		 		loadSongResource();	
		  		setSongStatus();
		  	}
	  	}
	};
	private ImageView.OnClickListener loopListener = new ImageView.OnClickListener() {
	  	@Override
	  	public void onClick(View v) {
	  		
	  		mode.modeToSingleLoop();
	  	}
	};
	private ImageView.OnClickListener randomListener = new ImageView.OnClickListener() {
	  	@Override
	  	public void onClick(View v) {
	  		mode.modeToRandom();
	  	}
	};
	private void setSongStatus(){
		if(mp.isPlaying()){
			mp.pause();
			handler.removeCallbacks(statusReflesher);
			playImageView.setImageResource(R.drawable.play);	
		}
		else{
			mp.start();
			handler.post(statusReflesher);  	
			playImageView.setImageResource(R.drawable.pause);	
		}
	}
	private void loadSongResource(){
		mp.reset();
		strSource = songInfo.get(songNumber).getPath();	//Set which song is choosen from the song list		
		strName = songInfo.get(songNumber).getName();	//Get song name from retriever
		strInfo = songInfo.get(songNumber).getArtist();	//Get song info from retriever
		setMediaPlayerSource(strSource);		//Set resource to MediaPlayer
		
		seek.setMax(songInfo.get(songNumber).getDuration());	
		seek.setProgress(0);

		setTextView();				//Set name and info
	 	loadCover();			//Set cover
		initTime();		//Reset the time
	}
	private void initTime(){
		date.setTime(0);
		timer = timeFormat.format(date);
		songTimeText.setText(timer);
	}
	private void setTextView(){
		songNameText.setText("Song Name : " + strName);
		songInfoText.setText("Song Artist : " + strInfo);
	}
	private void setMediaPlayerSource(String source){
		if(source.contains("http://") || source.contains("https://") || source.contains("files://")){
			try {
				mp.setDataSource(getContext() , Uri.parse(source));
				mp.prepare();
			}catch(Exception e){

			}
		}
		else{
			try {
				mp.setDataSource(source);
				mp.prepare();
			}catch(Exception e){

			}
		}
		
	}
	private void loadCover(){
		if(data != null){
			Bitmap bitmap = BitmapFactory.decodeByteArray(songInfo.get(songNumber).getGraphic(), 0, data.length);
			coverImage.setImageBitmap(bitmap);
		} 	
		else {
			coverImage.setImageResource(R.drawable.android);
		}
	}
	private void setLastCurrentStatus(){
		if(mp.isPlaying()){
			playImageView.setImageResource(R.drawable.pause);
			loadCover();
			handler.post(statusReflesher);
		}
		else {
			loadCover();
		}	
	}
	private void FillList(ArrayList<Song> info){
		adapter = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1);
		for(Song inf : info){
			adapter.add(inf.getName());
		}
		songListView.setAdapter(adapter);
		songCounts = info.size();
	}
	// PUBLIC METHOD **********************************************************************//
	public void setAbsolutePath(String path){
	//	findSingleAreaSongs(path);
		FillList(songInfo);
		songListView.setAdapter(adapter);			
	}
	public void setList(ArrayList<Song> sis){
		songInfo.clear();
		songInfo.addAll(sis);
		FillList(sis);			
	}
	public void addSong(Song sis){
		songInfo.add(sis);
		FillList(songInfo);
	}
	public void addStreamingSong(String url){
		//	songInfo.add(new Song("haha" , url , 10000L , 200000));
			FillList(songInfo);
			songCounts++;
	}

}
