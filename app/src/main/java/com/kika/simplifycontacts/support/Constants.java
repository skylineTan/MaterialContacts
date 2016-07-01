package com.kika.simplifycontacts.support;

/**
 * Created by skylineTan on 2016/6/30.
 */
public class Constants {

    public static class Extras{
        public static final String CONTACT = "contact";
        public static final String ADD_CONTACT = "add_contact";
    }

    public static class Requests{
        public static final int ADD_CONTACT = 10;
    }

    public static class Responses{
        public static final int MAIN = 11;
    }


    public static class Columns{
        public static final String URI_RAW_CONTACTS = "content://com.android.contacts/raw_contacts";
        public static final String URI_DATA = "content://com.android.contacts/data";
        public static final String RAW_CONTACT_ID = "raw_contact_id";
        //对应 电话号码
        public static final String DATA1 = "data1";
        //类型 2 为mobile 3为work
        public static final String DATA2 = "data2";
        //Label
        public static final String DATA3 = "data3";

        public static final String MIMETYPE = "mimetype";
        public static final String MIME_EMAIL = "vnd.android.cursor.item/email_v2";
        public static final String MIME_NICKNAME = "vnd.android.cursor.item/nickname";
        public static final String MIME_PHONE = "vnd.android.cursor.item/phone_v2";
        public static final String MIME_ADDRESS = "vnd.android.cursor.item/sip_address";
        public static final String MIME_NAME = "vnd.android.cursor.item/name";
    }
}
