package com.shtoone.shtw.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shtoone.shtw.R;
import com.shtoone.shtw.bean.PourPositionData;

import java.util.List;

/**
 * Created by Administrator on 2017/8/22.
 */
public class PourPositionActivityRecylerView  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final String TAG = PourPositionActivityRecylerView.class.getSimpleName();
    private Context context;
    private List<PourPositionData.DataBean> listData;
    private Resources mResources;
    private OnItemClickListener mOnItemClickListener;

    public PourPositionActivityRecylerView(Context context, List<PourPositionData.DataBean> listData) {
        super();
        this.context = context;
        this.listData = listData;
        mResources = context.getResources();
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PourPositionActivityRecylerView.ItemViewHolder) {
            PourPositionActivityRecylerView.ItemViewHolder mItemViewHolder = (PourPositionActivityRecylerView.ItemViewHolder) holder;
            PourPositionData.DataBean item = listData.get(position);
            mItemViewHolder.tv_pour_position.setText(item.getProjectname());

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
        if (listData != null && listData.size() > 0) {
            return listData.size();
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PourPositionActivityRecylerView.ItemViewHolder holder = new PourPositionActivityRecylerView.ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recylerview_pour_position_activity, parent, false));
        return holder;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tv_pour_position;

        public ItemViewHolder(View view) {
            super(view);
            tv_pour_position = (TextView) view.findViewById(R.id.tv_pour_position_item);

        }
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
