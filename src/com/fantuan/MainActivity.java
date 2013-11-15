package com.fantuan;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.*; 

@EActivity(R.layout.main)
public class MainActivity extends FragmentActivity implements FanTuanManager.Observer {

    public static int FROM_WELCOME = 1;
    public static int FROM_NEWDEAL = 2;

    @Extra
    int from;

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
                tab_host.newTabSpec("person").setIndicator(createTabView(R.string.person_list_title)),
                MainListFragment_.class,
                null);
        tab_host.addTab(
                tab_host.newTabSpec("history").setIndicator(createTabView(R.string.history_list_title)),
                HistoryListFragment_.class,
                null);
        tab_host.addTab(
                tab_host.newTabSpec("more").setIndicator(createTabView(R.string.more_title)),
                MoreFragment_.class,
                null);
        mFanTuanManager.registerObserver(this);
        onModelChanged();
    }

	private View createTabView(int id) {
		View view = LayoutInflater.from(this).inflate(R.layout.tabs_bg, null);
		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		tv.setText(getString(id));
		ImageView iv = (ImageView)view.findViewById(R.id.icon);
		iv.setImageResource(R.drawable.ic_tab);
		return view;
	}

    @Override
    public void onDestroy() {
        mFanTuanManager.unregisterObserver(this);
        super.onDestroy();
    }

    @Override
    public void onModelChanged() {
        if (mFanTuanManager.getPersonList().isEmpty()) {
            welcome_layout.setVisibility(View.VISIBLE);
            tab_host.setCurrentTab(0);
            MainListFragment f = (MainListFragment) getSupportFragmentManager()
                .findFragmentByTag("person");
            if (f != null)
                f.setEditMode(false);
        } else {
            welcome_layout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
    }
}
