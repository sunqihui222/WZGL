package com.shtoone.shtw.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;
import com.shtoone.shtw.fragment.EngineeringDepartment.YCLJinChangWeightFragment;
import com.shtoone.shtw.fragment.engineeringactivity.TaskListImpQueryFragment;

/**
 * Created by Administrator on 2017/10/8.
 */

public class YCLJinChangWeightActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joborderuncompounding);
        FrameLayout container = (FrameLayout) findViewById(R.id.root_layout_joborderuncompounding);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        YCLJinChangWeightFragment yclJinChangWeightFragment =  YCLJinChangWeightFragment.newInstance();
        transaction.add(R.id.root_layout_joborderuncompounding, yclJinChangWeightFragment);
        transaction.commit();
    }

}