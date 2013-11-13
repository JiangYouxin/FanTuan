package com.fantuan;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.*; 

@EActivity(R.layout.history_list)
public class NewDealStep2Activity extends FragmentActivity { 
    @Bean
    FanTuanManager mFanTuanManager;

    @ViewById
    ListView list_view;

    @ViewById
    Button button_right;

    @Extra
    String[] names;

    private int whoPay;

    private BaseAdapter mAdapter;

    private TextView header;

    @AfterViews
    void init() {
        mAdapter = new CustomAdapter();
        header = (TextView) getLayoutInflater().inflate(R.layout.list_header, null);
        header.setText(R.string.newdeal_step_2);
        list_view.addHeaderView(header);
        list_view.setAdapter(mAdapter);
        button_right.setVisibility(Button.VISIBLE);
        refresh();
    }

    @Click
    void button_right() {
        NewDealStep3Activity_.intent(this)
            .names(names)
            .whoPay(whoPay)
            .start();
    }

    @ItemClick
    void list_viewItemClicked(int position) {
        if (position > 0) {
            whoPay = position - 1;
            refresh();
        }
    }

    private void refresh() {
        mAdapter.notifyDataSetChanged();
    }

    private class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return names.length;
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public String getItem(int position) {
            return names[position];
        }
        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            if (convertView == null)
                convertView = getLayoutInflater().inflate(R.layout.radio_button, null);
            CheckedTextView tv = (CheckedTextView) convertView;
            tv.setText(names[position]);
            tv.setChecked(position == whoPay);
            return tv;
        }
    } 
}
