package com.fantuan;

import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;

import com.googlecode.androidannotations.annotations.*; 

@EActivity(R.layout.main)
public class MainActivity extends ActionBarActivity implements FanTuanManager.Observer {
    @Bean
    FanTuanManager mFanTuanManager;

    @ViewById
    FragmentTabHost tab_host;

    @ViewById
    View content_layout;

    @ViewById
    View welcome_layout;

    @AfterViews
    void init() {
        tab_host.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        tab_host.addTab(
                tab_host.newTabSpec("person").setIndicator(getString(R.string.person_list_title)),
                MainListFragment_.class,
                null);
        tab_host.addTab(
                tab_host.newTabSpec("history").setIndicator(getString(R.string.history_list_title)),
                HistoryListFragment_.class,
                null);
        tab_host.addTab(
                tab_host.newTabSpec("discover").setIndicator(getString(R.string.discover_title)),
                EmptyFragment_.class,
                null);
        tab_host.addTab(
                tab_host.newTabSpec("more").setIndicator(getString(R.string.more_title)),
                EmptyFragment_.class,
                null);
        mFanTuanManager.registerObserver(this);
        onModelChanged();
    }

    @Override
    public void onDestroy() {
        mFanTuanManager.unregisterObserver(this);
        super.onDestroy();
    }

    @Override
    public void onModelChanged() {
        if (mFanTuanManager.getPersonList().isEmpty()) {
            content_layout.setVisibility(View.GONE);
            welcome_layout.setVisibility(View.VISIBLE);
            getSupportActionBar().hide();
        } else {
            content_layout.setVisibility(View.VISIBLE);
            welcome_layout.setVisibility(View.GONE);
            getSupportActionBar().show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
    }
}
