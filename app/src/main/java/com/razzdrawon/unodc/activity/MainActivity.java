package com.razzdrawon.unodc.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.razzdrawon.unodc.R;

public class MainActivity extends AppCompatActivity {


    private Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startBtn = (Button) findViewById(R.id.start_btn);


        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(MainActivity.this, FormActivity.class);
                startActivity(intent);
            }
        });


    }
}
