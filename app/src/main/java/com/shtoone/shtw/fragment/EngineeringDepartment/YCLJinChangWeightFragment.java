package com.shtoone.shtw.fragment.EngineeringDepartment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.bean.ParametersData;
import com.shtoone.shtw.fragment.base.BaseLazyFragment;
import com.shtoone.shtw.fragment.concreteactivity.ProduceQueryFragment;
import com.shtoone.shtw.ui.PageStateLayout;

import in.srain.cube.views.ptr.PtrFrameLayout;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

/**
 * Created by Administrator on 2017/8/7.
 */

public class YCLJinChangWeightFragment extends BaseLazyFragment {

    private Toolbar        mToolbar;
    private PtrFrameLayout mPtrFrameLayout;
    private RecyclerView   mRecyclerView;



    private FloatingActionButton fab;
    private boolean isRegistered = false;
    private PageStateLayout mPageStateLayout;
    private Gson            mGson;
    private boolean         isLoading;


    private ParametersData          mParametersData;
    private LinearLayoutManager     mLinearLayoutManager;
    private int                     lastVisibleItemPosition;
    private ScaleInAnimationAdapter mScaleInAnimationAdapter;

    public static YCLJinChangWeightFragment newInstance() {
        return new YCLJinChangWeightFragment();
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (!isRegistered) {
            BaseApplication.bus.register(this);
            isRegistered = true;
        }


        return super.onCreateView(inflater, container, savedInstanceState);


    }
}
