package com.shtoone.shtw.activity;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.animation.OvershootInterpolator;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;
import com.shtoone.shtw.adapter.YCLChuChangWeightFragmentDetailRecycleViewAdapter;
import com.shtoone.shtw.bean.YCLChuChangWeightFragmentDetailData;
import com.shtoone.shtw.bean.YCLChuChangWeightFragmentListData;
import com.shtoone.shtw.bean.listdata;
import com.shtoone.shtw.ui.PageStateLayout;
import com.shtoone.shtw.utils.NetworkUtils;
import com.shtoone.shtw.utils.URL;

import org.json.JSONException;
import org.json.JSONObject;

import in.srain.cube.views.ptr.PtrFrameLayout;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;

/**
 * Created by Administrator on 2017/8/8.
 */

public class WebServiceActivity extends BaseActivity{

    private static final String TAG = ProduceQueryDetailActivity.class.getSimpleName();
    private Toolbar                                           mToolbar;
    private NestedScrollView                                  mNestedScrollView;
    private PageStateLayout                                   mPageStateLayout;
    private PtrFrameLayout                                    mPtrFrameLayout;
    private listdata                                          data;
    private YCLChuChangWeightFragmentListData.DataEntity      mDataBean;
    private Gson                                              mGson;
    private RecyclerView                                      mRecyclerView;
    private YCLChuChangWeightFragmentDetailRecycleViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jinchang_query_detail);
        initView();
        initDate();
    }

    private void initDate() {
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
//        String jinchangDetailData = URL.getYclChuchangDetailData(mDataBean.getId()+"",mDataBean.getGuobangleibie(),mDataBean.getCailiaoNo(),mDataBean.getGongyingshangdanweibianma(),String.valueOf(mDataBean.getPici()),
//                mDataBean.getShebeibianhao(),mDataBean.getJcmin(),mDataBean.getJcmax(),String.valueOf(mDataBean.getCcmin()),String.valueOf(mDataBean.getCcmax()));
//        Log.e("guobangleibie",mDataBean.getGuobangleibie());
//        Log.e(TAG,"url=:"+jinchangDetailData);
//        return URL.getYclChuchangDetailData(mDataBean.getId()+"",mDataBean.getGuobangleibie(),mDataBean.getCailiaoNo(),mDataBean.getGongyingshangdanweibianma(),String.valueOf(mDataBean.getPici()),
//                mDataBean.getShebeibianhao(),mDataBean.getJcmin(),mDataBean.getJcmax(),String.valueOf(mDataBean.getCcmin()),String.valueOf(mDataBean.getCcmax()));
        String index = "1";
        String size ="10";
        String jgdm = "XKZW01SG04BH01";
        String status = "21";
        String begintime = "2017-08-18";
        String endtime = "2017-08-21";

        return URL.getPAGE_LIST(index,size,jgdm,status,begintime,endtime);
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
            if (jsonObject.opt("code").equals("1")) {
                data = mGson.fromJson(response, listdata.class);
                if (null != data) {
                    if (data.getMessage().equals("3")) {
                        mPageStateLayout.showContent();
 //                       setAdapter();
                        Log.e("pagelist",data.getMessage());
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

//    private void setAdapter() {
//
//        // 设置显示数据
//
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        //设置动画与适配器
//        SlideInLeftAnimationAdapter mSlideInLeftAnimationAdapter = new SlideInLeftAnimationAdapter(mAdapter = new YCLChuChangWeightFragmentDetailRecycleViewAdapter(this, data.getData()));
//        mSlideInLeftAnimationAdapter.setFirstOnly(true);
//        mSlideInLeftAnimationAdapter.setDuration(500);
//        mSlideInLeftAnimationAdapter.setInterpolator(new OvershootInterpolator(.5f));
//        ScaleInAnimationAdapter mScaleInAnimationAdapter = new ScaleInAnimationAdapter(mSlideInLeftAnimationAdapter);
//        mScaleInAnimationAdapter.setFirstOnly(true);
//        mRecyclerView.setAdapter(mScaleInAnimationAdapter);
//
//    }

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



    private void initView() {
        mDataBean = (YCLChuChangWeightFragmentListData.DataEntity) getIntent().getSerializableExtra("chuchangquerydetail");
        mToolbar = (Toolbar) findViewById(R.id.toolbar_toolbar);
        mNestedScrollView = (NestedScrollView) findViewById(R.id.nsv_jinchang_query_detail_activity);
        mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.ptr_jinchang_query_detail_activity);
        mPageStateLayout = (PageStateLayout) findViewById(R.id.psl_jinchang_query_detail_activity);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_jinchang_query_detail_activity);


    }

    private void setToolbarTitle() {
        if (null != mToolbar && null != BaseApplication.mDepartmentData && !TextUtils.isEmpty(BaseApplication.mDepartmentData.departmentName)) {
            StringBuffer sb = new StringBuffer(BaseApplication.mDepartmentData.departmentName + " > ");
            sb.append(getString(R.string.banhezhan) + " > ");
            sb.append("出厂材料过磅" + " > ");
            sb.append(getString(R.string.detail)).trimToSize();
            mToolbar.setTitle(sb.toString());
        }
    }

}
