package com.kika.simplifycontacts.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import com.kika.simplifycontacts.presenter.IPresenter;
import com.kika.simplifycontacts.view.IView;

/**
 * Created by skylineTan on 2016/6/30.
 */
public abstract class BaseListActivity<V extends IView, P extends
        IPresenter<V>>
        extends AppCompatActivity {

    protected P mPresenter;

    protected abstract P createPresenter();

    protected abstract void initViewsAndEvents();

    protected abstract int getLayoutId();


    @SuppressWarnings("unchecked") @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        mPresenter = createPresenter();
        if(mPresenter != null){
            mPresenter.attachView((V) this);
        }
        initViewsAndEvents();
    }


    @Override protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if(mPresenter != null){
            mPresenter.detachView();
        }
    }
}
