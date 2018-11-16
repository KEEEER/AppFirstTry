package com.example.user.appexample;
import android.graphics.Canvas;
import android.content.Context;
import android.widget.Toast;
import java.util.Random;
class ModeSelect {
    public boolean RandomLoop = false;
    public boolean SingleLoop = false;
    public boolean ListLoop = true;

    public void modeToSingleLoop(){
        if(SingleLoop) modeToListLoop();
        else{
            RandomLoop  = false;
            ListLoop = false;
            SingleLoop = true;
        }
    }
    public void modeToRandom(){
        if(RandomLoop) modeToListLoop();
        else{
            RandomLoop = true;
            ListLoop = false;
            SingleLoop = false; 
        }
        
    }
    public void modeToListLoop(){
        RandomLoop = false;
        ListLoop = true;
        SingleLoop = false;  
    }

    public int determineNextSong(int songNumbers , int songCounts){
        
        int nextSong = songNumbers;
        if(SingleLoop);
        else if(RandomLoop){
            Random ran = new Random();
            int songTemp = songNumbers;
            while(nextSong == songTemp){
                nextSong = ran.nextInt(songCounts);
            }
        }
        else{
            nextSong++;
            if(nextSong == songCounts) nextSong = 0;
        }
        return nextSong;
    }
}