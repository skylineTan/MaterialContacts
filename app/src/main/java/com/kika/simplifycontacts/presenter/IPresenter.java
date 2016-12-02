package com.kika.simplifycontacts.presenter;

import com.kika.simplifycontacts.view.IView;

/**
 * Created by skylineTan on 2016/6/29.
 */
public interface IPresenter<V extends IView> {

    void attachView(V view);

    void detachView();
}
