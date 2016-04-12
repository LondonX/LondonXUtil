package com.londonx.lutil.adapter;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by LondonX on 16/3/1.
 * Adapter for layout testing
 */
public class TestAdapter extends BaseAdapter {
    private int layoutId;

    public TestAdapter(@LayoutRes int layoutId) {
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(layoutId, parent, false);
        }
        return convertView;
    }
}
