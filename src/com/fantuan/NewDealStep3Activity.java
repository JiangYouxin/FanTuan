package com.fantuan;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.*; 

@EActivity(R.layout.newdeal_step_3)
public class NewDealStep3Activity extends FragmentActivity {
    @Bean
    FanTuanManager mFanTuanManager;

    @ViewById
    EditText current;

    @ViewById
    Button button_right;

    @ViewById
    TextView count;

    @ViewById
    TextView who;

    @Extra
    String[] names;

    @Extra
    int whoPay;

    @AfterViews
    void init() {
        count.setText(String.format("%d", names.length));
        who.setText(names[whoPay]);
        button_right.setVisibility(Button.VISIBLE);
    }

    @AfterTextChange(R.id.current)
    void refresh() {
        try {
            double db = Double.valueOf(current.getText().toString());
            button_right.setEnabled(db > 0);
        } catch (Exception e) {
            button_right.setEnabled(false);
        }
    }

    @Click
    void button_right() {
        double db = Double.valueOf(current.getText().toString());
        NewDealStep4Activity_.intent(this)
            .names(names)
            .whoPay(whoPay)
            .current(db)
            .message(getString(R.string.new_deal_message))
            .start();
    }
}
