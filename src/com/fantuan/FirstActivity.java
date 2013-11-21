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
    void init() {
        count.requestFocus();
    }


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
        String[] names = mFanTuanManager.generateNames(
            Integer.valueOf(count.getText().toString()));

        NewDealStep4Activity_.intent(this)
            .names(names)
            .genName(true)
            .whoPay(0)
            .current(Double.valueOf(current.getText().toString()))
            .message(getString(R.string.new_deal_message_welcome))
            .start();
    }
}
