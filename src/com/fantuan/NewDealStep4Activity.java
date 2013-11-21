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

@EActivity(R.layout.list_with_header)
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
    double current;

    @Extra
    String message;

    @Extra
    boolean sendResult;

    @Extra
    boolean genName;

    @Bean
    PersonListAdapter mAdapter;

    @ViewById
    TextView list_header;

    private TextView header;
    private ArrayList<Person> mPersonList;

    @AfterViews
    void init() {
        mPersonList = mFanTuanManager.generatePersonList(names, whoPay, current);
        mAdapter.setPersonList(mPersonList);
        list_header.setText(message);
        View button = getLayoutInflater().inflate(R.layout.button, null);
        button.findViewById(R.id.commit).setOnClickListener(this);
        list_view.addFooterView(button);
        list_view.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        mFanTuanManager.mergePersonList(mPersonList, genName);
        if (sendResult) {
            setResult(1);
            finish();
        }
        else {
            MainActivity_.intent(this)
                .flags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .start();
        }
    }
}
