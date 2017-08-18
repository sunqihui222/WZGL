package com.shtoone.shtw.fragment.engineeringactivity;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;
import com.shtoone.shtw.bean.DesignStrengthData;
import com.shtoone.shtw.bean.TaskListEditActivityData;
import com.shtoone.shtw.bean.TaskListImpQueryFragmenData;
import com.shtoone.shtw.ui.PageStateLayout;
import com.shtoone.shtw.utils.NetworkUtils;
import com.shtoone.shtw.utils.URL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;

import static com.shtoone.shtw.R.id.spinner_sjqd_task_list_edit;

/**
 * Created by Administrator on 2017/8/17.
 */

public class TaskListEditActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = TaskListEditActivity.class.getSimpleName();
    private Toolbar mToolbar;
    private NestedScrollView mNestedScrollView;
    private PageStateLayout mPageStateLayout;
    private PtrFrameLayout mPtrFrameLayout;
    private TaskListImpQueryFragmenData.DataBean mDataBean;
    private Gson mGson;
    private TextView tv_renwuno;
    private TextView tv_jhfl;
    private TextView tv_kaipan_time;
    private TextView tv_gcmc;
    private TextView tv_kddj;
    private TextView tv_ksdj;
    private TextView tv_person;
    private TextView tv_create_time;
    private TextView tv_remrak;
    private MaterialSpinner spinnerDepart;
    private MaterialSpinner spinner_jzbw;
    private MaterialSpinner spinner_sjqd;
    private MaterialSpinner spinner_taluodu;
    private MaterialSpinner spinner_jzfs;
    private TaskListEditActivityData data;
    private List<TaskListEditActivityData.DataBean> listData;
    private String url;
    public int TYPE;
    public static final int DESIGN_STRENGTH = 1;
    public static final int DEPARTMENT = 2;
    public static final int JIAOZHU_BUWEI = 3;
    public static final int JIAOZHU_WAY = 4;
    public static final int TALUODU = 5;
    public static final int TASKLISTEDIT = 6;
    private List<String> strengthIds;
    private List<String> strengthNames;
    private DesignStrengthData mDesignStrengthData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list_edit);
        initView();
        initData();
    }

    private void initView() {
//        mDataBean = (TaskListImpQueryFragmenData.DataBean) getIntent().getSerializableExtra("tasklistdetail");
        mToolbar = (Toolbar) findViewById(R.id.toolbar_toolbar);
        mNestedScrollView = (NestedScrollView) findViewById(R.id.nsv_task_list_edit_activity);
        mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.ptr_task_list_edit_activity);
        mPageStateLayout = (PageStateLayout) findViewById(R.id.psl_task_list_edit_activity);
        //基础信息
        tv_renwuno = (TextView) findViewById(R.id.tv_renwuno_task_list_edit);
        tv_jhfl = (TextView) findViewById(R.id.tv_fangliang_task_list_edit);
        tv_kaipan_time = (TextView) findViewById(R.id.tv_time_task_list_edit);
        spinnerDepart = (MaterialSpinner) findViewById(R.id.spinner_depart_task_list_edit);
        spinner_jzbw = (MaterialSpinner) findViewById(R.id.spinner_jzbw_task_list_edit);
        spinner_sjqd = (MaterialSpinner) findViewById(spinner_sjqd_task_list_edit);
        spinner_taluodu = (MaterialSpinner) findViewById(R.id.spinner_taluodu_task_list_edit);
        spinner_jzfs = (MaterialSpinner) findViewById(R.id.spinner_jzfs_task_list_edit);
        tv_gcmc = (TextView) findViewById(R.id.tv_gcmc_task_list_edit);
        tv_kddj = (TextView) findViewById(R.id.tv_kddj_task_list_edit);
        tv_ksdj = (TextView) findViewById(R.id.tv_ksdj_task_list_edit);
        tv_person = (TextView) findViewById(R.id.tv_person_task_list_edit);
        tv_create_time = (TextView) findViewById(R.id.tv_create_time_task_list_edit);
        tv_remrak = (TextView) findViewById(R.id.tv_remrak_task_list_edit);
        spinnerDepart.setOnClickListener(this);
        spinner_jzbw.setOnClickListener(this);
        spinner_sjqd.setOnClickListener(this);
        spinner_taluodu.setOnClickListener(this);
        spinner_jzfs.setOnClickListener(this);

        //执行情况
        TYPE = TASKLISTEDIT;

    }


    private void initData() {
        listData = new ArrayList<>();
        mGson = new Gson();
        setToolbarTitle();
        initToolbarBackNavigation(mToolbar);
        setSupportActionBar(mToolbar);
        initPageStateLayout(mPageStateLayout);
        initPtrFrameLayout(mPtrFrameLayout);
    }

    @Override
    public boolean isCanDoRefresh() {
        //判断是哪种状态的页面，都让其可下拉
        if (mNestedScrollView.getScrollY() == 0) {
            return true;
        }
        return false;
    }

    @Override
    public String createRefreshULR() {
        mPageStateLayout.showLoading();
        switch (TYPE){
            case TASKLISTEDIT:
//                String taskListEditData = URL.getRenwudanEditData(mDataBean.getId());
//                Log.e(TAG,"url=:"+taskListEditData);
//                return URL.getRenwudanEditData(mDataBean.getId());
                url = URL.getRenwudanEditData("90780");
                Log.e(TAG, "任务单编辑url=:"+url);
                break;
            case DESIGN_STRENGTH:
                url = URL.getStrengthName("SJQD");
                Log.e(TAG, "设计强度url=:"+url);
                break;
        }

        return url;
    }

    @Override
    public void onRefreshSuccess(String response) {
        switch (TYPE){
            case DESIGN_STRENGTH:
                setStrengthQueryView(response);
                break;
            case TASKLISTEDIT:
                setTaskListEditActivityView(response);
                break;
        }
    }

    public void setTaskListEditActivityView(String response){
        if (!TextUtils.isEmpty(response)) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
                mPageStateLayout.showError();
                return;
            }
            if (jsonObject.optBoolean("success")) {
                data = mGson.fromJson(response, TaskListEditActivityData.class);
                if (null != data) {
                    if (data.isSuccess()) {
                        mPageStateLayout.showContent();
                        listData.addAll(data.getData());
                        setAdapter();
                    } else {
                        //提示数据为空，展示空状态
                        mPageStateLayout.showEmpty();
                    }
                } else {
                    //提示数据解析异常，展示错误页面
                    mPageStateLayout.showError();
                }
            } else {
                //提示数据为空，展示空状态
                mPageStateLayout.showEmpty();
            }
        } else {
            //提示返回数据异常，展示错误页面
            mPageStateLayout.showError();
        }
    }

    @Override
    public void onRefreshFailed(VolleyError error) {
        //提示网络数据异常，展示网络错误页面。此时：1.可能是本机网络有问题，2.可能是服务器问题
        if (!NetworkUtils.isConnected(this)) {
            //提示网络异常,让用户点击设置网络
            mPageStateLayout.showNetError();
        } else {
            //服务器异常，展示错误页面，点击刷新
            mPageStateLayout.showError();
        }
    }

    //还是不能这样搞，可能会内存泄漏，重复创建Adapyer对象。后面解决
    private void setAdapter() {

        List<TaskListEditActivityData.DataBean> data = this.data.getData();
        TaskListEditActivityData.DataBean dataBean = data.get(0);
        tv_renwuno.setText(dataBean.getRenwuno());
        tv_jhfl.setText(dataBean.getJihuafangliang());
        tv_kaipan_time.setText(dataBean.getKaipanriqi());
        tv_gcmc.setText(dataBean.getGcmc());
        tv_kddj.setText(dataBean.getKangdongdengji());
        tv_ksdj.setText(dataBean.getKangshendengji());
        tv_person.setText(dataBean.getCreateperson());
        tv_create_time.setText(dataBean.getCreatetime());
        tv_remrak.setText(dataBean.getRemark());

//        spinnerDepart.setItems();
//        spinner_jzbw.setItems();
//        spinner_sjqd.setItems();
//        spinner_taluodu.setItems();
//        spinner_jzfs.setItems();
    }

//    public void getDesignStrengtht(){
//        url = URL.getStrengthName("SJQD");
//        refresh();
//
//    }

    private void setToolbarTitle() {
        if (null != mToolbar && null != BaseApplication.mDepartmentData && !TextUtils.isEmpty(BaseApplication.mDepartmentData.departmentName)) {
            StringBuffer sb = new StringBuffer(BaseApplication.mDepartmentData.departmentName + " > ");
            sb.append(getString(R.string.engineering_department) + " > ");
            sb.append(getString(R.string.renwudan_zx) + " > ");
            sb.append(getString(R.string.detail)).trimToSize();
            mToolbar.setTitle(sb.toString());
        }
    }

    private void setStrengthQueryView(String response) {
        mDesignStrengthData = new Gson().fromJson(response, DesignStrengthData.class);
        strengthNames = new ArrayList<>();
        strengthIds = new ArrayList<>();
        for (DesignStrengthData.DataBean temp : mDesignStrengthData.getData()) {
            strengthNames.add(temp.getTypename());
            strengthIds.add(temp.getTypecode());
        }
        Log.e(TAG,"strengthNames=:" + strengthNames);
        Log.e(TAG,"strengthIds=:" + strengthIds);
//        ArrayAdapter<String> strengthAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, strengthNames);
//        strengthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner_sjqd.setAdapter(strengthAdapter);
        spinner_sjqd.setItems(strengthNames);
        Log.e(TAG, "-----setStrengthQueryView----");
    }

    @Override
    public void onClick(View v) {
        spinnerDepart = (MaterialSpinner) findViewById(R.id.spinner_depart_task_list_edit);
        spinner_jzbw = (MaterialSpinner) findViewById(R.id.spinner_jzbw_task_list_edit);
        spinner_sjqd = (MaterialSpinner) findViewById(spinner_sjqd_task_list_edit);
        spinner_taluodu = (MaterialSpinner) findViewById(R.id.spinner_taluodu_task_list_edit);
        spinner_jzfs = (MaterialSpinner) findViewById(R.id.spinner_jzfs_task_list_edit);
        switch (v.getId()){
            case R.id.spinner_depart_task_list_edit:
                TYPE = DEPARTMENT;
                break;
            case R.id.spinner_jzbw_task_list_edit:
                TYPE = JIAOZHU_BUWEI;
                break;
            case spinner_sjqd_task_list_edit:
                TYPE = DESIGN_STRENGTH;
                refresh();
                Log.e(TAG, "onClick:"+ spinner_sjqd_task_list_edit );
                break;
            case R.id.spinner_taluodu_task_list_edit:
                TYPE = TALUODU;
                break;
            case R.id.spinner_jzfs_task_list_edit:
                TYPE = JIAOZHU_WAY;
                break;
        }
    }
}
