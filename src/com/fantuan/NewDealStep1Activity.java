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

import java.util.ArrayList;

import com.googlecode.androidannotations.annotations.*; 

@EActivity(R.layout.history_list)
public class NewDealStep1Activity extends FragmentActivity { 
    @Bean
    FanTuanManager mFanTuanManager;

    @ViewById
    ListView list_view;

    @ViewById
    Button button_right;

    private String[] names;

    private boolean[] selected;

    private BaseAdapter mAdapter;

    private TextView header;

    @AfterViews
    void init() {
        int length = mFanTuanManager.getPersonList().size();
        names = new String[length];
        selected = new boolean[length];
        for (int i = 0; i < length; i++) {
            names[i] = mFanTuanManager.getPersonList().get(i).name;
            selected[i] = true;
        }
        mAdapter = new CustomAdapter();
        header = (TextView) getLayoutInflater().inflate(R.layout.list_header, null);
        list_view.addHeaderView(header);
        list_view.setAdapter(mAdapter);
        button_right.setVisibility(Button.VISIBLE);
        refresh();
    }

    @Click
    void button_right() {
        ArrayList<String> checkedNames = new ArrayList<String>();
        for (int i = 0; i < names.length; i++) {
            if (selected[i])
                checkedNames.add(names[i]);
        }
        NewDealStep2Activity_.intent(this)
            .names(checkedNames.toArray(new String[0]))
            .start();
    }

    @ItemClick
    void list_viewItemClicked(int position) {
        if (position > 0) {
            position--;
            selected[position] = !selected[position];
            mAdapter.notifyDataSetChanged();
            refresh();
        }
    }

    void refresh() {
        int count = getSelectedCount();
        button_right.setEnabled(count >= 2);
        header.setText(getString(R.string.newdeal_step_1, count));
    }

    private int getSelectedCount() {
        int selectedCount = 0;
        for (boolean b: selected) if (b)
           selectedCount++;
        return selectedCount;
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
                convertView = getLayoutInflater().inflate(R.layout.checked_text, null);
            CheckedTextView tv = (CheckedTextView) convertView;
            tv.setText(names[position]);
            tv.setChecked(selected[position]);
            return tv;
        }
    } 
}
