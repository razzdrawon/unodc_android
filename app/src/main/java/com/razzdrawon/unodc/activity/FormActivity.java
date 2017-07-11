package com.razzdrawon.unodc.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.razzdrawon.unodc.R;
import com.razzdrawon.unodc.adapter.ItemAdapter;
import com.razzdrawon.unodc.model.Item;
import com.razzdrawon.unodc.util.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class FormActivity extends AppCompatActivity {

    private List<Item> itemList = new ArrayList<>();
    private List<Item> copyItemList;
    private RecyclerView recyclerView;
    private ItemAdapter mAdapter;
    private Button finishBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        finishBtn = (Button) findViewById(R.id.finish_btn);



        try {
            readJsonFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //This List is gonaa be stored
                mAdapter.getItemList();
//                JsonParser.

                //Here we implement SQLite


                Intent intent;
                intent = new Intent(FormActivity.this, ThanksActivity.class);
                intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

    }

    private void readJsonFile() throws IOException {

        InputStream is = getResources().openRawResource(R.raw.questions);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            is.close();
        }

        String jsonString = writer.toString();

        copyItemList = JsonParser.parseItems(jsonString);

        if(copyItemList != null && copyItemList.size()>0) {
            itemList.add(copyItemList.get(0));
        }

        mAdapter = new ItemAdapter(this, itemList, copyItemList);
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onBackPressed() {
        showAlertDialog("¿Seguro que desea regresar? Se perderá la información capturada en este cuestionario.");
    }

    private void showAlertDialog(String mensaje){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder
                .setTitle("Advertencia")
                .setMessage(mensaje)
                .setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        startActivity(new Intent(FormActivity.this, MainActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
