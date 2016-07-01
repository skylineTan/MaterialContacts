package com.kika.simplifycontacts.model.impl;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.Data;
import android.util.Log;
import com.kika.simplifycontacts.bean.Contact;
import com.kika.simplifycontacts.model.IContactModel;
import com.kika.simplifycontacts.support.Constants;
import com.kika.simplifycontacts.support.callback.DataCallback;
import com.kika.simplifycontacts.support.callback.StatusCallback;
import com.kika.simplifycontacts.utils.CharacterParser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by skylineTan on 2016/6/30.
 */
public class ContactModel implements IContactModel {

    private Map<String, Contact> mIdMap;


    @Override
    public void getContactList(final Context context, final DataCallback<List<Contact>> callback) {
        new Thread(new Runnable() {
            @Override public void run() {
                List<Contact> contactList = new ArrayList<>();
                mIdMap = new HashMap<>();
                Cursor cursor = null;
                try {
                    cursor = context.getContentResolver()
                                    .query(Data.CONTENT_URI, new String[] {
                                                    Constants.Columns.RAW_CONTACT_ID,
                                                    Constants.Columns.DATA1,
                                                    Constants.Columns.DATA2 },
                                            Constants.Columns.MIMETYPE + "='" +
                                                    Constants.Columns.MIME_PHONE +
                                                    "'", null, null);
                    if (cursor != null) {
                        while (cursor.moveToNext()) {
                            String id = cursor.getString(cursor.getColumnIndex(
                                    Constants.Columns.RAW_CONTACT_ID));
                            Contact contact = new Contact(id);
                            contact.setPhoneNumber(cursor.getString(
                                    cursor.getColumnIndex(
                                            Constants.Columns.DATA1)));
                            if (!contactList.contains(mIdMap.get(id))) {
                                mIdMap.put(id, contact);
                                contactList.add(contact);
                            }
                        }
                        getName(context, contactList, callback);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailure(e.getMessage());
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }
        }).start();
    }


    @Override
    public void addContact(final Context context, final Contact contact, final StatusCallback callback) {
        new Thread(new Runnable() {
            @Override public void run() {
                Uri uri = Uri.parse(Constants.Columns.URI_RAW_CONTACTS);
                ContentResolver resolver = context.getContentResolver();
                ContentValues values = new ContentValues();
                long contactid = ContentUris.parseId(
                        resolver.insert(uri, values));

                uri = Uri.parse(Constants.Columns.URI_DATA);

                //姓名
                values.put(Constants.Columns.RAW_CONTACT_ID, contactid);
                values.put(Data.MIMETYPE, Constants.Columns.MIME_NAME);
                values.put(Constants.Columns.DATA1, contact.getName());
                resolver.insert(uri, values);
                values.clear();

                //电话
                values.put(Constants.Columns.RAW_CONTACT_ID, contactid);
                values.put(Data.MIMETYPE, Constants.Columns.MIME_PHONE);
                values.put(Constants.Columns.DATA1, contact.getPhoneNumber());
                resolver.insert(uri, values);
                values.clear();

                //Email
                values.put(Constants.Columns.RAW_CONTACT_ID, contactid);
                values.put(Data.MIMETYPE, Constants.Columns.MIME_EMAIL);
                values.put(Constants.Columns.DATA1, contact.getEmail());
                resolver.insert(uri, values);
                values.clear();

                //NickName
                values.put(Constants.Columns.RAW_CONTACT_ID, contactid);
                values.put(Data.MIMETYPE, Constants.Columns.MIME_NICKNAME);
                values.put(Constants.Columns.DATA1, contact.getNickName());
                resolver.insert(uri, values);
                values.clear();

                //Address
                values.put(Constants.Columns.RAW_CONTACT_ID, contactid);
                values.put(Data.MIMETYPE, Constants.Columns.MIME_ADDRESS);
                values.put(Constants.Columns.DATA1, contact.getAddress());
                resolver.insert(uri, values);

                callback.onSuccess();
            }
        }).start();
    }


    @Override
    public void editContact(final Context context, final Contact contact, final StatusCallback callback) {
        new Thread(new Runnable() {
            @Override public void run() {
                ContentValues values = new ContentValues();
                //修改名字
                values.put(Data.MIMETYPE, Constants.Columns.MIME_NAME);
                values.put(Constants.Columns.DATA1, contact.getName());
                context.getContentResolver()
                       .update(Data.CONTENT_URI, values,
                               Constants.Columns.RAW_CONTACT_ID + "= ?",
                               new String[] { contact.getId() });
                values.clear();

                //电话
                values.put(Data.MIMETYPE, Constants.Columns.MIME_PHONE);
                values.put(Constants.Columns.DATA1, contact.getPhoneNumber());
                context.getContentResolver()
                       .update(Data.CONTENT_URI, values,
                               Constants.Columns.RAW_CONTACT_ID + "= ?",
                               new String[] { contact.getId() });
                values.clear();

                //Email
                values.put(Data.MIMETYPE, Constants.Columns.MIME_EMAIL);
                values.put(Constants.Columns.DATA1, contact.getEmail());
                context.getContentResolver()
                       .update(Data.CONTENT_URI, values,
                               Constants.Columns.RAW_CONTACT_ID + "= ?",
                               new String[] { contact.getId() });
                values.clear();

                //NickName
                values.put(Data.MIMETYPE, Constants.Columns.MIME_NICKNAME);
                values.put(Constants.Columns.DATA1, contact.getNickName());
                context.getContentResolver()
                       .update(Data.CONTENT_URI, values,
                               Constants.Columns.RAW_CONTACT_ID + "= ?",
                               new String[] { contact.getId() });
                values.clear();

                //Address
                values.put(Data.MIMETYPE, Constants.Columns.MIME_ADDRESS);
                values.put(Constants.Columns.DATA1, contact.getAddress());
                context.getContentResolver()
                       .update(Data.CONTENT_URI, values,
                               Constants.Columns.RAW_CONTACT_ID + "= ?",
                               new String[] { contact.getId() });
                callback.onSuccess();
            }
        }).start();
    }


    @Override
    public void deleteContact(final Context context, final Contact contact, final StatusCallback callback) {
        new Thread(new Runnable() {
            @Override public void run() {
                Uri uri = Uri.parse(Constants.Columns.URI_DATA);
                context.getContentResolver()
                       .delete(uri, Constants.Columns.RAW_CONTACT_ID + "= ?",
                               new String[] { contact.getId() });
                callback.onSuccess();
            }
        }).start();
    }


    @Override
    public void getEmail(Context context, List<Contact> contactList, DataCallback<List<Contact>> callback) {
        String id = "";
        Cursor cursor = null;
        for (Contact contact : contactList) {
            id = contact.getId();
            cursor = context.getContentResolver()
                            .query(Data.CONTENT_URI,
                                    new String[] { Constants.Columns.DATA1 },
                                    Constants.Columns.RAW_CONTACT_ID + "= ?" +
                                            " AND " +
                                            Constants.Columns.MIMETYPE + "='" +
                                            Constants.Columns.MIME_EMAIL +
                                            "'", new String[] { id }, null);
            if (cursor.moveToNext()) {
                mIdMap.get(id)
                      .setEmail(cursor.getString(
                              cursor.getColumnIndex(Constants.Columns.DATA1)));
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        getNickName(context, contactList, callback);
    }


    @Override
    public void getNickName(Context context, List<Contact> contactList, DataCallback<List<Contact>> callback) {
        String id = "";
        Cursor cursor = null;
        for (Contact contact : contactList) {
            id = contact.getId();
            cursor = context.getContentResolver()
                            .query(Data.CONTENT_URI,
                                    new String[] { Constants.Columns.DATA1 },
                                    Constants.Columns.RAW_CONTACT_ID + "= ?" +
                                            " AND " +
                                            Constants.Columns.MIMETYPE + "='" +
                                            Constants.Columns.MIME_NICKNAME +
                                            "'", new String[] { id }, null);
            if (cursor.moveToNext()) {
                mIdMap.get(id)
                      .setNickName(cursor.getString(
                              cursor.getColumnIndex(Constants.Columns.DATA1)));
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        getAddress(context, contactList, callback);
    }


    @Override
    public void getAddress(Context context, List<Contact> contactList, DataCallback<List<Contact>> callback) {
        String id = "";
        Cursor cursor = null;
        for (Contact contact : contactList) {
            id = contact.getId();
            cursor = context.getContentResolver()
                            .query(Data.CONTENT_URI,
                                    new String[] { Constants.Columns.DATA1 },
                                    Constants.Columns.RAW_CONTACT_ID + "= ?" +
                                            " AND " +
                                            Constants.Columns.MIMETYPE + "='" +
                                            Constants.Columns.MIME_ADDRESS +
                                            "'", new String[] { id }, null);
            if (cursor.moveToNext()) {
                mIdMap.get(id)
                      .setAddress(cursor.getString(
                              cursor.getColumnIndex(Constants.Columns.DATA1)));
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        callback.onSuccess(contactList);
    }


    @Override
    public void getName(Context context, List<Contact> contactList, DataCallback<List<Contact>> callback) {
        String id = "";
        Cursor cursor = null;
        CharacterParser characterParser = CharacterParser.getInstance();
        for (Contact contact : contactList) {
            id = contact.getId();
            cursor = context.getContentResolver()
                            .query(Data.CONTENT_URI,
                                    new String[] { Constants.Columns.DATA1 },
                                    Constants.Columns.RAW_CONTACT_ID + "= ?" +
                                            " AND " +
                                            Constants.Columns.MIMETYPE + "='" +
                                            Constants.Columns.MIME_NAME +
                                            "'", new String[] { id }, null);
            if (cursor.moveToNext()) {
                mIdMap.get(id)
                      .setName(cursor.getString(
                              cursor.getColumnIndex(Constants.Columns.DATA1)));
                mIdMap.get(id)
                      .setPinyin(characterParser.getSelling(
                              mIdMap.get(id).getName()));
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        getEmail(context, contactList, callback);
    }
}
