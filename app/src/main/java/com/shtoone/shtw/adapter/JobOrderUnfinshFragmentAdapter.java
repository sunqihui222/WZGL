package com.shtoone.shtw.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.shtoone.shtw.bean.JobOrderUnfinshData;

import java.util.List;

/**
 * Created by Administrator on 2017/8/22.
 */

public class JobOrderUnfinshFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context                              context;
    private OnItemClickListener                  mOnItemClickListener;
    private List<JobOrderUnfinshData.DataEntity> itemsData;
    private Resources                            mResources;

    public enum ITEM_TYPE {
        TYPE_ITEM, TYPE_FOOTER
    }



    public JobOrderUnfinshFragmentAdapter(Context context, List<JobOrderUnfinshData.DataEntity> itemsData) {
        this.context = context;
        this.itemsData = itemsData;
        mResources = context.getResources();
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
