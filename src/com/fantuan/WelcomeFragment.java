package com.fantuan;

import android.support.v4.app.Fragment;
import android.widget.Button;

import com.googlecode.androidannotations.annotations.*; 

@EFragment(R.layout.welcome)
public class WelcomeFragment extends Fragment {
    @ViewById
    Button button_back;

    @AfterViews
    void init() {
        button_back.setVisibility(Button.GONE);
    }

    @Click
    void new_deal_welcome() {
        FirstActivity_.intent(getActivity()).start();
    }
}
