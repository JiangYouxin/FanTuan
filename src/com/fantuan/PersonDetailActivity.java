package com.fantuan;

import com.fantuan.model.PersonHistoryItem;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.*; 

@EActivity(R.layout.list_with_header)
public class PersonDetailActivity extends FragmentActivity { 
    @Bean
    FanTuanManager mFanTuanManager;

    @ViewById
    ListView list_view;

    @Extra
    String name;

    @Bean
    PersonHistoryListAdapter mAdapter;

    @ViewById
    TextView list_header;

    @ItemClick
    void list_viewItemClicked(int position) {
        HistoryItemDetailActivity_.intent(this)
            .historyId(mAdapter.getItem(position).historyId)
            .start();
    }

    @AfterViews
    void init() {
        mAdapter.setList(mFanTuanManager.generatePersonHistoryList(name));
        list_header.setText(getString(R.string.person_detail_message, name));
        list_view.setAdapter(mAdapter);
    }
}
