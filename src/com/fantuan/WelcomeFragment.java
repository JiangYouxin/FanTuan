package com.fantuan;

import android.support.v4.app.Fragment;

import com.googlecode.androidannotations.annotations.*; 

@EFragment(R.layout.welcome)
public class WelcomeFragment extends Fragment {
    @Bean
    Dialogs mDialogs; 

    @Click
    void new_deal_welcome() {
        mDialogs.newDealWelcome();
    }
}
