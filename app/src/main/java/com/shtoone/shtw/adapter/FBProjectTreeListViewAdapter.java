package com.shtoone.shtw.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.shtoone.shtw.R;
import com.shtoone.shtw.ui.treeview.Node;
import com.shtoone.shtw.ui.treeview.TreeListViewAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/8/10.
 */

public class FBProjectTreeListViewAdapter<T> extends TreeListViewAdapter<T> {

    String TAG = "FBProjectTreeListViewAdapter";
    public FBProjectTreeListViewAdapter(ListView tree, Context context, List<T> datas, int defaultExpandLevel) throws IllegalArgumentException, IllegalAccessException {
        super(tree, context, datas, defaultExpandLevel);
    }

    @Override
    public View getConvertView(Node node, int position, View convertView, ViewGroup parent) {
        FBProjectTreeListViewAdapter.ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_tree_listview_organization_activity, parent, false);
            holder = new FBProjectTreeListViewAdapter.ViewHolder();
            holder.mIcon = (ImageView) convertView
                    .findViewById(R.id.id_item_icon);
            holder.mText = (TextView) convertView
                    .findViewById(R.id.id_item_text);
            convertView.setTag(holder);
        } else {
            holder = (FBProjectTreeListViewAdapter.ViewHolder) convertView.getTag();
        }
        if (node.getIcon() == -1) {
            holder.mIcon.setVisibility(View.INVISIBLE);
        } else {
            holder.mIcon.setVisibility(View.VISIBLE);
            holder.mIcon.setImageResource(node.getIcon());
        }

        holder.mText.setText(node.getName());

        return convertView;
    }

    private class ViewHolder {
        ImageView mIcon;
        TextView  mText;
    }
}
