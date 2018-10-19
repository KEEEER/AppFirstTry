package com.example.user.appexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		final TextView tv = findViewById(R.id.text_view_id);
		final Button button = findViewById(R.id.button_id);
         button.setOnClickListener(new View.OnClickListener() {
             @Override
			 public void onClick(View v) {
                 tv.setText("You are bad!");
             }
         });

    }
}
