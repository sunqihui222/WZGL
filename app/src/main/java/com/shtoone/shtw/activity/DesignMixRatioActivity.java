package com.shtoone.shtw.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;
import com.shtoone.shtw.fragment.laboratoryactivity.PeiliaoTongzhidanFragment;

/**
 * Created by liangfeng on 2017/9/30.
 */

public class DesignMixRatioActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_mix_ratio);
        FrameLayout container = (FrameLayout) findViewById(R.id.root_layout_design_mix_ratio);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.commit();
    }

}
