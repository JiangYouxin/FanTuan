package com.fantuan;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import com.googlecode.androidannotations.annotations.*; 

@EActivity(R.layout.main)
public class MainActivity extends ActionBarActivity {
    @Bean
    FanTuanManager mFanTuanManager;

    @Bean
    TabsAdapter mAdapter;

    @AfterViews
    void init() {
        ActionBar bar = getSupportActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mAdapter.addTab(bar.newTab().setText(R.string.person_list_title),
                MainListFragment_.class, null);
        mAdapter.addTab(bar.newTab().setText(R.string.history_list_title),
                HistoryListFragment_.class, null);
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
    }

}
