package com.fantuan;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.EditText;

import com.googlecode.androidannotations.annotations.*; 

@EActivity(R.layout.newdeal_step_3)
public class NewDealStep3Activity extends FragmentActivity {
    @Bean
    FanTuanManager mFanTuanManager;

    @ViewById
    EditText current;

    @ViewById
    Button commit;

    @Extra
    String[] names;

    @Extra
    int whoPay;

    @AfterViews
    @AfterTextChange(R.id.current)
    void refresh() {
        try {
            double db = Double.valueOf(current.getText().toString());
            commit.setEnabled(db > 0);
        } catch (Exception e) {
            commit.setEnabled(false);
        }
    }

    @Click
    void commit() {
        double db = Double.valueOf(current.getText().toString());
        mFanTuanManager.newDeal(names, whoPay, db);
        MainActivity_.intent(this)
            .from(MainActivity.FROM_NEWDEAL)
            .flags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            .start();
    }
}
