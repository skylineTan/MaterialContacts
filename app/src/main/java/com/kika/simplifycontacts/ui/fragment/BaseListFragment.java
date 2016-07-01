package com.kika.simplifycontacts.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.kika.simplifycontacts.presenter.IPresenter;
import com.kika.simplifycontacts.presenter.impl.BasePresenter;
import com.kika.simplifycontacts.view.IBaseView;

/**
 * Created by skylineTan on 2016/6/30.
 */
public abstract  class BaseListFragment<M, V extends IBaseView<M>, P extends IPresenter<V>> extends Fragment{

    protected P mPresenter;

    protected abstract P createPresenter();

    protected abstract void initViewsAndEvents();

    protected abstract int getLayoutId();

    //选择性重写的方法
    protected void parseArguments() {

    }


    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseArguments();
    }


    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container,
                false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
        initViewsAndEvents();
    }


    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
