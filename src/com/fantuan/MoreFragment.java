package com.fantuan;

import com.fantuan.model.Person;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;

import com.googlecode.androidannotations.annotations.*; 

@EFragment(R.layout.more)
public class MoreFragment extends Fragment {
    @Bean
    Dialogs mDialog;

    @ViewById
    Button button_back;

    @AfterViews
    void init() {
        button_back.setVisibility(Button.GONE);
    }

    @Click
    void clear_all() {
        mDialog.clearAll();
    }
}
