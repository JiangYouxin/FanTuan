package com.fantuan;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.EditText;

import com.googlecode.androidannotations.annotations.*; 

@EActivity(R.layout.first)
public class FirstActivity extends FragmentActivity {
    @Bean
    FanTuanManager mFanTuanManager;

    @ViewById
    EditText count;

    @ViewById
    EditText current;

    @ViewById
    Button commit;

    @AfterViews
    @AfterTextChange({R.id.count, R.id.current})
    void refresh() {
        try {
            double db = Double.valueOf(current.getText().toString());
            int it = Integer.valueOf(count.getText().toString());
            commit.setEnabled(db > 0 && it > 1);
        } catch (Exception e) {
            commit.setEnabled(false);
        }
    }

    @Click
    void commit() {
        NewDealStep4Activity_.intent(this)
            .count(Integer.valueOf(count.getText().toString()))
            .current(Double.valueOf(current.getText().toString()))
            .start();
    }
}
