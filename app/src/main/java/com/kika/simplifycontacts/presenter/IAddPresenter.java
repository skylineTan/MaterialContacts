package com.kika.simplifycontacts.presenter;

import com.kika.simplifycontacts.bean.Contact;
import com.kika.simplifycontacts.view.IAddView;

/**
 * Created by skylineTan on 2016/6/30.
 */
public interface IAddPresenter extends IPresenter<IAddView>{

    void addContacts(Contact contact);

    void deleteContacts(Contact contact);

    void editContacts(Contact contact);
}
