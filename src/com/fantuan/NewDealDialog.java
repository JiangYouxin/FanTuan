package com.fantuan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.fantuan.model.Person;
import com.fantuan.R;

import java.util.ArrayList;

import com.googlecode.androidannotations.annotations.*;

@EBean
public class NewDealDialog {
    @RootContext
    Activity mActivity;

    @Bean
    FanTuanManager mFanTuanManager;

    public void newDeal() {
        ArrayList<Person> persons = mFanTuanManager.getPersonList();
        String[] names = new String[persons.size()];
        for (int i = 0; i < names.length; i++)
            names[i] = persons.get(i).toString();
        newDealStep1(names);
    }

    private void newDealStep1(final String[] names) {
        final boolean[] checked = new boolean[names.length];
        for (int i = 0; i < checked.length; i++)
            checked[i] = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(R.string.newdeal_step_1);
        builder.setMultiChoiceItems(names, checked, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                checked[which] = isChecked;
            }
        });
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ArrayList<String> checkedNames = new ArrayList<String>();
                for (int i = 0; i < names.length; i++) {
                    if (checked[i])
                        checkedNames.add(names[i]);
                }
                dialog.dismiss();
                if (!checkedNames.isEmpty())
                    newDealStep2(checkedNames.toArray(new String[0]));
            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        builder.show();
    }

    private void newDealStep2(final String[] names) {
    }
}
