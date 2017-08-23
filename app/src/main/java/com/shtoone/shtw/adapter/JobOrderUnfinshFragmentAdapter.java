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
        if (viewType == JobOrderUnfinshFragmentAdapter.ITEM_TYPE.TYPE_ITEM.ordinal()) {
            return new JobOrderUnfinshFragmentAdapter.ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recyclerview_unproduced_job_order, parent, false));
        } else if (viewType == JobOrderUnfinshFragmentAdapter.ITEM_TYPE.TYPE_FOOTER.ordinal()) {
            return new JobOrderUnfinshFragmentAdapter.FootViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerview_item_foot, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ItemViewHolder){
                ItemViewHolder mItemViewHolder = (ItemViewHolder) holder;

                JobOrderUnfinshData.DataEntity item = itemsData.get(position);
                if (item.getZhuangtai().equals("0")){
                    mItemViewHolder.tvState.setText("未配料");
                }else if (item.getZhuangtai().equals("1")){
                    mItemViewHolder.tvState.setText("已配料");
                }else if (item.getZhuangtai().equals("2")){
                    mItemViewHolder.tvState.setText("生产中");
                }else if (item.getZhuangtai().equals("3")){
                    mItemViewHolder.tvState.setText("已完成");
                }else if (item.getZhuangtai().equals("-1")){
                    mItemViewHolder.tvState.setText("未提交");
                }
                mItemViewHolder.tvOpenDate.setText(item.getKaipanriqi());
                mItemViewHolder.tvProjectName.setText(item.getGcmc());
                mItemViewHolder.tvCastingParts.setText(item.getJzbw());
                mItemViewHolder.tvCreateDate.setText(item.getCreatetime());
                mItemViewHolder.tvCreatePerson.setText(item.getCreateperson());
                mItemViewHolder.tvTaskId.setText(item.getRenwuno());
                mItemViewHolder.tvPeiBiNo.setText(item.getSgphbno());
                mItemViewHolder.tvDesignStrength.setText(item.getShuinibiaohao());
                mItemViewHolder.tvPlanVolume.setText(item.getJihuafangliang());

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
            return JobOrderUnfinshFragmentAdapter.ITEM_TYPE.TYPE_FOOTER.ordinal();
        } else {
            return JobOrderUnfinshFragmentAdapter.ITEM_TYPE.TYPE_ITEM.ordinal();
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvState;
        TextView tvOpenDate;
        TextView tvProjectName;
        TextView tvCastingParts;
        TextView tvCreateDate;
        TextView tvCreatePerson;
        TextView tvTaskId;
        TextView tvPeiBiNo;
        TextView tvDesignStrength;
        TextView tvPlanVolume;
        ImageView mImageView;

        public ItemViewHolder(View view) {
            super(view);
            tvState = (TextView) view.findViewById(R.id.tvState);
            tvOpenDate = (TextView) view.findViewById(R.id.tvOpenDate);
            tvProjectName = (TextView) view.findViewById(R.id.tvProjectName);
            tvCastingParts = (TextView) view.findViewById(R.id.tvCastingParts);
            tvCreateDate = (TextView) view.findViewById(R.id.tvCreateDate);
            tvCreatePerson = (TextView) view.findViewById(R.id.tvCreatePerson);
            tvTaskId = (TextView) view.findViewById(R.id.tvTaskId);
            tvPeiBiNo = (TextView) view.findViewById(R.id.tvPeiBiNo);
            tvDesignStrength = (TextView) view.findViewById(R.id.tvDesignStrength);
            tvPlanVolume = (TextView) view.findViewById(R.id.tvPlanVolume);
            mImageView = (ImageView) view.findViewById(R.id.iv_right_item_rv_main_fragment);
        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(View view) {
            super(view);
        }
    }
}
