package com.example.mylibrary;

import android.media.MediaPlayer;
//import com.example.user.myapplication.MainActivityt
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import android.view.View;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.io.IOException;
import android.widget.SeekBar;

public class classTry extends AppCompatActivity {
	private boolean playORpause = true;
	private SeekBar seekbar;
	private ListView listview;
	private ArrayAdapter adapter;
	private TextView textView;
	private Button bplay,bstop;
	Handler handler=new Handler();
	
    private MediaPlayer mp = new MediaPlayer();
	String[] strOrg = {"新北市","台北市","台中市","台南市","高雄市"};
	String[] str = {"新北市","台北市","台中市","台南市","高雄市"};
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new);
		textView = findViewById(R.id.textView);
		textView.setText("Hello how RU?");	
		listview = (ListView) findViewById(R.id.listview);
		
		bplay = (Button)findViewById(R.id.play);
        bstop = (Button)findViewById(R.id.stop);
		
		seekbar = (SeekBar)findViewById(R.id.seek);
		
		
		
		adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,str);
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
	
	/*new Handler(this.getMainLooper()).post(new Runnable(){
			public void run(){
				seekbar.setProgress(mp.getCurrentPosition());
			}
		});*/
	
    Runnable start=new Runnable(){

        @Override
        public void run() {
			textView.setText(String.valueOf(mp.getCurrentPosition()));
			seekbar.setProgress(mp.getCurrentPosition());
            handler.postDelayed(start, 10);
			if(!(mp.isPlaying())){
				
				bplay.setText("RePlay?");
				playORpause = true;
			}
    //        handler.post(updatesb);
            //用一个handler更新SeekBar
        }
    	
    };

	
	private AdapterView.OnItemClickListener onClickListView = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
			textView.setText(str[position]);
			if(!(str[position] == strOrg[position])){
				str[position] = strOrg[position];
			}
			else {
				str[position] = "Here we go !" + str[position];
			}
			listview.setAdapter(adapter);
		}};
    public void onClick(View view){
		String s = getResources().getResourceEntryName(view.getId());
		textView.setText(s);
		
		switch(s){
			case "set":
				textView.setText("Button Set!");
				//textView.setText(String.valueOf(seekbar.getMax()));
				try {
					mp.setDataSource("/storage/emulated/0/Download/try.mp3");
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
					textView.setText("Button Play!");	
					mp.start();
					textView.setText(String.valueOf(mp. getCurrentPosition()));
					playORpause = false;
					bplay.setText("Pause!");
				//	bplay.setText(String.valueOf(mp.getDuration()));
				}
				else{
					textView.setText("Button Pause!");
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
				textView.setText("Button Stop!");
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

