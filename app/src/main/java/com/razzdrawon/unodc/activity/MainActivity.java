package com.razzdrawon.unodc.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.razzdrawon.unodc.R;
import com.razzdrawon.unodc.dbhelper.ItemSQLiteHelper;
import com.razzdrawon.unodc.listener.RequestListener;
import com.razzdrawon.unodc.model.ObjectSync;
import com.razzdrawon.unodc.util.Constants;
import com.razzdrawon.unodc.util.JsonParser;
import com.razzdrawon.unodc.ws.SyncWS;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private Button startBtn;
    private Button syncBtn;
    private ItemSQLiteHelper db;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new ItemSQLiteHelper(this);

        startBtn = (Button) findViewById(R.id.start_btn);
        syncBtn = (Button) findViewById(R.id.sync_btn);


        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(MainActivity.this, FormActivity.class);
                startActivity(intent);
            }
        });

        syncBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean isAvailabletoSync = false;
                List<ObjectSync> items  = db.getAllItems();

                for (ObjectSync item: items){
                    if(!item.getSync()) {
                        try {
                            makSyncRequest(item);
                            isAvailabletoSync = true;
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if(!isAvailabletoSync)
                    Toast.makeText(getApplicationContext(), "No hay cuestionarios por sincronizar.", Toast.LENGTH_SHORT).show();

            }
        });


    }



    private void makSyncRequest(ObjectSync obj) throws IOException, JSONException {
//        pDialog = new ProgressDialog(this);
//        pDialog.setTitle("Login in");
//        pDialog.setMessage("Wait a moment...");
//        pDialog.show();

        String jsonStr = JsonParser.parseObjSynctoString(obj);

        JSONObject jsonObj = new JSONObject(jsonStr);

        SyncWS.makePostObjSyncRequest(Constants.URL_WEBSERVICES, jsonObj, new RequestListener() {

            @Override
            public void onResponse(Object response) {

//                pDialog.hide();
//                pDialog.dismiss();
                Log.i("Volley request", "Success");
                Log.i("Response: ", "" + response);
                ObjectSync objSync = (ObjectSync) response;

                db.updateItem(objSync);

                Toast.makeText(getApplicationContext(), "El cuestionario " + objSync.getId() + " se sincronizó correctamente", Toast.LENGTH_SHORT).show();

                // Aquí se marcan en true los objetos ya sincronizados de SQLite

            }

            @Override
            public void onError(VolleyError error) {
//                pDialog.hide();
//                pDialog.dismiss();

            }

        });

    }


}
