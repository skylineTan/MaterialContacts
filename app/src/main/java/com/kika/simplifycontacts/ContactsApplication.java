package com.kika.simplifycontacts;

import android.app.Application;
import android.content.Context;

/**
 * Created by skylineTan on 2016/6/29.
 */
public class ContactsApplication extends Application{

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override public void onCreate() {
        super.onCreate();
        context = this;
    }
}
