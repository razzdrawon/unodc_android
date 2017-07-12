package com.razzdrawon.unodc.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.razzdrawon.unodc.R;
import com.razzdrawon.unodc.model.ObjectSync;

import java.util.ArrayList;

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

    private Button syncBtn;


    private  void sync(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        syncBtn = (Button) findViewById(R.id.sync_btn);


        syncBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<ObjectSync> items  = obtineElementosSyn();




            }
        });


    }



    private  ArrayList<ObjectSync> obtineElementosSyn(){

        ArrayList<ObjectSync>  items = new ArrayList<ObjectSync>();



        //Ir por esas mierdas a la base de datos
        items.add(new ObjectSync (1,"JSON1"));
        items.add(new ObjectSync (2,"JSON2"));
        items.add(new ObjectSync (3,"JSON3"));



        return items;
    }




}
