package com.shtoone.shtw.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.bean.ParametersData;
import com.shtoone.shtw.fragment.mainactivity.LaboratoryFragment;
import com.shtoone.shtw.fragment.weighthouseactivity.EnterPoundsQuery;
import com.shtoone.shtw.fragment.weighthouseactivity.PlayPoundsQuery;
import com.shtoone.shtw.utils.ConstantsUtils;
import com.socks.library.KLog;
import com.squareup.otto.Subscribe;

public class WeightHouseViewPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = WeightHouseViewPagerAdapter.class.getSimpleName();
    private String[] titleType = {"进场过磅", "出场过磅"};
    private ParametersData mParametersData;
    private boolean isRegistered = false;

    public WeightHouseViewPagerAdapter(FragmentManager fm, ParametersData mParametersData) {
        super(fm);
        this.mParametersData = mParametersData;

        if (!isRegistered) {
            BaseApplication.bus.register(this);
            isRegistered = true;
        }
    }

    @Override
    public Fragment getItem(int position) {
        ParametersData mParametersData = (ParametersData) this.mParametersData.clone();
        Fragment fragment=null;
        switch (position){
            case  0:
                fragment= EnterPoundsQuery.newInstance(mParametersData);
                break;
            case 1:
                fragment= PlayPoundsQuery.newInstance(mParametersData);
                break;
        }
        return  fragment;
    }

    @Override
    public int getCount() {
        if (null != titleType) {
            return titleType.length;
        }
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (null != titleType) {
            return titleType[position];
        }
        return null;
    }

    @Subscribe
    public void updateSearch(ParametersData mParametersData) {
        if (mParametersData != null) {
            if (mParametersData.fromTo == ConstantsUtils.WEIGHTHOUSEFRAGMENT) {
                this.mParametersData.startDateTime = mParametersData.startDateTime;
                this.mParametersData.endDateTime = mParametersData.endDateTime;
                this.mParametersData.equipmentID = mParametersData.equipmentID;
                this.mParametersData.cailiaono = mParametersData.cailiaono;
                KLog.e("mParametersData:" + mParametersData.startDateTime);
                KLog.e("mParametersData:" + mParametersData.endDateTime);
                KLog.e("mParametersData:" + mParametersData.equipmentID);
                KLog.e("mParametersData:" + mParametersData.cailiaono);
            }
        }
    }

    public void unRegister() {
        BaseApplication.bus.unregister(this);
    }
}
