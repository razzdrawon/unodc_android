package com.razzdrawon.unodc.ws;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.razzdrawon.unodc.listener.RequestListener;
import com.razzdrawon.unodc.model.ObjectSync;
import com.razzdrawon.unodc.util.Constants;
import com.razzdrawon.unodc.util.JsonParser;
import com.razzdrawon.unodc.util.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.android.volley.Request.*;

/**
 * Created by mapadi3 on 12/07/17.
 */

public class SyncWS {

    public static void makePostObjSyncRequest(final String url, JSONObject params, final RequestListener listener)  {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST, url, params,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            ObjectSync objSync = JsonParser.parseObjSync(response.toString());
                            listener.onResponse(objSync);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //com.android.volley.TimeoutError        //null
                        Log.e(Constants.TAG_VOLLEY_REQ_EXCEPTION, error.toString() + ": " + error.getMessage());
                        listener.onError(error);
                    }
                }
        );

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy( // total timeout 20 segundos
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * Constants.REQUEST_RETRY_TIME_FACTOR, //Timeout - Specifies Socket Timeout in millis per every retry attempt.
                1, //Number Of Retries - Number of times retry is attempted.
                3)); // Back Off Multiplier - A multiplier which is used to determine exponential time set to socket for every retry attempt.

        // Adding request to request queue
        VolleySingleton.getInstance().addToRequestQueue(jsonObjReq);
    }

    public static void makePostListObjSyncRequest(final String url, JSONArray params, final RequestListener listener)  {

        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Method.POST, url, params,

                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            List<ObjectSync> listObjSync = JsonParser.parseListObjSync(response.toString());
                            listener.onResponse(listObjSync);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //com.android.volley.TimeoutError        //null
                        Log.e(Constants.TAG_VOLLEY_REQ_EXCEPTION, error.toString() + ": " + error.getMessage());
                        listener.onError(error);
                    }
                }
        );

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy( // total timeout 20 segundos
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * Constants.REQUEST_RETRY_TIME_FACTOR, //Timeout - Specifies Socket Timeout in millis per every retry attempt.
                1, //Number Of Retries - Number of times retry is attempted.
                3)); // Back Off Multiplier - A multiplier which is used to determine exponential time set to socket for every retry attempt.

        // Adding request to request queue
        VolleySingleton.getInstance().addToRequestQueue(jsonObjReq);
    }

}
