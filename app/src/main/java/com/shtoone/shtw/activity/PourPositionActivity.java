package com.shtoone.shtw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.google.gson.Gson;
import com.sdsmdg.tastytoast.TastyToast;
import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;
import com.shtoone.shtw.adapter.OnItemClickListener;
import com.shtoone.shtw.adapter.PourPositionActivityRecylerView;
import com.shtoone.shtw.bean.JobOrderUnfinshData;
import com.shtoone.shtw.bean.ParametersData;
import com.shtoone.shtw.bean.PourPositionData;
import com.shtoone.shtw.ui.DividerItemDecoration;
import com.shtoone.shtw.ui.PageStateLayout;
import com.shtoone.shtw.utils.URL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;

import static com.shtoone.shtw.BaseApplication.mDepartmentData;

/**
 * Created by Administrator on 2017/8/22.
 */
public class PourPositionActivity extends BaseActivity{

    private static final String TAG = PourPositionActivity.class.getSimpleName();
    private Toolbar                         mToolbar;
    private PtrFrameLayout                  mPtrFrameLayout;
    private RecyclerView                    mRecyclerView;
    private PageStateLayout                 mPageStateLayout;
    private Gson                            mGson;
    private PourPositionData                data;
    private List<PourPositionData.DataBean> listData;
    private LinearLayoutManager             mLinearLayoutManager;
    private PourPositionActivityRecylerView mAdapter;
    private ParametersData                  mParametersData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pour_position);
        initView();
        initData();
    }

    public void initView(){
        mToolbar = (Toolbar)findViewById(R.id.toolbar_toolbar);
        mPtrFrameLayout = (PtrFrameLayout)findViewById(R.id.ptrframelayout);
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        mPageStateLayout = (PageStateLayout)findViewById(R.id.pagestatelayout);
    }

    public void initData(){
        listData = new ArrayList<>();
        mParametersData = (ParametersData) BaseApplication.parametersData.clone();
        mGson = new Gson();
        setToolbarTitle();
        initPageStateLayout(mPageStateLayout);
        initPtrFrameLayout(mPtrFrameLayout);
    }

    @Override
    public String createRefreshULR() {
        mParametersData.currentPage = "1";//默认都是第一页
        String currentPage = "";
        currentPage = mParametersData.currentPage = "1";
        return URL.getPourPosData(getIntent().getStringExtra("departId"),currentPage);
    }

    @Override
    public String createLoadMoreULR() {
        mParametersData.currentPage = (Integer.parseInt(mParametersData.currentPage) + 1) + "";//默认都是第一页
        String currentPage = "";
        currentPage = mParametersData.currentPage;
        return URL.getPourPosData(getIntent().getStringExtra("departId"),currentPage);
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
                data = mGson.fromJson(response, PourPositionData.class);
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
    public void loadMoreSuccess(String response) {
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
                data = mGson.fromJson(response, PourPositionData.class);
                if (null != data) {
                    if (data.getData().size() > 0) {
                        if (null != listData) {
                            listData.addAll(data.getData());
                            if (listData.size() > 0) {
                                mPageStateLayout.showContent();
                                mAdapter.notifyDataSetChanged();
                            } else {
                                TastyToast.makeText(getApplicationContext(), "无更多数据!", TastyToast.LENGTH_SHORT, TastyToast.INFO);
                                mParametersData.currentPage = (Integer.parseInt(mParametersData.currentPage) - 1) + "";
                                mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                            }
                        } else {
                            TastyToast.makeText(getApplicationContext(), "数据异常!", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                            mParametersData.currentPage = (Integer.parseInt(mParametersData.currentPage) - 1) + "";
                            mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                        }
                    } else {
                        TastyToast.makeText(getApplicationContext(), "无更多数据!", TastyToast.LENGTH_SHORT, TastyToast.INFO);
                        mParametersData.currentPage = (Integer.parseInt(mParametersData.currentPage) - 1) + "";
                        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                    }
                } else {
                    TastyToast.makeText(getApplicationContext(), "解析异常!", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    mParametersData.currentPage = (Integer.parseInt(mParametersData.currentPage) - 1) + "";
                    mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                }
            } else {
                TastyToast.makeText(getApplicationContext(), "无更多数据!", TastyToast.LENGTH_SHORT, TastyToast.INFO);
                mParametersData.currentPage = (Integer.parseInt(mParametersData.currentPage) - 1) + "";
                mAdapter.notifyItemRemoved(mAdapter.getItemCount());
            }
        } else {
            //提示返回数据异常，展示错误页面
            TastyToast.makeText(getApplicationContext(), "数据异常!", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            mParametersData.currentPage = (Integer.parseInt(mParametersData.currentPage) - 1) + "";
            mAdapter.notifyItemRemoved(mAdapter.getItemCount());
        }


    }

    public void setAdapter(){

        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        //设置动画与适配器
        SlideInLeftAnimationAdapter mSlideInLeftAnimationAdapter = new SlideInLeftAnimationAdapter(mAdapter = new PourPositionActivityRecylerView(this, listData));
        mSlideInLeftAnimationAdapter.setFirstOnly(true);
        mSlideInLeftAnimationAdapter.setDuration(500);
        mSlideInLeftAnimationAdapter.setInterpolator(new OvershootInterpolator(.5f));
        ScaleInAnimationAdapter mScaleInAnimationAdapter = new ScaleInAnimationAdapter(mSlideInLeftAnimationAdapter);
        mScaleInAnimationAdapter.setFirstOnly(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setAdapter(mScaleInAnimationAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("pourposition",listData.get(position).getProjectname());
                setResult(22, intent);
                onBackPressed();
            }
        });
    }

    @Override
    public boolean isCanDoRefresh() {
        Log.e(TAG,"--------isCanDoRefresh----------");
        //判断是哪种状态的页面，都让其可下拉
        if (mPageStateLayout.isShowContent) {
            //判断RecyclerView是否在在顶部，在顶部则允许滑动下拉刷新
            if (null != mRecyclerView) {
                if (mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                    LinearLayoutManager lm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                    int position = lm.findFirstVisibleItemPosition();
                    if (position >= 0) {
                        if (lm.findViewByPosition(position).getTop() > 0 && position == 0) {
                            return true;
                        }
                    }
                }
            } else {
                return true;
            }
            return false;
        } else {
            return true;
        }
    }

    private void setToolbarTitle() {
        if (null != mToolbar && null != mDepartmentData && !TextUtils.isEmpty(mDepartmentData.departmentName)) {
            StringBuffer sb = new StringBuffer(getString(R.string.engineering_department)+ " > ");
            sb.append(getString(R.string.pour_position)).trimToSize();
            mToolbar.setTitle(sb.toString());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // 添加返回过渡动画.
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
