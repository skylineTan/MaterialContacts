package com.kika.simplifycontacts.view;

import com.kika.simplifycontacts.bean.Contact;

/**
 * Created by skylineTan on 2016/6/30.
 */
public interface IAddView extends IView{

    void finishActivity(Contact contact);

    void showError(String msg);

}
