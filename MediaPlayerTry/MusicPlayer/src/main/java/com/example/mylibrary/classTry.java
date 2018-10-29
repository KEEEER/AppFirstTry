package com.example.mylibrary;

import android.media.MediaPlayer;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import android.view.View;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import android.widget.SeekBar;

import android.content.DialogInterface.OnClickListener;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import java.io.IOException;

import java.io.File;


public class classTry extends AppCompatActivity {
	private int timeInSecond; 
	private String timeStack;
	
	private boolean playORpause = true;
	
	private SeekBar seekbar;
	private ListView listview;
	private ArrayAdapter adapter;
	private TextView textView;
	private TextView songTextView;
	private Button bplay,bstop;
	private Handler handler=new Handler();
	
    private MediaPlayer mp = new MediaPlayer();
	
	private ArrayList<String>  mp3_AL = new ArrayList<String>(findSongs("/storage/emulated/0/Download/"));
	private String strSource;
	private String[] strOrg = {"新北市","台北市","台中市","台南市","高雄市"};
	private String[] str = {"新北市","台北市","台中市","台南市","高雄市"};
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new);
		textView = findViewById(R.id.textView);
		songTextView = findViewById(R.id.songTextView);
		listview = (ListView) findViewById(R.id.listview);		
		bplay = (Button)findViewById(R.id.play);
        bstop = (Button)findViewById(R.id.stop);		
		seekbar = (SeekBar)findViewById(R.id.seek);
		
		adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,mp3_AL);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(onClickListView);
		seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override 
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if (fromUser){
					textView.setText(String.valueOf(progress));
					mp.seekTo(progress);
				}
			} 
			@Override 
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}
			@Override 
			public void onStopTrackingTouch(SeekBar seekBar) { 

			} 
		});
	}
	
    Runnable start=new Runnable(){
	
        @Override
        public void run() {
			timeInSecond = mp.getCurrentPosition() / 1000;
			timeStack = String.valueOf(timeInSecond / 60) + ":" + String.valueOf(timeInSecond % 60);
			textView.setText(timeStack);
			seekbar.setProgress(mp.getCurrentPosition());
            handler.postDelayed(start, 10);
			if(!(mp.isPlaying())){
				
				bplay.setText("RePlay?");
				playORpause = true;
			}
        }
  	
    };

	ArrayList<String> findSongs(String Path) {
		ArrayList<String> fileList = new ArrayList<>();
			try{
			   File rootFolder = new File(Path);
			   File[] files = rootFolder.listFiles(); 
			   for (File file : files) {
				 if (file.isDirectory()) {
					   if (findSongs(file.getAbsolutePath()) != null) {
							fileList.addAll(findSongs(file.getAbsolutePath()));
						} else {
							break;
						}
				 } else if (file.getName().endsWith(".mp3")) {
					 fileList.add(file.getAbsolutePath());
				 }
			}
			return fileList;
			}catch(Exception e){
			   return null;
			}
	}
	private AdapterView.OnItemClickListener onClickListView = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
			
			textView.setText(mp3_AL.get(position));
			strSource = mp3_AL.get(position);
			songTextView.setText(strSource);
			listview.setAdapter(adapter);
		}};
    public void onClick(View view){
		String s = getResources().getResourceEntryName(view.getId());
		
		switch(s){
			case "set":
				textView.setText("Button Set!");
				try {
					mp.setDataSource(strSource);
					mp.prepare();
					seekbar.setMax(mp.getDuration());
					
				}catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
				break;
			case "play":
				if(playORpause){
					handler.post(start);
					mp.start();
					textView.setText(String.valueOf(mp. getCurrentPosition()));
					playORpause = false;
					bplay.setText("Pause!");
				}
				else{
					if(mp != null){
						mp.pause();
						handler.removeCallbacks(start);
					}
					bplay.setText("Play!");
					playORpause = true;
				}
				
			//	mp.seekTo(1000);
				break;
			case "pause":
				
				break;
			case "stop":
			//	textView.setText("Button Stop!");
				if(mp != null){					
					mp.pause();
					mp.seekTo(0);
					seekbar.setProgress(0);
					handler.removeCallbacks(start);
					playORpause = true;
					bplay.setText("Play!");
				}
				break;
		}
	}
}

