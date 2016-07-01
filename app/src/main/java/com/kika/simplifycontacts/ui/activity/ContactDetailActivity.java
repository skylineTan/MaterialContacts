package com.kika.simplifycontacts.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.kika.simplifycontacts.R;
import com.kika.simplifycontacts.bean.Contact;
import com.kika.simplifycontacts.presenter.IDetailPresenter;
import com.kika.simplifycontacts.presenter.impl.DetailPresenter;
import com.kika.simplifycontacts.support.Constants;
import com.kika.simplifycontacts.utils.GlideHelper;
import com.kika.simplifycontacts.view.IDetailView;

/**
 * Created by skylineTan on 2016/6/30.
 */
public class ContactDetailActivity
        extends BaseListActivity<IDetailView, IDetailPresenter>
        implements IDetailView {

    @Bind(R.id.detail_mobile) TextView detailMobile;
    @Bind(R.id.detail_work) TextView detailWork;
    @Bind(R.id.detail_personal_email) TextView detailPersonalEmail;
    @Bind(R.id.detail_nick_name) TextView detailNickName;
    @Bind(R.id.detail_address) TextView detailAddress;
    @Bind(R.id.detail_other) TextView detailOther;
    @Bind(R.id.detail_pic) ImageView detailPic;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbar;
    @Bind(R.id.detail_fab) FloatingActionButton detailFab;
    private Contact mContact;


    public static void startActivityWithContact(Context context, Contact contact) {
        Intent intent = new Intent(context, ContactDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.Extras.CONTACT, contact);
        intent.putExtras(bundle);
        ((Activity)context).startActivityForResult(intent,Constants.Requests
                .ADD_CONTACT);
    }


    @Override protected IDetailPresenter createPresenter() {
        return new DetailPresenter();
    }


    @Override protected void initViewsAndEvents() {
        mContact = getIntent().getParcelableExtra(Constants.Extras.CONTACT);
        initToolbar();
        detailMobile.setText(mContact.getPhoneNumber());
        detailWork.setText(mContact.getPhoneNumber());
        detailPersonalEmail.setText(mContact.getEmail());
        detailNickName.setText(mContact.getNickName());
        detailAddress.setText(mContact.getAddress());
        GlideHelper.getInstance().loadLocalImage(this,R.mipmap.person_header1,
                new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        detailPic.setImageBitmap(resource);
                    }
                });
    }


    private void initToolbar() {
        setSupportActionBar(toolbar);
        collapsingToolbar.setTitle(mContact.getName());
        //左上角图标是否显示
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //给左上角图标的左边加上一个返回的图标
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override protected int getLayoutId() {
        return R.layout.activity_contact_detail;
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
                AddContactActivity.startActivityWithContact(this,mContact);
                break;
            default:
                break;
        }
        return true;
    }
}
