package com.kika.simplifycontacts.presenter.impl;

import com.kika.simplifycontacts.presenter.IPresenter;
import com.kika.simplifycontacts.view.IView;
import java.lang.ref.WeakReference;

/**
 * Created by skylineTan on 2016/6/29.
 */
public class BasePresenter<V extends IView> implements IPresenter<V> {

    private WeakReference<V> viewRef;


    @Override public void attachView(V view) {
        viewRef = new WeakReference<V>(view);
    }

    public V getView(){
        return viewRef == null ? null : viewRef.get();
    }

    public boolean isViewAttached(){
        return viewRef != null && viewRef.get() != null;
    }

    @Override public void detachView() {
        if(viewRef != null){
            viewRef.clear();
            viewRef = null;
        }
    }
}
