package com.shtoone.shtw.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;
import com.shtoone.shtw.fragment.laboratoryactivity.LaboratoryStatisticFragment;
import com.shtoone.shtw.fragment.laboratoryactivity.PeiliaoTongzhidanFragment;

/**
 * Created by liangfeng on 2017/9/30.
 */

public class StatisticAnalysisActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_analysis);
        FrameLayout container = (FrameLayout) findViewById(R.id.root_layout_statistic_analysis);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        LaboratoryStatisticFragment laboratoryStatisticFragment = LaboratoryStatisticFragment.newInstance();
        transaction.add(R.id.root_layout_statistic_analysis, laboratoryStatisticFragment);
        transaction.commit();
    }

}
