package com.example.user.myapplication;

import android.media.MediaPlayer;
import com.example.mylibrary.classTry;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
    }	
	public void  newView(View view){
		Button button = findViewById(R.id.button_id);
		button.setText("you are bad!");
		//Intent intent = getPackageManager().getLaunchIntentForPackage("com.example.mylibrary.classTry");
	//	Intent intent = new Intent("com.example.mylibrary.classTry");
     //   startActivity(intent);
	}
}
