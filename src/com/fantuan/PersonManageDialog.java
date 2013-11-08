package com.fantuan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;

import com.fantuan.model.Person;
import com.fantuan.R;

import java.util.ArrayList;

import com.googlecode.androidannotations.annotations.*;

@EBean
public class PersonManageDialog {
    @RootContext
    Activity mActivity;

    @Bean
    FanTuanManager mFanTuanManager;

    public void add() {
        final EditText edit = new EditText(mActivity);
        new AlertDialog.Builder(mActivity)
                .setTitle(R.string.input_name)
                .setView(edit)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = edit.getText().toString();
                        if (name != null && !name.isEmpty())
                            mFanTuanManager.addNewPerson(name);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

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
        final int[] whoPay = new int[] { mFanTuanManager.suggestWhoPay(names) };
        
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(R.string.newdeal_step_2);
        builder.setSingleChoiceItems(names, whoPay[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                whoPay[0] = which;
            }
        });
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                newDealStep3(names, whoPay[0]);
            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        builder.show();
    }

    private void newDealStep3(final String[] names, final int whoPay) {
        final EditText editView = new EditText(mActivity);
        editView.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(R.string.newdeal_step_3);
        builder.setView(editView);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                double current = Double.valueOf(editView.getText().toString());
                mFanTuanManager.newDeal(names, whoPay, current);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        builder.show();
    }

    public void modify(final Person p) {
        final EditText edit = new EditText(mActivity);
        edit.setText(p.name);
        new AlertDialog.Builder(mActivity)
                .setTitle(R.string.input_name_modify)
                .setView(edit)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = edit.getText().toString();
                        if (name != null && !name.isEmpty())
                            mFanTuanManager.modifyName(p, name);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    public void delete(final Person p) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(R.string.confirm_delete);
        builder.setMessage(mActivity.getString(R.string.confirm_delete_name, p.name));
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mFanTuanManager.removePerson(p);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        builder.show();
    }
}
