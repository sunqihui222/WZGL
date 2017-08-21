package com.shtoone.shtw.activity.base;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.adapter.FBProjectTreeListViewAdapter;
import com.shtoone.shtw.bean.FBProjectData;
import com.shtoone.shtw.bean.FENBUProjectData;
import com.shtoone.shtw.bean.ParametersData;
import com.shtoone.shtw.ui.PageStateLayout;
import com.shtoone.shtw.ui.treeview.Node;
import com.shtoone.shtw.ui.treeview.TreeListViewAdapter;
import com.shtoone.shtw.utils.NetworkUtils;
import com.shtoone.shtw.utils.URL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Administrator on 2017/8/10.
 */

public class FBProjectListActivity extends BaseActivity {

    private Toolbar                                    mToolbar;
    private ListView                                   treeListView;
    private LinearLayout                               ll_container;
    private PageStateLayout                            mPageStateLayout;
    private FENBUProjectData                              data;
    private List<FBProjectData>                        treeNodes;
    private String                                     type;
    private FBProjectTreeListViewAdapter<FBProjectData> mAdapter;
    private PtrFrameLayout                             mPtrFrameLayout;
    private ParametersData                             mParametersData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization);
        initView();
        initData();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_toolbar);
        mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.ptr_organization_activity);
        mPageStateLayout = (PageStateLayout) findViewById(R.id.psl_organization_activity);
        treeListView = (ListView) findViewById(R.id.lv_tree_organization_activity);
        ll_container = (LinearLayout) findViewById(R.id.ll_container_organization_activity);
    }

    private void initData() {


        mParametersData = BaseApplication.parametersData;
        mToolbar.setTitle("材料名称");
        initToolbarBackNavigation(mToolbar);
        treeNodes = new ArrayList<FBProjectData>();

        //        ll_container.post(new Runnable() {
        //            @Override
        //            public void run() {
        //                revealView();
        //            }
        //        });

        initPageStateLayout(mPageStateLayout);
        initPtrFrameLayout(mPtrFrameLayout);
    }

    @Override
    public String createRefreshULR() {


        return URL.FB_PROJECT;
    }

    @Override
    public boolean isCanDoRefresh() {

        return false;
    }

    @Override
    public void onRefreshSuccess(String response) {
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
                data = new Gson().fromJson(response, FENBUProjectData.class);
                if (null != data) {
                    if (data.getSuccess()) {
                        mPageStateLayout.showContent();
                        setViewData();
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

    private void setViewData() {
        if (null == data || !(data.getData().size() > 0)) {
            return;
        }

        String projectNo=null;
        String projectName=null;
        String parentNo=null;

        treeNodes.clear();

        for (FENBUProjectData.DataEntity mDataBean : data.getData()) {
            if (TextUtils.isEmpty(mDataBean.getProjectNo())) {
                mPageStateLayout.showError();
                return;
            } else {
                projectNo = mDataBean.getProjectNo();
            }

            parentNo = mDataBean.getParentNo();
            projectName = mDataBean.getProjectName();
            treeNodes.add(new FBProjectData(projectNo,parentNo,projectName));
        }

        try {
            mAdapter = new FBProjectTreeListViewAdapter<>(treeListView, this, treeNodes, 1);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        treeListView.setAdapter(mAdapter);
        mAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener() {
            @Override
            public void onClick(Node node, int position) {



                    mParametersData.projectno = node.getId();
                    BaseApplication.bus.post(mParametersData);


            }
        });

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // 添加返回过渡动画.
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public boolean swipeBackPriority() {
        return false;
    }
}
