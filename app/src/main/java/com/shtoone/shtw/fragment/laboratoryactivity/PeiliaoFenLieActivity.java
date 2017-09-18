package com.shtoone.shtw.fragment.laboratoryactivity;

import android.Manifest;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;
import com.shtoone.shtw.adapter.PeiliaoFenLieAdapter;
import com.shtoone.shtw.bean.PeiliaoTongzhidanFragmentListData;

import java.util.List;

import static com.shtoone.shtw.BaseApplication.mDepartmentData;

/**
 * Created by Administrator on 2017/9/12.
 */

public class PeiliaoFenLieActivity extends BaseActivity {

    private Toolbar mToolbar;
    private AHBottomNavigation bottomNavigation;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private AppBarLayout mAppBarLayout;
    private PeiliaoFenLieAdapter mAdapter;
    private PeiliaoTongzhidanFragmentListData.DataBean mDataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_yclquery);
        initView();
        initData();
    }

    private void initView() {
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appbar_toolbar_tablayout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_toolbar_tablayout);
        mTabLayout = (TabLayout) findViewById(R.id.tablayout_toolbar_tablayout);
        mViewPager = (ViewPager) findViewById(R.id.vp_jinchu_fragment);
    }

    private void initData() {
        setToolbarTitle();
        initToolbarBackNavigation(mToolbar);
        mDataBean = (PeiliaoTongzhidanFragmentListData.DataBean) getIntent().getSerializableExtra("PeiliaoTongzhidanDetail");
        setAdapter();
    }

    private void setAdapter() {
        mViewPager.setAdapter(mAdapter = new PeiliaoFenLieAdapter(getSupportFragmentManager(), mDataBean));
        mTabLayout.setupWithViewPager(mViewPager);

    }

    private void setToolbarTitle() {
        if (null != mToolbar && null != mDepartmentData && !TextUtils.isEmpty(mDepartmentData.departmentName)) {
            StringBuffer sb = new StringBuffer(mDepartmentData.departmentName + " > ");
            sb.append("进出厂台账");
            mToolbar.setTitle(sb.toString());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BaseApplication.bus.unregister(this);
    }


}
