package com.fantuan.view;

import com.fantuan.R;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.*;

@EView
public class TitleView extends LinearLayout {

    public TitleView(Context context) {
        super(context);
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Click
    void button_back() {
        ((Activity)getContext()).finish();
    }
}
