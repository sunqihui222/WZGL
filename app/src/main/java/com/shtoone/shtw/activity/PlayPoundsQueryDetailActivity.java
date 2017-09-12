package com.shtoone.shtw.activity;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;
import com.shtoone.shtw.bean.EnterPoundsDetailData;
import com.shtoone.shtw.bean.EnterPoundsListData;
import com.shtoone.shtw.bean.PlayPoundsDetailData;

import com.shtoone.shtw.bean.PlayPoundsListData;
import com.shtoone.shtw.ui.PageStateLayout;
import com.shtoone.shtw.utils.NetworkUtils;
import com.shtoone.shtw.utils.URL;

import org.json.JSONException;
import org.json.JSONObject;

import in.srain.cube.views.ptr.PtrFrameLayout;

import static com.shtoone.shtw.BaseApplication.mDepartmentData;

public class PlayPoundsQueryDetailActivity extends BaseActivity {
    private Toolbar mToolbar;
    private NestedScrollView mNestedScrollView;
    private PageStateLayout mPageStateLayout;
    private PtrFrameLayout mPtrFrameLayout;
    private PlayPoundsDetailData playPoundsDetailData;
    private TextView tvProvider, tvWaag, tvWeighman, tvEnterTime, tvPlayTime, tvSilo, tvPici, tvOrderNo, tvPlate, tvRemark;//基本信息
    private TextView tvMaterial, tvRoughWeight, tvPiZhong, tvKouZhong, tvKouLv, tvNetWeight,tvDeviation;//材料明细
    private ImageView ivJCGKPicname,ivJCCPPicname,ivJCHCPPicname,ivJCBFPicname;//进场情况
    private ImageView ivCCGKPicname,ivCCCPPicname,ivCCHCPPicname,ivCCBFPicname;//出场情况
    private TextView tvName;
    private Gson mGson;
    private PlayPoundsListData.DataBean mDataBean;
    private int identificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterpounds_detail);
        initView();
        initDate();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_toolbar);
        mNestedScrollView = (NestedScrollView) findViewById(R.id.nsv_produce_query_detail_activity);
        mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.ptr_produce_query_detail_activity);
        mPageStateLayout = (PageStateLayout) findViewById(R.id.psl_produce_query_detail_activity);
        tvName = (TextView)findViewById(R.id.tvName);
        tvName.setText("过磅类别：");

        //基本信息
        tvProvider = (TextView) findViewById(R.id.tvProvider);//供应商
        tvWaag = (TextView) findViewById(R.id.tvWaag);//地磅名称
        tvWeighman = (TextView) findViewById(R.id.tvWeighman);//司磅员
        tvEnterTime = (TextView) findViewById(R.id.tvEnterTime);//进场时间
        tvPlayTime = (TextView) findViewById(R.id.tvPlayTime);//出场时间
        tvSilo = (TextView) findViewById(R.id.tvSilo);//料仓
        tvPici = (TextView) findViewById(R.id.tvPici);//批次
        tvOrderNo = (TextView) findViewById(R.id.tvOrderNo);//过磅类别
        tvPlate = (TextView) findViewById(R.id.tvPlate);//车牌号
        tvRemark = (TextView) findViewById(R.id.tvRemark);//备注

        //材料明细
        tvMaterial = (TextView) findViewById(R.id.tvMaterial);//材料名称
        tvRoughWeight = (TextView) findViewById(R.id.tvRoughWeight);//毛重
        tvPiZhong = (TextView) findViewById(R.id.tvPiZhong);//皮重
        tvKouZhong = (TextView) findViewById(R.id.tvKouZhong);//扣重
        tvKouLv = (TextView) findViewById(R.id.tvKouLv);//扣率
        tvNetWeight = (TextView) findViewById(R.id.tvNetWeight);//净重
        tvDeviation = (TextView) findViewById(R.id.tvDeviation);//称重偏差

        //进场情况
        ivJCGKPicname = (ImageView)findViewById(R.id.ivJCGKPicname);
        ivJCCPPicname = (ImageView)findViewById(R.id.ivJCCPPicname);
        ivJCHCPPicname = (ImageView)findViewById(R.id.ivJCHCPPicname);
        ivJCBFPicname = (ImageView)findViewById(R.id.ivJCBFPicname);
        //出场情况
        ivCCGKPicname = (ImageView)findViewById(R.id.ivCCGKPicname);
        ivCCCPPicname = (ImageView)findViewById(R.id.ivCCCPPicname);
        ivCCHCPPicname = (ImageView)findViewById(R.id.ivCCHCPPicname);
        ivCCBFPicname = (ImageView)findViewById(R.id.ivCCBFPicname);
    }

    private void initDate() {
        mGson = new Gson();
        mDataBean = (PlayPoundsListData.DataBean) getIntent().getSerializableExtra("playdetail");
        identificationId = Integer.parseInt(mDataBean.getId());
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
        return URL.getPlayPoundsDetail(String.valueOf(identificationId));
    }

    private void setViewData(){
        //基本信息
        PlayPoundsDetailData.DataBean data = playPoundsDetailData.getData();
        tvProvider.setText(data.getGongyingshangname());
        tvWaag.setText(data.getBanhezhanminchen());//地磅名称
        tvWeighman.setText(data.getSibangyuan());//司磅员
        tvEnterTime.setText(data.getJinchangshijian());//进场时间
        tvPlayTime.setText(data.getChuchangshijian());//出场时间
        tvSilo.setText(data.getLiaocang());//料仓
        tvPici.setText(data.getPici());//批次
        if (data.getGuobangleibie().trim().equals("1")){
            tvOrderNo.setText("调拨");//过磅类别
        }else {
            tvOrderNo.setText("废料");
        }
        tvPlate.setText(data.getQianchepai());//车牌号
        tvRemark.setText(data.getRemark());//备注

        //材料明细
        tvMaterial.setText(data.getCailiaoname());//材料名称
        tvRoughWeight.setText(data.getMaozhong());//毛重
        tvPiZhong.setText(data.getPizhong());//皮重
        tvKouZhong.setText(data.getKouzhong());//扣重
        tvKouLv.setText(data.getKoulv());//扣率
        tvNetWeight.setText(data.getJingzhong());//净重
        tvDeviation.setText(data.getChengzhongpiancha());//称重偏差


        //进场情况
        if (data.getJCGKPic().trim() != null){
            Glide.with(getApplicationContext()).load(URL.BaseURL + data.getJCGKPic()).crossFade().into(ivJCGKPicname);
        }
        if (data.getJCCPPic().trim() != null){
            Glide.with(getApplicationContext()).load(URL.BaseURL + data.getJCCPPic()).crossFade().into(ivJCCPPicname);
        }
        if (data.getJCHCPPic().trim() != null){
            Glide.with(getApplicationContext()).load(URL.BaseURL + data.getJCHCPPic()).crossFade().into(ivJCHCPPicname);
        }
       if (URL.BaseURL + data.getJCBFPic().trim() != null){
           Glide.with(getApplicationContext()).load(URL.BaseURL + data.getJCBFPic()).crossFade().into(ivJCBFPicname);
       }

        //出场情况
        if (data.getCCGKPic().trim() != null){
            Glide.with(getApplicationContext()).load(URL.BaseURL + data.getCCGKPic()).crossFade().into(ivCCGKPicname);
        }
        if (data.getCCCPPic().trim() != null){
            Glide.with(getApplicationContext()).load(URL.BaseURL + data.getCCCPPic()).crossFade().into(ivCCCPPicname);
        }
        if (data.getCCHCPPic().trim() != null){
            Glide.with(getApplicationContext()).load(URL.BaseURL + data.getCCHCPPic()).crossFade().into(ivCCHCPPicname);
        }
        if (data.getCCBFPic().trim() != null){
            Glide.with(getApplicationContext()).load(URL.BaseURL + data.getCCBFPic()).crossFade().into(ivCCBFPicname);
        }
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
                playPoundsDetailData = mGson.fromJson(response, PlayPoundsDetailData.class);
                if (null != playPoundsDetailData) {
                    if (playPoundsDetailData.isSuccess()) {
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

    private void setToolbarTitle() {
        if (null != mToolbar && null != mDepartmentData && !TextUtils.isEmpty(mDepartmentData.departmentName)) {
            StringBuffer sb = new StringBuffer(mDepartmentData.departmentName + " > ");
            sb.append(getString(R.string.enter_pounds) + " > ");
            sb.append(getString(R.string.detail)).trimToSize();
            mToolbar.setTitle(sb.toString());
        }
    }
}
