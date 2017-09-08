package com.shtoone.shtw.fragment.engineeringactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.sdsmdg.tastytoast.TastyToast;
import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.adapter.JobOrderUnfinshFragmentAdapter;
import com.shtoone.shtw.adapter.OnItemDelClickListener;
import com.shtoone.shtw.bean.DepartmentData;
import com.shtoone.shtw.bean.JobOrderUnfinshData;
import com.shtoone.shtw.bean.ParametersData;
import com.shtoone.shtw.bean.UserInfoData;
import com.shtoone.shtw.event.EventData;
import com.shtoone.shtw.fragment.base.BaseLazyFragment;
import com.shtoone.shtw.ui.PageStateLayout;
import com.shtoone.shtw.utils.ConstantsUtils;
import com.shtoone.shtw.utils.HttpUtils;
import com.shtoone.shtw.utils.NetworkUtils;
import com.shtoone.shtw.utils.URL;
import com.socks.library.KLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.srain.cube.views.ptr.PtrFrameLayout;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;

import static com.shtoone.shtw.BaseApplication.mDepartmentData;

/**
 * Created by Administrator on 2017/8/22.
 */

public class JobOrderUnFinishFragment extends BaseLazyFragment {
    private PtrFrameLayout mPtrFrameLayout;
    private RecyclerView mRecyclerView;
    private JobOrderUnfinshFragmentAdapter mAdapter;
    private JobOrderUnfinshData itemsData;
    private FloatingActionButton fab;
    private boolean isRegistered = false;
    private PageStateLayout mPageStateLayout;
    private Gson mGson;
    private boolean isLoading;
    private List<JobOrderUnfinshData.DataEntity> listData;

    private ParametersData mParametersData;
    private LinearLayoutManager mLinearLayoutManager;
    private int lastVisibleItemPosition;
    private ScaleInAnimationAdapter mScaleInAnimationAdapter;
    private String id;
    private UserInfoData mUserInfoData;

    public static JobOrderUnFinishFragment newInstance() {
        return new JobOrderUnFinishFragment();
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {

        initData();
    }

    private void initData() {
        if (null != BaseApplication.mDepartmentData && !TextUtils.isEmpty(BaseApplication.mDepartmentData.departmentName)) {
            mDepartmentData = new DepartmentData(BaseApplication.mUserInfoData.getDepartId(), BaseApplication.mUserInfoData.getDepartName(), ConstantsUtils.JOBORDERUNFINSH);
            mParametersData = (ParametersData) BaseApplication.parametersData.clone();
            mParametersData.userGroupID = BaseApplication.mDepartmentData.departmentID;
            mParametersData.username = BaseApplication.mDepartmentData.departmentName;
        }

        mParametersData.fromTo = ConstantsUtils.JOBORDERUNFINSH;

        mGson = new Gson();
        listData = new ArrayList<>();

        mLinearLayoutManager = new LinearLayoutManager(_mActivity);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mUserInfoData = BaseApplication.mUserInfoData;

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUserInfoData.getQuanxian().isWZGCB()) {
                    fab.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(_mActivity, TaskListNewEditActivity.class);
                    intent.putExtra("username", mParametersData.username);
                    startActivity(intent);
                } else {
                    fab.setVisibility(View.GONE);
                }
            }
        });

        //设置动画与适配器
        SlideInLeftAnimationAdapter mSlideInLeftAnimationAdapter = new SlideInLeftAnimationAdapter(mAdapter = new JobOrderUnfinshFragmentAdapter(_mActivity, listData));
        mSlideInLeftAnimationAdapter.setDuration(500);
        mSlideInLeftAnimationAdapter.setInterpolator(new OvershootInterpolator(.5f));
        mScaleInAnimationAdapter = new ScaleInAnimationAdapter(mSlideInLeftAnimationAdapter);
        mRecyclerView.setAdapter(mScaleInAnimationAdapter);
        if (mUserInfoData.getQuanxian().isWZGCB()) {
            mAdapter.setOnItemClickListener(new OnItemDelClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    // 实现局部界面刷新, 这个view就是被点击的item布局对象
                    changeReadedState(view);
                    jump2TaskListDetailActivity(position);
                }

                @Override
                public void onRightClick(View view, int position) {
                    if (!TextUtils.isEmpty(listData.get(position).getId())) {
                        if (listData.get(position).getZhuangtai().equals("-1"))
                        {
                            //弹出对话框，确定提交
                            id = listData.get(position).getId();
                            new MaterialDialog.Builder(getActivity())
                                    .title("删除")
                                    .content("请问您确定无误删除吗？")
                                    .positiveText("确定")
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            MaterialDialog progressDialog = new MaterialDialog.Builder(getActivity())
                                                    .title("删除")
                                                    .content("正在删除中，请稍等……")
                                                    .progress(true, 0)
                                                    .progressIndeterminateStyle(true)
                                                    .cancelable(false)
                                                    .show();
                                            joborderDel(progressDialog, id);
                                        }
                                    })
                                    .negativeText("放弃")
                                    .show();
                        }else {
                            Toast.makeText(getContext(),"只有未提交能够删除",Toast.LENGTH_SHORT).show();
                        }

                    }

                }

                @Override
                public void onBelowClick(View view, int position) {
                    if (!TextUtils.isEmpty(listData.get(position).getId())) {
                        if (listData.get(position).getZhuangtai().equals("-1"))
                        {
                            //弹出对话框，确定提交
                            id = listData.get(position).getId();
                            new MaterialDialog.Builder(getActivity())
                                    .title("提交")
                                    .content("请问您确定提交吗？")
                                    .positiveText("确定")
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            MaterialDialog progressDialog = new MaterialDialog.Builder(getActivity())
                                                    .title("提交")
                                                    .content("正在提交中，请稍等……")
                                                    .progress(true, 0)
                                                    .progressIndeterminateStyle(true)
                                                    .cancelable(false)
                                                    .show();
                                            joborderSubmit(progressDialog, id, mParametersData.username);
                                        }
                                    })
                                    .negativeText("放弃")
                                    .show();
                        }else {
                            Toast.makeText(getContext(),"任务单已提交过",Toast.LENGTH_SHORT).show();
                        }

                    }

                }
            });
        }

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

                if (dy > 5) {
                    fab.hide();
                } else if (dy < -5) {
                    fab.show();
                }
            }
        });

        initPageStateLayout(mPageStateLayout);
        initPtrFrameLayout(mPtrFrameLayout);
    }

    private void joborderSubmit(final MaterialDialog progressDialog, String id, String name) {

        String url = null;
        try {
            url = URL.getJOBORDERSUBMIT(id, URLEncoder.encode(name, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpUtils.getRequest(url, new HttpUtils.HttpListener() {
            @Override
            public void onSuccess(String response) {
                KLog.json(response);
                progressDialog.dismiss();
                if (!TextUtils.isEmpty(response)) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        TastyToast.makeText(getContext(), "解析异常！", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    }

                    if (jsonObject.optBoolean("success")) {

                        BaseApplication.bus.post(new EventData(ConstantsUtils.REFRESH));
                        TastyToast.makeText(getContext(), "上传成功!", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                        mPtrFrameLayout.autoRefresh(true);
                    } else {
                        TastyToast.makeText(getContext(), "上传失败，请重试！", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    }

                } else {
                    TastyToast.makeText(getContext(), "上传失败，请重试！", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                }
            }

            @Override
            public void onFailed(VolleyError error) {
                progressDialog.dismiss();
                if (!NetworkUtils.isConnected(getActivity())) {
                    //提示网络异常,让用户点击设置网络，
                    View view = getActivity().getWindow().getDecorView();
                    Snackbar.make(view, "当前网络已断开！", Snackbar.LENGTH_LONG)
                            .setAction("设置网络", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // 跳转到系统的网络设置界面
                                    NetworkUtils.openSetting(getActivity());
                                }
                            }).show();
                } else {
                    //服务器异常，展示错误页面，点击刷新
                    TastyToast.makeText(getActivity(), "服务器异常！", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                }
            }
        });
    }


    private void joborderDel(final MaterialDialog progressDialog, String id) {
        Map<String, String> paramsMap = new HashMap<String, String>();
        try {
            paramsMap.put("id", id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpUtils.postRequest(URL.JOBORDER_UNFINSHDEL, paramsMap, new HttpUtils.HttpListener() {
            @Override
            public void onSuccess(String response) {
                progressDialog.dismiss();

                KLog.json(response);
                if (!TextUtils.isEmpty(response)) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        TastyToast.makeText(getContext(), "解析异常！", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    }

                    if (jsonObject.optBoolean("success")) {

                        BaseApplication.bus.post(new EventData(ConstantsUtils.REFRESH));
                        TastyToast.makeText(getContext(), "上传成功!", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                        mPtrFrameLayout.autoRefresh(true);
                    } else {
                        TastyToast.makeText(getContext(), "上传失败，请重试！", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    }

                } else {
                    TastyToast.makeText(getContext(), "上传失败，请重试！", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                }
            }

            @Override
            public void onFailed(VolleyError error) {
                progressDialog.dismiss();
                if (!NetworkUtils.isConnected(getActivity())) {
                    //提示网络异常,让用户点击设置网络，
                    View view = getActivity().getWindow().getDecorView();
                    Snackbar.make(view, "当前网络已断开！", Snackbar.LENGTH_LONG)
                            .setAction("设置网络", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // 跳转到系统的网络设置界面
                                    NetworkUtils.openSetting(getActivity());
                                }
                            }).show();
                } else {
                    //服务器异常，展示错误页面，点击刷新
                    TastyToast.makeText(getActivity(), "服务器异常！", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                }
            }
        });
    }

    @Override
    public boolean isCanDoRefresh() {
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

    @Override
    public String createRefreshULR() {
        mPageStateLayout.showLoading();
        mParametersData.currentPage = "1";//默认都是第一页
        String userGroupID = "";
        String startDateTime = "";
        String endDateTime = "";
        String currentPage = "";

        if (null != mParametersData) {
            userGroupID = mParametersData.userGroupID;
            startDateTime = mParametersData.startDateTime;
            endDateTime = mParametersData.endDateTime;
            currentPage = mParametersData.currentPage;
        }
        String state = "0";
        if (null != listData) {
            listData.clear();
        }
        return URL.getJobOrderUnfinsh(userGroupID, state, startDateTime, endDateTime, currentPage);
    }

    @Override
    public String createLoadMoreULR() {
        mParametersData.currentPage = (Integer.parseInt(mParametersData.currentPage) + 1) + "";//默认都是第一页
        String userGroupID = "";
        String startDateTime = "";
        String endDateTime = "";
        String currentPage = "";
        if (null != mParametersData) {
            userGroupID = mParametersData.userGroupID;
            startDateTime = mParametersData.startDateTime;
            endDateTime = mParametersData.endDateTime;
            currentPage = mParametersData.currentPage;
        }
        String state = "0";
        return URL.getJobOrderUnfinsh(userGroupID, state, startDateTime, endDateTime, currentPage);
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
                itemsData = mGson.fromJson(response, JobOrderUnfinshData.class);
                if (null != itemsData) {
                    if (itemsData.getSuccess() && itemsData.getData().size() > 0) {
                        listData.addAll(itemsData.getData());
                        if (null != listData) {
                            if (listData.size() > 0) {
                                mPageStateLayout.showContent();
                                mRecyclerView.setAdapter(mScaleInAnimationAdapter);
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
                itemsData = mGson.fromJson(response, JobOrderUnfinshData.class);
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
        }
    }

    @Override
    public void onLoadMoreFailed(VolleyError error) {
        super.onLoadMoreFailed(error);
        mParametersData.currentPage = (Integer.parseInt(mParametersData.currentPage) - 1) + "";
        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (!isRegistered) {
            BaseApplication.bus.register(this);
            isRegistered = true;
        }
        View view = inflater.inflate(R.layout.fragment_joborder_unfinsh, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //返回到看见此fragment时，fab显示
        if (mUserInfoData.getQuanxian().isWZGCB()) {
            fab.show();
        }

    }

    private void initView(View view) {
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        mPtrFrameLayout = (PtrFrameLayout) view.findViewById(R.id.ptrframelayout);
        mPageStateLayout = (PageStateLayout) view.findViewById(R.id.pagestatelayout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
    }

    @Override
    public void onPause() {
        super.onPause();
        //防止屏幕旋转后重画时fab显示
        fab.hide();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BaseApplication.bus.unregister(this);
    }

    private void changeReadedState(View view) {
        //此处可以做一些修改点击过的item的样式，方便用户看出哪些已经点击查看过
    }

    private void jump2TaskListDetailActivity(int position) {
        Intent intent = new Intent(_mActivity, TaskListEditActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("tasklistdetail", listData.get(position).getId());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
