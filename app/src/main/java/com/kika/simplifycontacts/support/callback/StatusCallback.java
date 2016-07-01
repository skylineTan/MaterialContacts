package com.kika.simplifycontacts.support.callback;

/**
 * Created by skylineTan on 2016/6/30.
 */
public abstract class StatusCallback {

    public abstract void onSuccess();

    public abstract void onFailure(String msg);

    public void onError(String msg){}
}
