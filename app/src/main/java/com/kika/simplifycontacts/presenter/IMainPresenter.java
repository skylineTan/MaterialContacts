package com.kika.simplifycontacts.presenter;

import com.kika.simplifycontacts.view.IMainView;

/**
 * Created by skylineTan on 2016/6/30.
 */
public interface IMainPresenter extends IPresenter<IMainView>{

    void loadContacts(final boolean pullToRefresh);
}
