package com.shtoone.shtw.fragment.EngineeringDepartment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.EngineerDepartmentActivity;
import com.shtoone.shtw.activity.JobOrderCompoundingActivity;
import com.shtoone.shtw.activity.JobOrderInProductionActvity;
import com.shtoone.shtw.activity.JobOrderProductionActvity;
import com.shtoone.shtw.activity.JobOrderUnCompoundingActivity;
import com.shtoone.shtw.activity.JobOrderUnSubmitActivity;
import com.shtoone.shtw.activity.MaterialConsumeActivity;
import com.shtoone.shtw.activity.TaskListImpQueryActivity;
import com.shtoone.shtw.activity.WZProjectProgressQueryActivity;
import com.shtoone.shtw.activity.YCLChuChangWeightActivity;
import com.shtoone.shtw.activity.YCLJinChangWeightActivity;
import com.shtoone.shtw.activity.base.FBProjectListActivity;
import com.shtoone.shtw.adapter.GridViewAdapter1;
import com.shtoone.shtw.adapter.GridViewAdapter2;
import com.shtoone.shtw.adapter.OnItemClickListener;
import com.shtoone.shtw.adapter.WZProjectProgressQueryAdapter;
import com.shtoone.shtw.bean.DepartmentData;
import com.shtoone.shtw.bean.ParametersData;
import com.shtoone.shtw.bean.TJFXData;
import com.shtoone.shtw.bean.WZProjectProgressQueryData;
import com.shtoone.shtw.fragment.base.BaseLazyFragment;
import com.shtoone.shtw.fragment.engineeringactivity.TaskListNewEditActivity;
import com.shtoone.shtw.ui.PageStateLayout;
import com.shtoone.shtw.utils.AnimationUtils;
import com.shtoone.shtw.utils.ConstantsUtils;
import com.shtoone.shtw.utils.NetworkUtils;
import com.shtoone.shtw.utils.URL;
import com.socks.library.KLog;
import com.squareup.otto.Subscribe;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.srain.cube.views.ptr.PtrFrameLayout;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;

import static com.shtoone.shtw.BaseApplication.mDepartmentData;

/**
 * Created by Administrator on 2017/8/7.
 */

public class WZProjectProgressQueryFragment extends BaseLazyFragment {
    private static final String TAG = "WZProjectProgressQueryFragment";
    private Toolbar mToolbar;
    private PtrFrameLayout mPtrFrameLayout;
    //    private RecyclerView mRecyclerView;
    private WZProjectProgressQueryAdapter mAdapter;
    //    private WZProjectProgressQueryData itemsData;
    private TJFXData itemsData;
    private boolean isRegistered = false;
    private PageStateLayout mPageStateLayout;
    private Gson mGson;
    private boolean isLoading;
    private List<WZProjectProgressQueryData.DataEntity> listData;

    private ParametersData mParametersData;
    private LinearLayoutManager mLinearLayoutManager;
    private int lastVisibleItemPosition;
    private ScaleInAnimationAdapter mScaleInAnimationAdapter;
    private GridView gridView1;
    private String[] titles1 = {"未配料",
            "已配料", "已完工", "生产中", "未提交", "新增"};
    private String[] titles2 = {"任务单查询",
            "进耗分析", "进场台账", "出场台账", "进度查询"};

    private String[] nums1 = new String[6];
    private String[] nums2 = new String[6];
    //    private String[] nums2 = {"101", "100", "102", "109", "111"};
    private GridView gridView2;
    private GridViewAdapter1 adapter1;


    public static WZProjectProgressQueryFragment newInstance() {
        return new WZProjectProgressQueryFragment();
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {
        initData();
    }

    private void initData() {
        if (null != BaseApplication.parametersData) {
            mParametersData = (ParametersData) BaseApplication.parametersData.clone();
            mParametersData.fromTo = ConstantsUtils.WZPROGRESS;
        }
        if (null != BaseApplication.mDepartmentData && !TextUtils.isEmpty(BaseApplication.mDepartmentData.departmentName)) {
            mDepartmentData = new DepartmentData(BaseApplication.mUserInfoData.getDepartId(), BaseApplication.mUserInfoData.getDepartName(), ConstantsUtils.WZPROGRESS);

        }
        BaseApplication.mDepartmentData.departmentID = BaseApplication.mUserInfoData.getDepartId();
        BaseApplication.mDepartmentData.departmentName = BaseApplication.mUserInfoData.getDepartName();


        mGson = new Gson();
        setToolbarTitle();
        //((MainActivity) _mActivity).initToolBar(mToolbar);
        initToolbarBackNavigation(mToolbar);
        mToolbar.inflateMenu(R.menu.menu_hierarchy);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_hierarchy:
                        Intent intent = new Intent(getActivity(), FBProjectListActivity.class);
//                        Log.e(TAG,"跳转到FBProjectListActivity");
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(ConstantsUtils.PARAMETERS, mParametersData);
                        intent.putExtras(bundle);
                        AnimationUtils.startActivity(_mActivity, intent, mToolbar.findViewById(R.id.action_hierarchy), R.color.base_color, 500);
                        break;
                }
                return true;
            }
        });
        listData = new ArrayList<>();
        mLinearLayoutManager = new LinearLayoutManager(_mActivity);
//        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        //设置动画与适配器
        SlideInLeftAnimationAdapter mSlideInLeftAnimationAdapter = new SlideInLeftAnimationAdapter(mAdapter = new WZProjectProgressQueryAdapter(_mActivity, listData));
        mSlideInLeftAnimationAdapter.setDuration(500);
        mSlideInLeftAnimationAdapter.setInterpolator(new OvershootInterpolator(.5f));
        mScaleInAnimationAdapter = new ScaleInAnimationAdapter(mSlideInLeftAnimationAdapter);
//        mRecyclerView.setAdapter(mScaleInAnimationAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // 实现局部界面刷新, 这个view就是被点击的item布局对象
                changeReadedState(view);
                jump2EngineerDepartmentDetailActivity(position);
            }
        });
/*
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //还有一个不完美的地方就是当规定的item个数时，最后一个item在屏幕外滑到可见时，其底部没有footview，这点以后再解决。
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition + 1 == mAdapter.getItemCount() && listData.size() >= 4) {
                    if (!isLoading) {
                        isLoading = true;
                        mRecyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                KLog.e("第" + mParametersData.currentPage + "页");
                                loadMore();
                                KLog.e("上拉加载更多下一页=" + mParametersData.currentPage);
                                isLoading = false;
                            }
                        }, 500);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItemPosition = mLinearLayoutManager.findLastVisibleItemPosition();


            }
        });*/

        initPageStateLayout(mPageStateLayout);
        initPtrFrameLayout(mPtrFrameLayout);
    }

    @Override
    public boolean isCanDoRefresh() {

        return true;

        /********************************************************/
        //判断是哪种状态的页面，都让其可下拉
     /*   if (mPageStateLayout.isShowContent) {
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
        }*/
    }

    @Override
    public String createRefreshULR() {
        mPageStateLayout.showLoading();
        mParametersData.currentPage = "1";//默认都是第一页
        String parentno = "";
        String currentPage = "";

        if (null != mParametersData) {
            currentPage = mParametersData.currentPage;
            parentno = mParametersData.projectno;
        }
        if (null != listData) {
            listData.clear();
        }
//        return URL.getWZprojectprogress(parentno, currentPage);
        return URL.TJFX_URL;
    }

    @Override
    public String createLoadMoreULR() {
        mParametersData.currentPage = (Integer.parseInt(mParametersData.currentPage) + 1) + "";//默认都是第一页
        String parentno = "";
        String currentPage = "";

        if (null != mParametersData) {
            currentPage = mParametersData.currentPage;
            parentno = mParametersData.projectno;
        }

//        return URL.getWZprojectprogress(parentno, currentPage);
        return URL.TJFX_URL;
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
                Log.e(TAG, "response*****>>>>>" + response);
                itemsData = mGson.fromJson(response, TJFXData.class);
                if (null != itemsData) {
                    if (itemsData.isSuccess() && itemsData.getData().size() > 0) {
//                        listData.addAll(itemsData.getData());
                        if (null != listData) {
                            if (itemsData.getData().size() > 0) {
                                mPageStateLayout.showContent();
                                nums1[0] = itemsData.getData().get(0).getNsrCount();
                                nums1[1] = itemsData.getData().get(0).getIsrCount();
                                nums1[2] = itemsData.getData().get(0).getIsshengchancount();
                                nums1[3] = itemsData.getData().get(0).getShengchaningCount();
                                nums1[4] = itemsData.getData().get(0).getNotijiaoCount();

                                //1. 准备数据源
                                List list1 = new ArrayList<Map<String, String>>();
                                for (int i = 0; i < titles1.length; i++) {
                                    Map<String, String> map = new HashMap<String, String>();
                                    map.put("itemTitle", titles1[i]);
                                    if (nums1.length > i) {
                                        map.put("itemNum", nums1[i]);
                                    }
                                    list1.add(map);
                                }
                                //      2.为数据源设置适配器
                                if (adapter1 == null) {
                                    adapter1 = new GridViewAdapter1(getContext(), list1);
                                    gridView1.setAdapter(adapter1);
                                } else {
                                    adapter1.notifyDataSetChanged();
                                }
//                                mRecyclerView.setAdapter(mScaleInAnimationAdapter);
//                                mRecyclerView.setAdapter(mScaleInAnimationAdapter);
                            } else {
                                //提示数据为空，展示空状态
//                                mPageStateLayout.showEmpty();
                            }
                        } else {
                            //提示数据解析异常，展示错误页面
                            mPageStateLayout.showError();
                        }
                    } else {
                        //提示数据为空，展示空状态
//                        mPageStateLayout.showEmpty();
                    }
                } else {
                    //提示数据解析异常，展示错误页面
                    mPageStateLayout.showError();
                }
            } else {
                //提示数据为空，展示空状态
//                mPageStateLayout.showEmpty();
            }
        } else {
            //提示返回数据异常，展示错误页面
            mPageStateLayout.showError();
        }
    }

    @Override
    public void onRefreshFailed(VolleyError error) {
        //提示网络数据异常，展示网络错误页面。此时：1.可能是本机网络有问题，2.可能是服务器问题
        if (!NetworkUtils.isConnected(_mActivity)) {
            //提示网络异常,让用户点击设置网络
            mPageStateLayout.showNetError();
        } else {
            //服务器异常，展示错误页面，点击刷新
            mPageStateLayout.showError();
        }
    }

    @Override
    public void onLoadMoreSuccess(String response) {
      /*  if (!TextUtils.isEmpty(response)) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
                mPageStateLayout.showError();
                return;
            }
            if (jsonObject.optBoolean("success")) {
                itemsData = mGson.fromJson(response, WZProjectProgressQueryData.class);
                if (null != itemsData) {
                    if (itemsData.getSuccess() && itemsData.getData().size() > 0) {
                        if (null != listData) {
                            listData.addAll(itemsData.getData());
                            if (listData.size() > 0) {
                                mPageStateLayout.showContent();
                                mAdapter.notifyDataSetChanged();
                            } else {
                                TastyToast.makeText(_mActivity.getApplicationContext(), "无更多数据!", TastyToast.LENGTH_SHORT, TastyToast.INFO);
                                mParametersData.currentPage = (Integer.parseInt(mParametersData.currentPage) - 1) + "";
                                mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                            }
                        } else {
                            TastyToast.makeText(_mActivity.getApplicationContext(), "数据异常!", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                            mParametersData.currentPage = (Integer.parseInt(mParametersData.currentPage) - 1) + "";
                            mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                        }
                    } else {
                        TastyToast.makeText(_mActivity.getApplicationContext(), "无更多数据!", TastyToast.LENGTH_SHORT, TastyToast.INFO);
                        mParametersData.currentPage = (Integer.parseInt(mParametersData.currentPage) - 1) + "";
                        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                    }
                } else {
                    TastyToast.makeText(_mActivity.getApplicationContext(), "解析异常!", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    mParametersData.currentPage = (Integer.parseInt(mParametersData.currentPage) - 1) + "";
                    mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                }
            } else {
                TastyToast.makeText(_mActivity.getApplicationContext(), "无更多数据!", TastyToast.LENGTH_SHORT, TastyToast.INFO);
                mParametersData.currentPage = (Integer.parseInt(mParametersData.currentPage) - 1) + "";
                mAdapter.notifyItemRemoved(mAdapter.getItemCount());
            }
        } else {
            //提示返回数据异常，展示错误页面
            TastyToast.makeText(_mActivity.getApplicationContext(), "数据异常!", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            mParametersData.currentPage = (Integer.parseInt(mParametersData.currentPage) - 1) + "";
            mAdapter.notifyItemRemoved(mAdapter.getItemCount());
        }*/
    }

    @Override
    public void onLoadMoreFailed(VolleyError error) {
        super.onLoadMoreFailed(error);
        mParametersData.currentPage = (Integer.parseInt(mParametersData.currentPage) - 1) + "";
        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
    }

    @Subscribe
    public void updateSearch(ParametersData mParametersData) {
        if (mParametersData != null) {
            if (mParametersData.fromTo == ConstantsUtils.WZPROGRESS) {
                this.mParametersData = mParametersData;
                //this.mParametersData.projectno = mParametersData.projectno;
                KLog.e("mParametersData:" + mParametersData.projectno);
                mPtrFrameLayout.autoRefresh(true);
            }

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        BaseApplication.bus.register(this);
        View view = inflater.inflate(R.layout.fragment_wzprogress_query, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //返回到看见此fragment时，fab显示
        Log.e("onResume", "WZProjectProgress");
    }

    private void initView(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar_toolbar);
        mPtrFrameLayout = (PtrFrameLayout) view.findViewById(R.id.ptrframelayout);
        mPageStateLayout = (PageStateLayout) view.findViewById(R.id.pagestatelayout);
//        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        /************************************************/
        gridView1 = (GridView) view.findViewById(R.id.gv_wzprogress);
        gridView2 = (GridView) view.findViewById(R.id.gv_tjfx);

        List list2 = new ArrayList<Map<String, String>>();
        for (int i = 0; i < titles1.length-1; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("itemTitle", titles2[i]);
            list2.add(map);
        }
        GridViewAdapter2 adapter2 = new GridViewAdapter2(getContext(), list2);
//      3.将适配过后点数据显示在GridView 上
        gridView2.setAdapter(adapter2);
        // item点击事件处理
        initListener();
    }

    private void initListener() {
        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //
                Intent intent;
                Log.e(TAG, "被点击的position：" + position);
                switch (position) {
                    case 0:
                        intent = new Intent(getActivity(), JobOrderUnCompoundingActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(getActivity(), JobOrderCompoundingActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(getActivity(), JobOrderProductionActvity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(getActivity(), JobOrderInProductionActvity.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(getActivity(), JobOrderUnSubmitActivity.class);
                        startActivity(intent);
                        break;
                    case 5:
                        if (BaseApplication.mUserInfoData.getQuanxian().isWZGCB()) {
                            intent = new Intent(_mActivity, TaskListNewEditActivity.class);
                            intent.putExtra("username", mParametersData.username);
                            startActivity(intent);
                        }
                        break;


                }
            }
        });

        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                Log.e(TAG, "被点击的position：" + position);
                switch (position) {
                    case 0:
                        intent = new Intent(getActivity(), TaskListImpQueryActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(getActivity(), MaterialConsumeActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(getActivity(), YCLJinChangWeightActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(getActivity(), YCLChuChangWeightActivity.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(getActivity(), WZProjectProgressQueryActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }


    @Override
    public void onPause() {
        super.onPause();
        //防止屏幕旋转后重画时fab显示
        Log.e("onPause", "WZProjectProgress");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BaseApplication.bus.unregister(this);
    }


    private void changeReadedState(View view) {
        //此处可以做一些修改点击过的item的样式，方便用户看出哪些已经点击查看过
    }

    //进入EngineerActivity
    private void jump2EngineerDepartmentDetailActivity(int position) {
        Intent intent = new Intent(_mActivity, EngineerDepartmentActivity.class);
        startActivity(intent);
    }

    private void setToolbarTitle() {
        if (null != mToolbar && null != mDepartmentData && !TextUtils.isEmpty(mDepartmentData.departmentName)) {
            StringBuffer sb = new StringBuffer(mDepartmentData.departmentName + " > ");
            sb.append("工程进度查询").trimToSize();
            mToolbar.setTitle(sb.toString());
        }
    }
}
