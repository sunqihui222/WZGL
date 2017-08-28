package com.shtoone.shtw.adapter;

import android.view.View;

public interface OnItemDelClickListener {
    void onItemClick(View view, int position);
    void onRightClick(View view, int position);
    void onBelowClick(View view, int position);
}
