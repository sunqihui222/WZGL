package com.shtoone.shtw.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shtoone.shtw.R;
import com.shtoone.shtw.bean.JobOrderFinshData;
import com.shtoone.shtw.bean.JobOrderUnfinshData;
import com.shtoone.shtw.ui.MyProgressBar;

import java.util.List;

/**
 * Created by Administrator on 2017/8/22.
 */

public class JobOrderFinshFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context                            context;
    private OnItemClickListener                mOnItemClickListener;
    private List<JobOrderFinshData.DataEntity> itemsData;
    private Resources                          mResources;

    public enum ITEM_TYPE {
        TYPE_ITEM, TYPE_FOOTER
    }



    public JobOrderFinshFragmentAdapter(Context context, List<JobOrderFinshData.DataEntity> itemsData) {
        this.context = context;
        this.itemsData = itemsData;
        mResources = context.getResources();
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == JobOrderFinshFragmentAdapter.ITEM_TYPE.TYPE_ITEM.ordinal()) {
            return new JobOrderFinshFragmentAdapter.ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recyclerview_produced_job_order, parent, false));
        } else if (viewType == JobOrderFinshFragmentAdapter.ITEM_TYPE.TYPE_FOOTER.ordinal()) {
            return new JobOrderFinshFragmentAdapter.FootViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerview_item_foot, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ItemViewHolder){
                ItemViewHolder mItemViewHolder = (ItemViewHolder) holder;

                JobOrderFinshData.DataEntity item = itemsData.get(position);
                if (item.getZhuangtai().equals("0")){
                    mItemViewHolder.tvState.setText("未生成");
                }else if (item.getZhuangtai().equals("1")){
                    mItemViewHolder.tvState.setText("已生成");
                }else if (item.getZhuangtai().equals("2")){
                    mItemViewHolder.tvState.setText("生产中");
                }else if (item.getZhuangtai().equals("3")){
                    mItemViewHolder.tvState.setText("已完成");
                }else if (item.getZhuangtai().equals("-1")){
                    mItemViewHolder.tvState.setText("未提交");
                }
                mItemViewHolder.tvOpenDate.setText(item.getKaipanriqi());
                mItemViewHolder.tvCastingParts.setText(item.getJzbw());
                mItemViewHolder.tvRealVolume.setText(item.getShijifangliang());
                mItemViewHolder.tvjiecao.setText(item.getJiechao());
                mItemViewHolder.tvTaskId.setText(item.getRenwuno());
                mItemViewHolder.tvPeiBiNo.setText(item.getSgphbno());
                mItemViewHolder.tvDesignStrength.setText(item.getShuinibiaohao());
                mItemViewHolder.tvPlanVolume.setText(item.getJihuafangliang());
                double v = Double.parseDouble(item.getBaifenbi()) * 100;
                int jindu = (int)v;
                mItemViewHolder.pb_jindu.setProgress(jindu);

                if (mOnItemClickListener != null) {
                    mItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int position = holder.getLayoutPosition();
                            mOnItemClickListener.onItemClick(holder.itemView, position);
                        }
                    });
                }

            }

    }

    @Override
    public int getItemCount() {
        if (itemsData != null && itemsData.size() > 0) {
            //这里的10是根据分页查询，一页该显示的条数
            if (itemsData.size() > 4) {
                return itemsData.size() + 1;
            } else {
                return itemsData.size();
            }
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItemCount() > 4 && position + 1 == getItemCount()) {
            return JobOrderFinshFragmentAdapter.ITEM_TYPE.TYPE_FOOTER.ordinal();
        } else {
            return JobOrderFinshFragmentAdapter.ITEM_TYPE.TYPE_ITEM.ordinal();
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvState;
        TextView tvOpenDate;
        TextView tvCastingParts;
        TextView tvRealVolume;
        TextView tvjiecao;
        TextView tvTaskId;
        TextView tvPeiBiNo;
        TextView tvDesignStrength;
        TextView tvPlanVolume;
        MyProgressBar pb_jindu;
        ImageView mImageView;

        public ItemViewHolder(View view) {
            super(view);
            tvState = (TextView) view.findViewById(R.id.tvState);
            tvOpenDate = (TextView) view.findViewById(R.id.tvOpenDate);
            tvCastingParts = (TextView) view.findViewById(R.id.tvCastingParts);
            tvRealVolume = (TextView) view.findViewById(R.id.tvRealVolume);
            tvjiecao = (TextView) view.findViewById(R.id.tvjiecao);
            tvTaskId = (TextView) view.findViewById(R.id.tvTaskId);
            tvPeiBiNo = (TextView) view.findViewById(R.id.tvPeiBiNo);
            tvDesignStrength = (TextView) view.findViewById(R.id.tvDesignStrength);
            tvPlanVolume = (TextView) view.findViewById(R.id.tvPlanVolume);
            pb_jindu = (MyProgressBar) view.findViewById(R.id.executeProgress);
            mImageView = (ImageView) view.findViewById(R.id.iv_right_item_rv_main_fragment);
        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(View view) {
            super(view);
        }
    }
}
