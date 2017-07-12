package com.razzdrawon.unodc.listener;

import com.android.volley.VolleyError;

/**
 * Created by Ricardo on 13/04/16.
 */
public interface RequestListener {
    public void onResponse(Object response);

    public void onError(VolleyError error);

}
