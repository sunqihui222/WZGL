package com.shtoone.shtw.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.fragment.EngineeringDepartment.YCLChuChangWeightFragment;
import com.shtoone.shtw.fragment.EngineeringDepartment.YCLJinChangWeightFragment;
import com.shtoone.shtw.fragment.engineeringactivity.JobOrderUnfinshFragment;
import com.shtoone.shtw.fragment.engineeringactivity.JobOrderfinshFragment;

/**
 * Created by Administrator on 2017/8/18.
 */

public class JobOrderPagerAdapter extends FragmentPagerAdapter {


    private String[] titleType = {"未生产", "已生产"};
    private boolean isRegistered = false;


    public JobOrderPagerAdapter(FragmentManager fm) {
        super(fm);
        if (!isRegistered) {
            BaseApplication.bus.register(this);
            isRegistered = true;
        }
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment=null;
        switch (position){
            case  0:
                fragment= JobOrderUnfinshFragment.newInstance();
                break;
            case 1:
                fragment= JobOrderfinshFragment.newInstance();
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

    public void unRegister() {
        BaseApplication.bus.unregister(this);
    }
}
