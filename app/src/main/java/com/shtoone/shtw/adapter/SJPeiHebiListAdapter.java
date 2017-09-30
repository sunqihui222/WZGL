package com.shtoone.shtw.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shtoone.shtw.R;
import com.shtoone.shtw.bean.PeiliaoTongzhidanFragmentListData;
import com.shtoone.shtw.bean.SJPeiHebiData;
import com.shtoone.shtw.ui.SlantedTextView;

import java.util.List;

/**
 * Created by Administrator on 2017/9/30.
 */

public class SJPeiHebiListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final String TAG = SJPeiHebiListAdapter.class.getSimpleName();
    private Context             context;
    private OnItemClickListener mOnItemClickListener;
    private Resources           mResources;
    private List<SJPeiHebiData.DataEntity> itemsData;

    public enum ITEM_TYPE {
        TYPE_ITEM, TYPE_FOOTER
    }

    public SJPeiHebiListAdapter(Context context, List<SJPeiHebiData.DataEntity> itemsData) {
        this.context = context;
        this.itemsData = itemsData;
        mResources = context.getResources();
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof SJPeiHebiListAdapter.ItemViewHolder) {
            SJPeiHebiListAdapter.ItemViewHolder mItemViewHolder = (SJPeiHebiListAdapter.ItemViewHolder) holder;
            mItemViewHolder.cv.setCardBackgroundColor(position % 2 == 0 ? mResources.getColor(R.color.material_teal_50) : mResources.getColor(R.color.material_blue_50));

            SJPeiHebiData.DataEntity item = itemsData.get(position);
            Log.e(TAG, "item=:"+ item.toString() );
            mItemViewHolder.tv_depart.setText(item.getDepartname());
            mItemViewHolder.tv_phbNo.setText(item.getLlphbno());
            mItemViewHolder.tv_time.setText(item.getCreatedatetime());
            mItemViewHolder.tv_sjqd.setText(item.getSjqd());
            mItemViewHolder.tv_sjb.setText(item.getShuijiaobi());
            if (item.getZhuangtai().equals("-1")){
                mItemViewHolder.stv_sjpeihebi.setText("未提交");
            }else if (item.getZhuangtai().equals("0")){
                mItemViewHolder.stv_sjpeihebi.setText("提交");
            }




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
            return SJPeiHebiListAdapter.ITEM_TYPE.TYPE_FOOTER.ordinal();
        } else {
            return SJPeiHebiListAdapter.ITEM_TYPE.TYPE_ITEM.ordinal();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == SJPeiHebiListAdapter.ITEM_TYPE.TYPE_ITEM.ordinal()) {
            return new SJPeiHebiListAdapter.ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recyclerview_sjpeihebi_fragment, parent, false));
        } else if (viewType == SJPeiHebiListAdapter.ITEM_TYPE.TYPE_FOOTER.ordinal()) {
            return new SJPeiHebiListAdapter.FootViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerview_item_foot, parent, false));
        }
        return null;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView tv_depart;
        TextView tv_phbNo;
        TextView tv_time;
        TextView tv_sjqd;
        TextView tv_sjb;
        SlantedTextView stv_sjpeihebi;

        public ItemViewHolder(View view) {
            super(view);
            cv = (CardView) view.findViewById(R.id.cv_item_recyclerview_sjpeihebi);
            tv_depart = (TextView) view.findViewById(R.id.tv0_item_recyclerview_sjpeihebi);
            tv_phbNo = (TextView) view.findViewById(R.id.tv1_item_recyclerview_sjpeihebi);
            tv_time = (TextView) view.findViewById(R.id.tv4_item_recyclerview_sjpeihebi);
            tv_sjqd = (TextView) view.findViewById(R.id.tv5_item_recyclerview_sjpeihebi);
            tv_sjb = (TextView) view.findViewById(R.id.tv7_item_recyclerview_sjpeihebi);
            stv_sjpeihebi = (SlantedTextView) view.findViewById(R.id.stv_sjpeihebi);
        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(View view) {
            super(view);
        }
    }
}
