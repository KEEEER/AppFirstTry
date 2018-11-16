package com.example.user.appexample;

import android.net.Uri ;
import android.widget.*; 
import com.example.user.appexample.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import java.io.IOException;
import java.io.File;
import android.content.Context;
import java.util.HashMap;
import android.media.AudioManager;
import android.view.View;

class FormatTransformer {
    private MediaMetadataRetriever retriever = new MediaMetadataRetriever();
    private MediaPlayer player = new MediaPlayer();
    public Song PathToSong(String str){
        return getInfo(str);
    }
    public Song URLToSong(String str){
        return getUrlInfo(str);
    }

    public ArrayList<Song> FilePathToSong(ArrayList<String> path){
        ArrayList<Song> info = new ArrayList<>();

        return info;
    }
    public ArrayList<Song> URLToSong(ArrayList<String> url){
        ArrayList<Song> info = new ArrayList<>();
        return info;
    }

    public ArrayList<Song> FindInFolder(String Path) {

        ArrayList<Song> songs = new ArrayList<>();
        try{
           File rootFolder = new File(Path);
           File[] files = rootFolder.listFiles(); 
            for (File file : files) {
                if (file.isDirectory());
                else if (file.getName().endsWith(".mp3")) {                
                    retriever.setDataSource(file.getAbsolutePath());
                    String name = file.getName();
                    String artist = retriever.extractMetadata(2);
                    String path = file.getAbsolutePath();
                    Long editDate = file.lastModified();
                    int duration = Integer.parseInt(retriever.extractMetadata(9));
                    songs.add(new Song(name , artist , path , editDate , duration));             
                 }
            }
            return songs;
        }
        catch(Exception e){         
            return songs;
        }
    }

    private Song getInfo(String str){
        File file = new File(str);
        retriever.setDataSource(file.getAbsolutePath());
        String name = file.getName();
        String artist = retriever.extractMetadata(2);
        String path = file.getAbsolutePath();
        Long editDate = file.lastModified();
        int duration = Integer.parseInt(retriever.extractMetadata(9));
        Song info = new Song(name , artist , path , editDate , duration);
        return info;
    }
    private Song getUrlInfo(String str){
        File file = new File(str);
        retriever.setDataSource( str , new HashMap<String, String>());
        String name = file.getName();
        String artist = retriever.extractMetadata(2);
        String path = file.getAbsolutePath();
        Long editDate = file.lastModified();
        int duration = Integer.parseInt(retriever.extractMetadata(9));
        Song info = new Song(name , artist , path , editDate , duration);
        return info;
    }
    private int extractDuration(String str){
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            player.setDataSource(str);
            player.prepare();
            return player.getDuration();
        }catch(Exception e){

        }
        return 0;
    }
 /*   private ArrayList<String> FindUnderFolder(String Path) {
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
                return fileList;
            }
            catch(Exception e){
                return fileList;
            }
    }*/

}