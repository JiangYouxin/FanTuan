package com.fantuan;

import com.fantuan.model.Person;

import android.content.Intent;
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

@EActivity(R.layout.list_with_header)
public class RemovePersonStep1Activity extends FragmentActivity { 
    @Bean
    FanTuanManager mFanTuanManager;

    @ViewById
    ListView list_view;

    @ViewById
    Button button_right;

    @Extra
    String nameToRemove;

    @ViewById
    TextView list_header;

    private ArrayList<String> names;

    private int whoPay;

    private BaseAdapter mAdapter;

    @AfterViews
    void init() {
        names = new ArrayList<String>();
        for (Person p: mFanTuanManager.getPersonList()) {
            if (!p.name.equals(nameToRemove))
                names.add(p.name);
        }
        mAdapter = new CustomAdapter();
        list_view.setAdapter(mAdapter);
        list_header.setText(getString(R.string.remove_person_select,
                nameToRemove));
        button_right.setVisibility(Button.VISIBLE);
        whoPay = 0;
        refresh();
    }

    @Click
    void button_right() {
        String[] names = new String[] {nameToRemove, this.names.get(whoPay) };
        double current = mFanTuanManager.getCurrentByName(nameToRemove);
        String message;
        if (current > 0)
            message = getString(R.string.remove_person_message,
                    names[1], names[0], current);
        else
            message = getString(R.string.remove_person_message,
                    names[0], names[1], -current);
        message += "\n";
        message += getString(R.string.new_deal_message);
        NewDealStep4Activity_.intent(this)
            .names(names)
            .whoPay(1)
            .current(current * 2)
            .message(message)
            .sendResult(true)
            .startForResult(0);
    }

    @Override
    protected void onActivityResult(int code, int resultCode, Intent data) {
        if (code == 0 && resultCode == 1) {
            mFanTuanManager.removePerson(nameToRemove);
            MainActivity_.intent(this)
                .flags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .start();
        }
    }

    @ItemClick
    void list_viewItemClicked(int position) {
        whoPay = position;
        refresh();
    }

    private void refresh() {
        mAdapter.notifyDataSetChanged();
    }

    private class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return names.size();
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public String getItem(int position) {
            return names.get(position);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            if (convertView == null)
                convertView = getLayoutInflater().inflate(R.layout.radio_button, null);
            CheckedTextView tv = (CheckedTextView) convertView;
            tv.setText(names.get(position));
            tv.setChecked(position == whoPay);
            return tv;
        }
    } 
}
