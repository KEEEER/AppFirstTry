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
import java.util.Collections;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.Map;

 //RelativeLayout
public class MusicPlayerView extends LinearLayout{
	private Toast toast;
	private static int songNumber = 0;
	private static int songCounts = 0;
	private static int currentProgress = 0;

	private static String strSource = "NODATA";
	private static String strName = "Pick one!";
	private static String strInfo = "Pick one!";

	private static boolean hasBeenCreated = false;
	private static boolean isLoop = false;
	private static boolean isRandom = false;
	private static boolean sortByTime = false;
	private static boolean sortByName = false;
	
	private ImageView coverImage;
	private ImageView playImageView;
	private ImageView stopImageView;
	private ImageView setImageView;
	private ImageView loopImageView;
	private ImageView randomImageView;

	private Button sortTimeButton;
	private Button sortNameButton;

	private TextView songNameText;
	private TextView songInfoText;
	private TextView songTimeText;
	private TextView songModeText;

	private ArrayAdapter adapter;
	private ListView songListView;

	private Button play;
	private SeekBar seek;

	private String timer;

	private static byte[] data; //Cover data

	Random ran = new Random();
	private static MediaPlayer mp = new MediaPlayer();
	private MediaPlayer mediaPrepare = new MediaPlayer();

	private SimpleDateFormat timeFormat = new SimpleDateFormat(" mm:ss");
	private Date date = new Date();
	private Handler handler = new Handler();
	private ArrayList<String> fileNames = new ArrayList<>();
	private ArrayList<String> filePath = new ArrayList<String>();
	private ArrayList<Integer> fileTime = new ArrayList<>();

	private static MediaMetadataRetriever retriever = new MediaMetadataRetriever();
	
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

        sortTimeButton = (Button)findViewById(R.id.SortByTime);
		sortNameButton = (Button)findViewById(R.id.SortByName);
        
        songNameText = (TextView)findViewById(R.id.nameTextView);
        songInfoText = (TextView)findViewById(R.id.infoTextView);
		songTimeText = (TextView)findViewById(R.id.timeTextView);        
		songModeText = (TextView)findViewById(R.id.modeTextView);

		
		
		
		//findSingleAreaSongs("/storage/emulated/0/Download/")

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
        sortTimeButton.setOnClickListener(sortTimeListener);
        sortNameButton.setOnClickListener(sortNameListener);

        setTextView();
        if(hasBeenCreated){
        	setLastCurrentStatus();
			seek.setMax(mp.getDuration());
        }
		
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
			@Override 
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if (fromUser && !fileNames.isEmpty()){
					currentProgress = progress;				
				}
			} 
			@Override 
			public void onStartTrackingTouch(SeekBar seekBar) {
				if(!fileNames.isEmpty())
					handler.removeCallbacks(statusReflesher);
			}
			@Override 
			public void onStopTrackingTouch(SeekBar seekBar) { 
				if(!fileNames.isEmpty()){
					mp.seekTo(currentProgress);
					handler.post(statusReflesher);	
				}
				
			} 
		});       
    //    handler.post(viewSizeRefresher);
    }

    @Override
    protected void onDetachedFromWindow(){
    	super.onDetachedFromWindow();
    	handler.removeCallbacks(statusReflesher);
    	hasBeenCreated = true;
    	toast = Toast.makeText(getContext() , "Hello!" , Toast.LENGTH_SHORT);
		toast.show();
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
    private ArrayList<String> findUnderAreaSongs(String Path) {
		ArrayList<String> fileList = new ArrayList<>();
			try{
			   File rootFolder = new File(Path);
			   File[] files = rootFolder.listFiles(); 
			   	for (File file : files) {
					if (file.isDirectory()) {
					   if (findSingleAreaSongs(file.getAbsolutePath()) != null) {
							fileList.addAll(findSingleAreaSongs(file.getAbsolutePath()));
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
  	private ArrayList<String> findSingleAreaSongs(String Path) {
		ArrayList<String> fileList = new ArrayList<>();
		try{
		   File rootFolder = new File(Path);
		   File[] files = rootFolder.listFiles(); 
		   	for (File file : files) {
				if (file.isDirectory());
				else if (file.getName().endsWith(".mp3")) {
					setMediaPrepareSource(file.getAbsolutePath());
					fileList.add(file.getAbsolutePath());
				 	fileNames.add(file.getName() + getFormatTime(mediaPrepare.getDuration()));
				 	fileTime.add(mediaPrepare.getDuration());
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
			//	setPlayImageStatus();
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
	  		if(!fileNames.isEmpty()){
		  		setPlayImageStatus();
		  		setSongStatus();
		 	}
	  	}
	};
	private ImageView.OnClickListener stopListener = new ImageView.OnClickListener() {
	  	@Override
	  	public void onClick(View v) {
	  		if(!fileNames.isEmpty()){
	  			mp.pause();
		  		resetView();						
	  			setPlayImageStatus();
		  		setSongStatus();
		  	}
		}
	};
	private ImageView.OnClickListener setListener = new ImageView.OnClickListener() {
	  	@Override
	  	public void onClick(View v) {
	  		if(!fileNames.isEmpty()){
		 		setSong();	
		 		setPlayImageStatus();
		  		setSongStatus();
		  	}
	  	}
	};
	private ImageView.OnClickListener loopListener = new ImageView.OnClickListener() {
	  	@Override
	  	public void onClick(View v) {
			if(!isLoop){
				isLoop = true;
				isRandom = false;
				songModeText.setText("MODE : 1   ");
			}
			else {
				isLoop = false;
				if(isRandom) songModeText.setText("MODE : 2   ");
				else songModeText.setText("MODE : 0   ");
			}		
	  	}
	};
	private ImageView.OnClickListener randomListener = new ImageView.OnClickListener() {
	  	@Override
	  	public void onClick(View v) {
	 		if(!isRandom){
	 			isRandom = true;
	 			isLoop = false;
	 			songModeText.setText("MODE : 2   ");
	 		}
	 		else{
	 			isRandom = false;
	 			if(isLoop) songModeText.setText("MODE : 1   ");
	 			else songModeText.setText("MODE : 0   ");
	 		}
	  	}
	};
	private Button.OnClickListener sortTimeListener = new Button.OnClickListener(){
		@Override
	  	public void onClick(View v) {
	  		sortByTime = true;
			sortByName = false;
	  		sortListByTime();
	  	}
	};
	private Button.OnClickListener sortNameListener = new Button.OnClickListener(){
		@Override
	  	public void onClick(View v) {
	  		sortByTime = false;
			sortByName = true;
	  		sortListByName();
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
	private void setHandler(boolean status){
		if(status)
			handler.post(statusReflesher);
		else
			handler.removeCallbacks(statusReflesher);
	}
	private void setSong(){

 		beforeSet();	//MediaPlayer reset
 		
 		strSource = filePath.get(songNumber);	//Set which song is choosen from the song list

 		setSongRetriever();			//Set song info taker
 		strName = getSongName();	//Get song name from retriever
	 	strInfo = getSongInfo();	//Get song info from retriever

	
		setTextView();				//Set name and info
	 	
	 	data = getPicture();	//Get cover image from retriever
	 	setCover(data);			//Set cover

		
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
	private void setSongNumber(int number){
		songNumber = number;
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
	private void setCurrentTime(int time){
		date.setTime(time);
		timer = timeFormat.format(date);
		songTimeText.setText(timer);
	}
	private String getFormatTime(int time){
		String t;
		date.setTime(time);
		t = timeFormat.format(date);
		return t; 
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
 			strSource = filePath.get(0);
 		}
 	//	setPlayImageStatus();
	}
	private void setMediaPlayerSource(String source){
		try {
			mp.reset();
 			mp.setDataSource(source);
 			mp.prepare();
		}catch (IllegalArgumentException e) {	
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }catch(IOException e){
    		e.printStackTrace();
   		}
	}
	private void setMediaPrepareSource(String source){
		try {
			mediaPrepare.reset();
 			mediaPrepare.setDataSource(source);
 			mediaPrepare.prepare();
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
	private void setLastCurrentStatus(){
		if(mp.isPlaying()){
			playImageView.setImageResource(R.drawable.pause);
			setCover(data);
			setHandler(true);
		}
		else {
			setCover(data);
		//	setHandler(false);
		}	
	}
	private void sortListByTime(){
		
		ArrayList<Integer> arrTime = new ArrayList<>();
  		ArrayList<String> arrPath = new ArrayList<>();
  		ArrayList<String> arrNames = new ArrayList<>();
  		Map<Integer , Integer> sortTimeMap = new TreeMap<Integer , Integer>();

  		for(int i = 0 ; i < songCounts ; i++){
  			if(i == 0)
  				sortTimeMap.put(fileTime.get(i) , i);
  			else {
  				while(sortTimeMap.containsKey(fileTime.get(i))){
  					int temp = fileTime.get(i);
  					temp++;
  					fileTime.set(i,temp);
  				}
  				sortTimeMap.put(fileTime.get(i) , i);
  			}
  		}
  		for (Map.Entry<Integer, Integer> entry : sortTimeMap.entrySet()) {
  			arrTime.add(fileTime.get(entry.getValue()));
  			arrPath.add(filePath.get(entry.getValue()));
  			arrNames.add(fileNames.get(entry.getValue()));
        }
        fileTime.clear();
        filePath.clear();
        fileNames.clear();

        fileTime.addAll(arrTime);
        filePath.addAll(arrPath);
        fileNames.addAll(arrNames);

        setSongNumber(fileNames.indexOf(strName));
        adapter = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,fileNames);
		songListView.setAdapter(adapter);
	}
	private void sortListByName(){

		ArrayList<Integer> arrTime = new ArrayList<>();
  		ArrayList<String> arrPath = new ArrayList<>();
  		ArrayList<String> arrNames = new ArrayList<>();
  		Map<String , Integer> sortNameMap = new TreeMap<String , Integer>();

  		for(int i = 0 ; i < songCounts ; i++){
  			
  				sortNameMap.put(fileNames.get(i) , i);
  			
  		}
  		for (Map.Entry<String, Integer> entry : sortNameMap.entrySet()) {
  			arrTime.add(fileTime.get(entry.getValue()));
  			arrPath.add(filePath.get(entry.getValue()));
  			arrNames.add(fileNames.get(entry.getValue()));
        }
        fileTime.clear();
        filePath.clear();
        fileNames.clear();

        fileTime.addAll(arrTime);
        filePath.addAll(arrPath);
        fileNames.addAll(arrNames);

;
        adapter = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,fileNames);
		songListView.setAdapter(adapter);
	}
	// PUBLIC METHOD **********************************************************************//
	public void setAbsolutePath(String path , int mode){
		
		/********************************* 
		
			mode 0 : findSingleAreaSongs
			mode 1 : findUnderAreaSongs
		
		*********************************/
		switch(mode) {
			case 0:
				fileTime.clear();
      		  	filePath.clear();
       			fileNames.clear();
				filePath.addAll(findSingleAreaSongs(path));
				if(sortByTime){
					sortListByTime();
					toast = Toast.makeText(getContext() , strName , Toast.LENGTH_SHORT);
					toast.show();
				} 
				if(sortByName){
					sortListByName();
					toast = Toast.makeText(getContext() , strName , Toast.LENGTH_SHORT);
					toast.show();
				} 
				adapter = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,fileNames);
				songListView.setAdapter(adapter);
				break;
			case 1:
				findUnderAreaSongs(path);
				break;
		}
			
	}


}
