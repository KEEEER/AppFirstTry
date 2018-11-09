package com.example.user.appexample;
import android.net.Uri ;
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
import android.net.Uri;

import java.io.IOException;
import java.io.File;
import java.text.SimpleDateFormat; 
import java.util.Date; 
import java.util.ArrayList;
import java.util.Random;

 //RelativeLayout
public class MusicPlayerView extends LinearLayout{
	private int timeInSecond = 0; 
	private int songNumber = 0;
	private int songCounts = 0;
	private int currentProgress = 0;
	private int mode = 0; // normal(0) , recycle(1) , random(2)

	private String timeStack;
	private String strSource = "NODATA";
	private String strName = "Pick one!";
	private String strInfo = "Pick one!";

	private boolean isLoop = false;
	private boolean isRandom = false;

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

	private Button play;
	private SeekBar seek;

	private int maxHeight = 0;
	private int maxWidth = 0;

	private String status = "play";
	private String timer;

	private byte[] data; //Cover data

	Random ran = new Random();
	private MediaPlayer mp = new MediaPlayer();
	private SimpleDateFormat timeFormat = new SimpleDateFormat(" mm:ss");
	private Date date = new Date();
	private Handler handler=new Handler();
	private ArrayList<String> mp3_files;
	private ArrayList<String> fileNames;
	private MediaMetadataRetriever retriever = new MediaMetadataRetriever();
	
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

		fileNames = new ArrayList<>();
		mp3_files = new ArrayList<String>(findSongs("/storage/emulated/0/Download/"));
		

		songListView = (ListView)findViewById(R.id.songListView);
		adapter = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,mp3_files);
		songListView.setAdapter(adapter);
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

        setTextView();

        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
			@Override 
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if (fromUser){
					currentProgress = progress;				
				}
			} 
			@Override 
			public void onStartTrackingTouch(SeekBar seekBar) {
				handler.removeCallbacks(statusReflesher);
			}
			@Override 
			public void onStopTrackingTouch(SeekBar seekBar) { 
				mp.seekTo(currentProgress);
				handler.post(statusReflesher);
			} 
		});

    //    handler.post(viewSizeRefresher);
    }
	public MusicPlayerView(Context context,AttributeSet attrs,int defStyleAttr){
		super(context, attrs , defStyleAttr);
		initLayout();
	}
	private void initLayout(){
    	View view = inflate(getContext(), R.layout.music_view_layout, null);
    	
        addView(view);
    }
	public int dip2px(float dpValue) {
		final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

  	ArrayList<String> findSongs(String Path) {
		ArrayList<String> fileList = new ArrayList<>();
			try{
			   File rootFolder = new File(Path);
			   File[] files = rootFolder.listFiles(); 
			   	for (File file : files) {
					if (file.isDirectory()) {
					   if (findSongs(file.getAbsolutePath()) != null) {
							fileList.addAll(findSongs(file.getAbsolutePath()));
						} 
						else {
							break;
						}
					 } 
					 else if (file.getName().endsWith(".mp3")) {
						fileList.add(file.getAbsolutePath());
					 	fileNames.add(file.getName());
					 }
				}
				songCounts = fileList.size();
				return fileList;
			}
			catch(Exception e){
				songCounts = fileList.size();
				songInfoText.setText("CryCry");
			   	return fileList;
			}
	}
    private Runnable statusReflesher = new Runnable(){	
        @Override
        public void run() {
			setCurrentTime(mp.getCurrentPosition());
			setCurrentSeek(mp.getCurrentPosition());
			handler.postDelayed(statusReflesher , 10);
			if(isSongGoingToChange()){
				setNextSongInfo();		// songNumber determine
				setSong();				// set song to MediaPlayer
				setPlayImageStatus();
				setSongStatus();
			}
        }
    };

    private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        	songNumber = position;
			setSong();
			setPlayImageStatus();
	  		setSongStatus();
		}
	};
    private ImageView.OnClickListener playListener = new ImageView.OnClickListener() {
	  	@Override
	  	public void onClick(View v) {	
	  		setPlayImageStatus();
	  		setSongStatus();
	  	}
	};
	private ImageView.OnClickListener stopListener = new ImageView.OnClickListener() {
	  	@Override
	  	public void onClick(View v) {
	  		mp.pause();
	  		resetView();						
  			setPlayImageStatus();
	  		setSongStatus();
	  	}
	};
	private ImageView.OnClickListener setListener = new ImageView.OnClickListener() {
	  	@Override
	  	public void onClick(View v) {
	 		setSong();	
	 		setPlayImageStatus();
	  		setSongStatus();
	  	}
	};
	private ImageView.OnClickListener loopListener = new ImageView.OnClickListener() {
	  	@Override
	  	public void onClick(View v) {
			if(!isLoop){
				isLoop = true;
				isRandom = false;
				songModeText.setText("MODE : 1");
			}
			else {
				isLoop = false;
				if(isRandom) songModeText.setText("MODE : 2");
				else songModeText.setText("MODE : 0");
			}		
	  	}
	};
	private ImageView.OnClickListener randomListener = new ImageView.OnClickListener() {
	  	@Override
	  	public void onClick(View v) {
	 		if(!isRandom){
	 			isRandom = true;
	 			isLoop = false;
	 			songModeText.setText("MODE : 2");
	 		}
	 		else{
	 			isRandom = false;
	 			if(isLoop) songModeText.setText("MODE : 1");
	 			else songModeText.setText("MODE : 0");
	 		}
	  	}
	};
	private void setPlayImageStatus(){
		if(mp.isPlaying()){
  			playImageView.setImageResource(R.drawable.play);  			
  		}
  		else{
  			playImageView.setImageResource(R.drawable.pause);
  		}
	}
	private void setSongStatus(){
		if(mp.isPlaying()){
			mp.pause();
			handler.removeCallbacks(statusReflesher);	
  		}
  		else{
  			mp.start();
  			handler.post(statusReflesher);  	
  			
  		}
	}
	private void setSong(){

 		beforeSet();	//MediaPlayer reset
 		
 		setSongRetriever();			//Set song info taker
 		strName = getSongName();	//Get song name from retriever
	 	strInfo = getSongInfo();	//Get song info from retriever
		setTextView();				//Set name and info
	 	
	 	data = getPicture();	//Get cover image from retriever
	 	setCover(data);			//Set cover

		strSource = mp3_files.get(songNumber);	//Set which song is choosen from the song list
		setMediaPlayerSource(strSource);		//Set resource to MediaPlayer

		seek.setMax(mp.getDuration());	
		seek.setProgress(0);
		initTime();		//Reset the time
	}
	private void resetView(){
	//	mp.pause();
		mp.seekTo(0); 
		seek.setProgress(0);
		initTime();		
	}
	private boolean isSongGoingToChange(){
		return (mp.getDuration() <= mp.getCurrentPosition());
	}
	private byte[] getPicture(){
		return retriever.getEmbeddedPicture();
	}
	private String getSongName(){
		return fileNames.get(songNumber);
	}
	private String getSongInfo(){
		return retriever.extractMetadata(2);	
	}
	private void setCurrentSeek(int position){
		seek.setProgress(position);
	}
	private void setSongName(String name){
		strName = name;
	}
	private void setSongInfo(String info){
		strInfo = info;	
	}
	private void setSongRetriever(){
		retriever.setDataSource(strSource);	
	}
	private void setCurrentTime(long time){
		date.setTime(time);
		timer = timeFormat.format(date);
		songTimeText.setText(timer);
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
	private void beforeSet(){
		mp.reset();
 		if(strSource == "NODATA"){
 			strSource = mp3_files.get(0);
 		}
 	//	setPlayImageStatus();
	}
	private void setMediaPlayerSource(String sourceStr){
		try {
 			mp.setDataSource(sourceStr);
 			mp.prepare();
		}catch (IllegalArgumentException e) {	
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }catch(IOException e){
    		e.printStackTrace();
   		}
	}
	private void setCover(byte[] data){
		if(data != null){
			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			coverImage.setImageBitmap(bitmap);
		} 	
		else {
			coverImage.setImageResource(R.drawable.android);
		}
	}
	private void setNextSongInfo(){
		if(isLoop){

		}
		else if(isRandom){
			int songTemp = songNumber;
			while(songNumber == songTemp){
				songNumber = ran.nextInt(songCounts);
			}
			
		}
		else{
			songNumber++;
			if(songNumber == songCounts) songNumber = 0;
		}
	}
}
