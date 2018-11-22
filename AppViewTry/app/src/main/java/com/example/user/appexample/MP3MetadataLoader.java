package com.example.user.appexample;
import com.example.user.appexample.*;
import android.graphics.Canvas;
import android.content.Context;
import android.widget.Toast;
import java.util.Random;
import android.net.Uri;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import java.util.HashMap;
import android.widget.*;
import java.util.ArrayList;
import java.io.File;
class MP3MetadataLoader {
    private Long editDate;
    private byte[] graphic;
    private String dataPath;

    private String album;
    private String albumArtist;
    private String artist;
    private String author;
    private String bitrate;
    private String captureFramerate;
    private String cdTrackNumber;
    private String compailation;
    private String composer;
    private String date;
    private String discNumber;
    private String duration;
    private String genre;
    private String hasAudio;
    private String hasImage;
    private String hasVideo; 
    private String imageCount;
    private String imageHeight;
    private String imagePrimary;
    private String imageRotation;
    private String imageWidth;
    private String location;
    private String mineType;
    private String numberTracks;
    private String title;
    private String videoFarmeCount;
    private String videoHeight;
    private String videoRotation;
    private String videoWidth;
    private String writer;
    private String year;
    private String optionClosest;
    private String optionClosestSync;
    private String optionNextSync;
    private String optionPreviousSync;

    private ArrayList<String> arr = new ArrayList<>();

    public MP3MetadataLoader(String path){
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        if(path.contains("http://")){
            try{
                mmr.setDataSource(path , new HashMap());                 
            }catch(Exception e){}
        }
        else{
            try{
                mmr.setDataSource(path);                      
            }catch(Exception e){}
        }
        if(mmr != null){
            File file = new File(path);
            editDate = file.lastModified();
            setAllMetadata(mmr);     
        }  
        dataPath = path;
    }
    private void setAllMetadata(MediaMetadataRetriever mmr){
        graphic = mmr.getEmbeddedPicture();
        album = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
        albumArtist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUMARTIST);
        artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        author = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_AUTHOR);
        bitrate = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE);
        captureFramerate = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_CAPTURE_FRAMERATE);
        cdTrackNumber = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_CD_TRACK_NUMBER);
        compailation = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_COMPILATION);
        composer = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_COMPOSER);
        date = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DATE);
        discNumber = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DISC_NUMBER);
        duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        genre = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE);
        hasAudio = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_HAS_AUDIO);
        hasImage = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_HAS_IMAGE);
        hasVideo = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_HAS_VIDEO); 
        imageCount = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_IMAGE_COUNT);
        imageHeight = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_IMAGE_HEIGHT);
        imagePrimary = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_IMAGE_PRIMARY);
        imageRotation = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_IMAGE_ROTATION);
        imageWidth = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_IMAGE_WIDTH);
        location = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_LOCATION);
        mineType = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE);
        numberTracks = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_NUM_TRACKS);
        title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        videoFarmeCount = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_FRAME_COUNT);
        videoHeight = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
        videoRotation = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);
        videoWidth = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
        writer = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_WRITER);
        year = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_YEAR);
        optionClosest = mmr.extractMetadata(MediaMetadataRetriever.OPTION_CLOSEST);
        optionClosestSync = mmr.extractMetadata(MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
        optionNextSync = mmr.extractMetadata(MediaMetadataRetriever.OPTION_NEXT_SYNC);
        optionPreviousSync = mmr.extractMetadata(MediaMetadataRetriever.OPTION_PREVIOUS_SYNC);
        mmr.release();
    }
    public void loadMetadata(String path){
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        if(path.contains("http://") || path.contains("https://") || path.contains("files://")){
            try{
                mmr.setDataSource(path , new HashMap());                  
            }catch(Exception e){}
        }
        else{
            try{
                mmr.setDataSource(path);           
            }catch(Exception e){}
        }
        dataPath = path;
    }
    public Song getSong(){
        return new Song(graphic , title , artist , dataPath , editDate , Integer.valueOf(duration));
    }
    public ArrayList<String> getAll(){
        ArrayList<String> arrl = new ArrayList<>();
        if(album != null) arrl.add(album);
        return arrl; 
    }
    public String getAlbum(){
        return album;
    }
    public String getAlbumArtist(){
        return albumArtist;
    }
    public String getArtist(){
        return artist;
    }
    public String getAuthor(){
        return author;
    }
    public String getBitrate(){
        return bitrate;
    }
    public String getCaptureFramerate(){
        return captureFramerate;
    }
    public String getCdTrackNumber(){
        return cdTrackNumber;
    }
    public String getCompailation(){
        return compailation;
    }
    public String getComposer(){
        return composer;
    }
    public String getDate(){
        return date;
    }
    public String getDiscNumber(){
        return discNumber;
    }
    public String getDuration(){
        return duration;
    }
    public String getGenre(){
        return genre;
    }
    public String getHasAudio(){
        return hasAudio;
    }
    public String getHasImage(){
        return hasImage;
    }
    public String getHasVideo(){
        return hasVideo;
    } 
    public String getImageCount(){
        return imageCount;
    }
    public String getImageHeight(){
        return imageHeight;
    }
    public String getImagePrimary(){
        return imagePrimary;
    }
    public String getImageRotation(){
        return imageRotation;
    }
    public String getImageWidth(){
        return imageWidth;
    }
    public String getLocation(){
        return location;
    }
    public String getMineType(){
        return mineType;
    }
    public String getNumberTracks(){
        return numberTracks;
    }
    public String getTitle(){
        return title;
    }
    public String getVideoFarmeCount(){
        return videoFarmeCount;
    }
    public String getVideoHeight(){
        return videoHeight;
    }
    public String getVideoRotation(){
        return videoRotation;
    }
    public String getVideoWidth(){
        return videoWidth;
    }
    public String getWriter(){
        return writer;
    }
    public String getYear(){
        return year;
    }
    public String getOptionClosest(){
        return optionClosest;
        
    }
    public String getOptionClosestSync(){
        return optionClosestSync;
        
    }
    public String getOptionNextSync(){
        return optionNextSync;
    }
    public String getOptionPreviousSync(){
        return optionPreviousSync;
    }

}
