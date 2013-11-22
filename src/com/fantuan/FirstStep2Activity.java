package com.fantuan;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.*; 

@EActivity(R.layout.first_step_2)
public class FirstStep2Activity extends FragmentActivity {
    @Bean
    FanTuanManager mFanTuanManager;

    @Extra
    int count;

    @Extra
    double current;

    @ViewById
    Button button_right;

    @ViewById
    LinearLayout container;

    private String[] names;

    private boolean showNext() {
        for (int i = 0; i < names.length; i++) {
            if (names[i].length() == 0)
                return false;
            for (int j = i + 1; j < names.length; j++) {
                if (names[i].equals(names[j]))
                    return false;
            }
        }
        return true;
    }

    @AfterViews
    void init() {
        names = new String[count];
        button_right.setVisibility(Button.VISIBLE);
        for (int i = 0; i < count; i++) {
            final int id = i;
            names[i] = getString(R.string.gen_new_name, i + 1);
            LinearLayout view = (LinearLayout) getLayoutInflater().inflate(R.layout.name_edit, null);
            TextView tv = (TextView) view.findViewById(R.id.text);
            EditText edit = (EditText) view.findViewById(R.id.name);
            tv.setText(i == 0 ? getString(R.string.gen_new_name_0) : names[i]);
            edit.setText(names[i]);
            edit.addTextChangedListener(new TextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {
                    names[id] = s.toString();
                    button_right.setEnabled(showNext());
                }
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
            });
            container.addView(view);
        }
    }

    @Click
    void button_right() {
        NewDealStep4Activity_.intent(this)
            .names(names)
            .whoPay(0)
            .current(current)
            .message(getString(R.string.new_deal_message))
            .start();
    }
}
