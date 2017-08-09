package com.shtoone.shtw.fragment.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.DialogActivity;
import com.shtoone.shtw.activity.MainActivity;
import com.shtoone.shtw.activity.OrganizationActivity;
import com.shtoone.shtw.adapter.WeightHouseViewPagerAdapter;
import com.shtoone.shtw.bean.DepartmentData;
import com.shtoone.shtw.bean.ParametersData;
import com.shtoone.shtw.fragment.base.BaseLazyFragment;
import com.shtoone.shtw.utils.AnimationUtils;
import com.shtoone.shtw.utils.ConstantsUtils;
import com.socks.library.KLog;
import com.squareup.otto.Subscribe;

public class WeightHouseFragment extends BaseLazyFragment {
    private Toolbar mToolbar;
    private FloatingActionButton fab;
    private AHBottomNavigation bottomNavigation;
    private ParametersData mParametersData;
    private DepartmentData mDepartmentData;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private AppBarLayout mAppBarLayout;
    private WeightHouseViewPagerAdapter mAdapter;

    public static WeightHouseFragment newInstance() {
        return new WeightHouseFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        BaseApplication.bus.register(this);
        View view = inflater.inflate(R.layout.fragment_weight_house, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mAppBarLayout = (AppBarLayout) view.findViewById(R.id.appbar_toolbar_tablayout);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar_toolbar_tablayout);
        mTabLayout = (TabLayout) view.findViewById(R.id.tablayout_toolbar_tablayout);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_yaliji_fragment);
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {
        initData();
    }

    private void initData() {
        if (null != BaseApplication.parametersData) {
            mParametersData = (ParametersData) BaseApplication.parametersData.clone();
            mParametersData.fromTo = ConstantsUtils.WEIGHTHOUSEFRAGMENT;
        }
        if (null != BaseApplication.mDepartmentData && !TextUtils.isEmpty(BaseApplication.mDepartmentData.departmentName)) {
            mDepartmentData = new DepartmentData(BaseApplication.mUserInfoData.getDepartId(), BaseApplication.mUserInfoData.getDepartName(), ConstantsUtils.WEIGHTHOUSEFRAGMENT);
        }
        setToolbarTitle();
        ((MainActivity) _mActivity).initToolBar(mToolbar);
        mToolbar.inflateMenu(R.menu.menu_hierarchy);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_hierarchy:
                        Intent intent = new Intent(getActivity(), OrganizationActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(ConstantsUtils.DEPARTMENT, mDepartmentData);
                        intent.putExtras(bundle);
                        intent.putExtra("type", "1");
                        AnimationUtils.startActivity(_mActivity, intent, mToolbar.findViewById(R.id.action_hierarchy), R.color.base_color, 500);
                        break;
                }
                return true;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(_mActivity, DialogActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(ConstantsUtils.PARAMETERS, mParametersData);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        setAdapter();
    }

    private void setToolbarTitle() {
        if (null != mToolbar && null != mDepartmentData && !TextUtils.isEmpty(mDepartmentData.departmentName)) {
            StringBuffer sb = new StringBuffer(mDepartmentData.departmentName + " > ");
            sb.append(getString(R.string.weight_house)).trimToSize();
            mToolbar.setTitle(sb.toString());
        }
    }

    private void setAdapter() {
        mViewPager.setAdapter(mAdapter = new WeightHouseViewPagerAdapter(getChildFragmentManager(), mParametersData));
        mTabLayout.setupWithViewPager(mViewPager);
    }

//    @Subscribe
//    public void updateSearch(ParametersData mParametersData) {
//        if (mParametersData != null) {
//            if (mParametersData.fromTo == ConstantsUtils.WEIGHTHOUSEFRAGMENT) {
//                this.mParametersData.startDateTime = mParametersData.startDateTime;
//                this.mParametersData.endDateTime = mParametersData.endDateTime;
//                this.mParametersData.equipmentID = mParametersData.equipmentID;
//                this.mParametersData.testTypeID = mParametersData.testTypeID;
//                KLog.e("mParametersData:" + mParametersData.startDateTime);
//                KLog.e("mParametersData:" + mParametersData.endDateTime);
//                KLog.e("mParametersData:" + mParametersData.equipmentID);
//                KLog.e("mParametersData:" + mParametersData.testTypeID);
//
//            }
//        }
//    }

    @Override
    public void onResume() {
        super.onResume();
        //返回到看见此fragment时，fab显示
        fab.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        //防止屏幕旋转后重画时fab显示
        fab.hide();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BaseApplication.bus.unregister(this);
    }

}
