package com.shtoone.shtw.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;
import com.shtoone.shtw.fragment.engineeringactivity.JobOrderUnSubmitFragment;

/**
 * Created by Administrator on 2017/10/8.
 */

public class JobOrderUnSubmitActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joborderunsubmit);
        FrameLayout container = (FrameLayout) findViewById(R.id.root_layout_joborderunsubmit);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        JobOrderUnSubmitFragment jobOrderUnSubmitFragment =  JobOrderUnSubmitFragment.newInstance();
        transaction.add(R.id.root_layout_joborderunsubmit, jobOrderUnSubmitFragment);
        transaction.commit();
    }
}
