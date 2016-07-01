package com.kika.simplifycontacts.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Bind;
import com.kika.simplifycontacts.R;
import com.kika.simplifycontacts.bean.Contact;
import com.kika.simplifycontacts.presenter.IMainPresenter;
import com.kika.simplifycontacts.presenter.impl.MainPresenter;
import com.kika.simplifycontacts.support.Constants;
import com.kika.simplifycontacts.ui.activity.ContactDetailActivity;
import com.kika.simplifycontacts.ui.adapter.BaseRecyclerAdapter;
import com.kika.simplifycontacts.ui.adapter.ContactAdapter;
import com.kika.simplifycontacts.view.IMainView;
import java.util.List;

/**
 * Created by skylineTan on 2016/6/30.
 */
public class MainFragment
        extends BaseListFragment<List<Contact>, IMainView, IMainPresenter>
        implements IMainView,SwipeRefreshLayout.OnRefreshListener{

    @Bind(R.id.loadingView) ProgressBar loadingView;
    @Bind(R.id.errorView) TextView errorView;
    @Bind(R.id.main_recycler_view) RecyclerView mainRecyclerView;
    @Bind(R.id.main_swipe_refresh_layout) SwipeRefreshLayout
            mainSwipeRefreshLayout;

    private List<Contact> mContactList;
    private ContactAdapter mContactAdapter;


    @Override protected IMainPresenter createPresenter() {
        return new MainPresenter(getActivity());
    }


    @Override protected void initViewsAndEvents() {
        loadData(false);
    }


    @Override protected int getLayoutId() {
        return R.layout.fragment_main;
    }


    @Override public void showLoading(boolean pullToRefresh) {
        errorView.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
        mainRecyclerView.setVisibility(View.GONE);
    }


    @Override public void hideLoading() {
        errorView.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
        mainRecyclerView.setVisibility(View.VISIBLE);
    }


    @Override public void showError(String msg, boolean pullToRefresh) {
        errorView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
        mainRecyclerView.setVisibility(View.GONE);
    }


    @Override public void setData(List<Contact> data) {
        mContactList = data;
        mContactAdapter = new ContactAdapter(getActivity(),R.layout
                .item_contact,mContactList);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mContactAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override public void onItemClick(View view, int position) {
                ContactDetailActivity.startActivityWithContact(getActivity(),
                        mContactList.get(position));
            }
        });
        mainRecyclerView.setAdapter(mContactAdapter);
    }


    @Override public void loadData(boolean pullToRefresh) {
        mPresenter.loadContacts(pullToRefresh);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            mContactAdapter.add((Contact) data.getParcelableExtra(Constants.Extras
                    .ADD_CONTACT));
        }
    }


    @Override public void onRefresh() {
        loadData(true);
    }
}
