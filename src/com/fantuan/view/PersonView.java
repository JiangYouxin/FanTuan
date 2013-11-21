package com.fantuan.view;

import com.fantuan.Dialogs;
import com.fantuan.R;
import com.fantuan.RemovePersonStep1Activity_;
import com.fantuan.model.Person;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.*;

@EView
public class PersonView extends LinearLayout {

    @ViewById(android.R.id.text1)
    TextView text1;

    @ViewById(android.R.id.text2)
    TextView text2;

    @ViewById
    TextView rename;

    @ViewById
    TextView delete;

    @Bean
    Dialogs mDialogs;

    @ViewById
    ImageView icon;

    private Person p;

    public PersonView(Context context) {
        super(context);
    }

    public PersonView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void bind(Person p, boolean editMode, boolean canDelete, boolean showIcon) {
        this.p = p;
        text1.setText(p.name);
        text2.setText(String.format("%.2f", p.current));
        text2.setTextAppearance(getContext(), p.current >= 0 
                ? R.style.PrimaryTextGreen: R.style.PrimaryTextRed);
        icon.setVisibility(showIcon ? ImageView.VISIBLE : ImageView.GONE);
        rename.setVisibility(editMode ? TextView.VISIBLE: TextView.GONE);
        delete.setVisibility(editMode && canDelete ? TextView.VISIBLE: TextView.GONE);
    }

    @Click
    void rename() {
        mDialogs.modify(p);
    }

    @Click
    void delete() {
        RemovePersonStep1Activity_.intent(getContext())
            .nameToRemove(p.name)
            .start();
    }
}
