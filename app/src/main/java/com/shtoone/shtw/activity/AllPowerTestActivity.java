package com.shtoone.shtw.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;
import com.shtoone.shtw.fragment.laboratoryactivity.WannengjiFragment;
import com.shtoone.shtw.fragment.laboratoryactivity.YaLiJiFragment;

/**
 * Created by liangfeng on 2017/9/30.
 */

public class AllPowerTestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_power_test);
        FrameLayout container = (FrameLayout) findViewById(R.id.root_layout_all_power_test);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        WannengjiFragment wannengjiFragment = WannengjiFragment.newInstance();
        transaction.add(R.id.root_layout_all_power_test, wannengjiFragment);
        transaction.commit();
    }

}
