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

import java.util.Collections;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;

public class MainActivity extends AppCompatActivity {
	private MusicPlayerView mediaPlayer;
	private TextView tx;
	private String path = "/storage/emulated/0/Download/";
	ArrayList<Song> si = new ArrayList<>();
	private MediaPlayer mediaPrepare = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		mediaPlayer = (MusicPlayerView)findViewById(R.id.music);
		si.addAll(findSingleAreaSongs(path));
		Collections.sort(si , new ByPath());
		mediaPlayer.setListData(si);
	}

	public ArrayList<Song> findSingleAreaSongs(String Path) {
		ArrayList<Song> songs = new ArrayList<>();
		try{
		   File rootFolder = new File(Path);
		   File[] files = rootFolder.listFiles(); 
		   	for (File file : files) {
				if (file.isDirectory());
				else if (file.getName().endsWith(".mp3")) {
					setMediaPrepareSource(file.getAbsolutePath());
					String name = file.getName();
					String path = file.getAbsolutePath();
					Long editDate = file.lastModified();
					int duration = mediaPrepare.getDuration();
					songs.add(new Song(name , path , editDate , duration));			 	
				 }
			}
			return songs;
		}
		catch(Exception e){			
			return songs;
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
	
}