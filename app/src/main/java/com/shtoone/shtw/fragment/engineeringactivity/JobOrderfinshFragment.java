package com.shtoone.shtw.fragment.engineeringactivity;

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
import com.shtoone.shtw.R;
import com.shtoone.shtw.bean.ParametersData;
import com.shtoone.shtw.fragment.base.BaseLazyFragment;
import com.shtoone.shtw.ui.PageStateLayout;

import in.srain.cube.views.ptr.PtrFrameLayout;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

/**
 * Created by Administrator on 2017/8/22.
 */

public class JobOrderfinshFragment extends BaseLazyFragment{


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

    public static JobOrderfinshFragment newInstance() {
        return new JobOrderfinshFragment();
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
        View view = inflater.inflate(R.layout.fragment_joborder_finsh, container, false);
        initView(view);
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        //返回到看见此fragment时，fab显示
        fab.show();
    }

    private void initView(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar_toolbar);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        mPtrFrameLayout = (PtrFrameLayout) view.findViewById(R.id.ptrframelayout);
        mPageStateLayout = (PageStateLayout) view.findViewById(R.id.pagestatelayout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
    }


}
