package com.kika.simplifycontacts.presenter.impl;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.kika.simplifycontacts.bean.Contact;
import com.kika.simplifycontacts.model.IContactModel;
import com.kika.simplifycontacts.model.impl.ContactModel;
import com.kika.simplifycontacts.presenter.IMainPresenter;
import com.kika.simplifycontacts.support.callback.DataCallback;
import com.kika.simplifycontacts.view.IMainView;
import java.util.List;

/**
 * Created by skylineTan on 2016/6/30.
 */
public class MainPresenter extends BasePresenter<IMainView>
        implements IMainPresenter {

    private IContactModel mIMainModel;
    private Context mContext;


    public MainPresenter(Context context) {
        mContext = context;
        mIMainModel = new ContactModel();
    }


    @SuppressWarnings("unchecked") @Override
    public void loadContacts(final boolean pullToRefresh) {
        getView().showLoading(pullToRefresh);
        mIMainModel.getContactList(mContext, new DataCallback<List<Contact>>() {
            @Override public void onSuccess(final List<Contact> contacts) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override public void run() {
                        getView().hideLoading();
                        getView().setData(contacts);
                    }
                });
            }


            @Override public void onFailure(final String msg) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override public void run() {
                        getView().hideLoading();
                        getView().showError(msg, pullToRefresh);
                    }
                });
            }
        });
    }
}
