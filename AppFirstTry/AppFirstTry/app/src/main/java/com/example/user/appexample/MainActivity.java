package com.example.user.appexample;

import abc.sayHello;
import android.widget.EditText;
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
		final EditText ET1 = findViewById(R.id.edittext1);
		final EditText ET2 = findViewById(R.id.edittext2);
		final TextView tv = findViewById(R.id.text_view_id);
		final Button button = findViewById(R.id.button_id);
         button.setOnClickListener(new View.OnClickListener() {
             @Override
			 public void onClick(View v) {
				 int a = Integer.parseInt(ET1.getText().toString());
				 int b = Integer.parseInt(ET2.getText().toString());
                 sayHello pt = new sayHello();
				 int ans = pt.plus(a , b);
				 tv.setText(String.valueOf(ans));
             }
         });

    }
}
