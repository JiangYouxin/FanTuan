package com.fantuan;

import com.fantuan.model.Person;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import com.googlecode.androidannotations.annotations.*; 

@EActivity(R.layout.history_list)
public class NewDealStep4Activity extends FragmentActivity implements View.OnClickListener { 
    @Bean
    FanTuanManager mFanTuanManager;

    @ViewById
    ListView list_view;

    @Extra
    String[] names;

    @Extra
    int whoPay;

    @Extra
    int count;

    @Extra
    double current;

    @Bean
    PersonListAdapter mAdapter;

    private TextView header;
    private ArrayList<Person> mPersonList;

    @AfterViews
    void init() {
        if (names == null)
            mPersonList = mFanTuanManager.generatePersonList(count, current);
        else
            mPersonList = mFanTuanManager.generatePersonList(names, whoPay, current);
        mAdapter.setPersonList(mPersonList);
        header = (TextView) getLayoutInflater().inflate(R.layout.list_header, null);
        header.setText(names == null ? R.string.new_deal_message_welcome : R.string.new_deal_message);
        View button = getLayoutInflater().inflate(R.layout.button, null);
        button.findViewById(R.id.commit).setOnClickListener(this);
        list_view.addHeaderView(header);
        list_view.addFooterView(button);
        list_view.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        mFanTuanManager.mergePersonList(mPersonList);
        MainActivity_.intent(this)
            .from(names == null ? MainActivity.FROM_WELCOME : MainActivity.FROM_NEWDEAL)
            .flags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            .start();
    }
}
