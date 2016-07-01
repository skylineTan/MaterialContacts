package com.kika.simplifycontacts.model;

import android.content.Context;
import com.kika.simplifycontacts.bean.Contact;
import com.kika.simplifycontacts.support.callback.DataCallback;
import com.kika.simplifycontacts.support.callback.StatusCallback;
import java.util.List;

/**
 * Created by skylineTan on 2016/6/30.
 */
public interface IContactModel {

    void getContactList(Context context, DataCallback<List<Contact>> callback);

    void addContact(Context context,Contact contact,StatusCallback callback);

    void editContact(Context context,Contact contact,StatusCallback callback);

    void deleteContact(Context context,Contact contact,StatusCallback callback);

    void getEmail(Context context,List<Contact> contactList,
                     DataCallback<List<Contact>> callback);

    void getNickName(Context context,List<Contact> contactList,
                        DataCallback<List<Contact>> callback);

    void getAddress(Context context,List<Contact> contactList,
                       DataCallback<List<Contact>> callback);

    void getName(Context context,List<Contact> contactList,
                    DataCallback<List<Contact>> callback);
}
