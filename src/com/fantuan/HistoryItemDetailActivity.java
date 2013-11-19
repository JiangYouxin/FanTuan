package com.fantuan;

import com.fantuan.model.NewHistoryItem;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.*; 

@EActivity(R.layout.list_with_header)
public class HistoryItemDetailActivity extends FragmentActivity { 
    @Bean
    FanTuanManager mFanTuanManager;

    @ViewById
    ListView list_view;

    @Extra
    int historyId;

    @Bean
    PersonListAdapter mAdapter;

    @ViewById
    TextView list_header;

    @AfterViews
    void init() {
        NewHistoryItem item = mFanTuanManager.getHistoryList().get(historyId);
        mAdapter.setPersonList(item.persons);
        list_header.setText(item.time);
        list_view.setAdapter(mAdapter);
    }
}
