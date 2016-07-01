package com.kika.simplifycontacts.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import com.kika.simplifycontacts.R;
import com.kika.simplifycontacts.bean.Contact;
import com.kika.simplifycontacts.presenter.IAddPresenter;
import com.kika.simplifycontacts.presenter.impl.AddPresenter;
import com.kika.simplifycontacts.support.Constants;
import com.kika.simplifycontacts.utils.CharacterParser;
import com.kika.simplifycontacts.view.IAddView;

public class AddContactActivity
        extends BaseListActivity<IAddView,
        IAddPresenter> implements IAddView{

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.add_contact_name) TextInputLayout addContactName;
    @Bind(R.id.add_contact_nick_name) TextInputLayout addContactNickName;
    @Bind(R.id.add_contact_phone) TextInputLayout addContactPhone;
    @Bind(R.id.add_contact_email) TextInputLayout addContactEmail;
    @Bind(R.id.add_contact_address) TextInputLayout addContactAddress;
    @Bind(R.id.add_contact_cancel) TextView addContactCancel;
    @Bind(R.id.add_contact_save) TextView addContactSave;
    private Contact mContact;


    public static void startActivityWithContact(Context context, Contact contact) {
        Intent intent = new Intent(context, AddContactActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.Extras.CONTACT, contact);
        intent.putExtras(bundle);
        ((Activity)context).startActivityForResult(intent,Constants.Requests
                .ADD_CONTACT);
    }

    @Override protected IAddPresenter createPresenter() {
        return new AddPresenter(this);
    }


    @Override protected void initViewsAndEvents() {
        initToolbar();
        mContact = getIntent().getParcelableExtra(Constants.Extras.CONTACT);
        if(mContact != null){
            addContactName.getEditText().setText(mContact.getName());
            addContactNickName.getEditText().setText(mContact.getNickName());
            addContactPhone.getEditText().setText(mContact.getPhoneNumber());
            addContactEmail.getEditText().setText(mContact.getEmail());
            addContactAddress.getEditText().setText(mContact.getAddress());
        }
    }


    @Override protected int getLayoutId() {
        return R.layout.activity_add_contact;
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_contact_detail, menu);
        return true;
    }


    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_edit:

            default:
                break;
        }
        return true;
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        //左上角图标是否显示
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //给左上角图标的左边加上一个返回的图标
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @OnClick(R.id.add_contact_cancel)
    public void clickToCancel(){
        finish();
    }

    @OnClick(R.id.add_contact_save)
    public void clickToSave(){
        Contact contact = new Contact();
        contact.setName(addContactName.getEditText().getText().toString());
        contact.setEmail(addContactEmail.getEditText().getText().toString());
        contact.setNickName(addContactNickName.getEditText().getText().toString());
        contact.setAddress(addContactAddress.getEditText().getText().toString());
        contact.setPhoneNumber(addContactPhone.getEditText().getText().toString());
        contact.setPinyin(CharacterParser.getInstance().getSelling(contact
                .getName()));
        if(mContact == null){
            mPresenter.addContacts(contact);
        }else {
            contact.setId(mContact.getId());
            mPresenter.editContacts(contact);
        }

    }


    @Override public void finishActivity(Contact contact) {
        if(mContact == null){
            Snackbar.make(addContactSave,"Add Contact Success",Snackbar
                    .LENGTH_SHORT).show();
        }else {
            Snackbar.make(addContactSave,"Edit Contact Success",Snackbar
                    .LENGTH_SHORT)
                    .show();
        }
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.Extras.ADD_CONTACT, contact);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override public void showError(String msg) {
        Snackbar.make(addContactSave,"Something error:"+msg,Snackbar
                .LENGTH_SHORT)
                .show();
    }
}
