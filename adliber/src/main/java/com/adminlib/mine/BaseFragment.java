package com.adminlib.mine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * 项目名称：JKPay
 * 类描述：
 * 创建人：Michael
 * 创建时间：2015/12/28 13:39
 * 修改人：Michael
 * 修改时间：2015/12/28 13:39
 * 修改备注：
 */
public abstract class BaseFragment extends Fragment {

    private boolean injected = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        injected = true;
        View view = onBaseCreateView(inflater, container, savedInstanceState);
        return view;
    }

    public abstract View onBaseCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!injected) {
        }
    }

    protected void showToast(String msg) {
        MApplication.showToast(getActivity(), msg + "");
    }

    protected String getResString(int resId) {
        return getResources().getString(resId) + "";
    }
}
