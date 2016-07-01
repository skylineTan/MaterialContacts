package com.kika.simplifycontacts.presenter.impl;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.kika.simplifycontacts.bean.Contact;
import com.kika.simplifycontacts.model.IContactModel;
import com.kika.simplifycontacts.model.impl.ContactModel;
import com.kika.simplifycontacts.presenter.IAddPresenter;
import com.kika.simplifycontacts.support.callback.StatusCallback;
import com.kika.simplifycontacts.view.IAddView;

/**
 * Created by skylineTan on 2016/6/30.
 */
public class AddPresenter extends BasePresenter<IAddView> implements
        IAddPresenter {

    private IContactModel mIContactModel;
    private Context mContext;

    public AddPresenter(Context context){
        mContext = context;
        mIContactModel = new ContactModel();
    }

    @Override public void addContacts(final Contact contact) {
        mIContactModel.addContact(mContext, contact, new StatusCallback() {
            @Override public void onSuccess() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override public void run() {
                        getView().finishActivity(contact);
                    }
                });
            }


            @Override public void onFailure(final String msg) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override public void run() {
                        getView().showError(msg);
                    }
                });
            }
        });
    }


    @Override public void deleteContacts(final Contact contact) {
        mIContactModel.deleteContact(mContext, contact, new StatusCallback() {
            @Override public void onSuccess() {
                getView().finishActivity(contact);
            }


            @Override public void onFailure(String msg) {
                getView().showError(msg);
            }
        });
    }


    @Override public void editContacts(final Contact contact) {
        mIContactModel.editContact(mContext, contact, new StatusCallback() {
            @Override public void onSuccess() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override public void run() {
                        getView().finishActivity(contact);
                    }
                });
            }


            @Override public void onFailure(final String msg) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override public void run() {
                        getView().showError(msg);
                    }
                });
            }
        });
    }
}
