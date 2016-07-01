package com.kika.simplifycontacts.view;

/**
 * Created by skylineTan on 2016/6/30.
 */
public interface IBaseView<M> extends IView {

    void showLoading(boolean pullToRefresh);

    void hideLoading();

    void showError(String msg, boolean pullToRefresh);

    void setData(M data);

    void loadData(boolean pullToRefresh);

}
