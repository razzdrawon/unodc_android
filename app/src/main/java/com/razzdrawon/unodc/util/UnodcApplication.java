package com.razzdrawon.unodc.util;

import android.app.Application;

/**
 * Created by mapadi3 on 12/07/17.
 */

public class UnodcApplication  extends Application {
    private static UnodcApplication unodcApplicationContext;

    public UnodcApplication() {
        this.unodcApplicationContext = this;
    }

    public static UnodcApplication getAppContext() {
        return unodcApplicationContext;
    }
}
