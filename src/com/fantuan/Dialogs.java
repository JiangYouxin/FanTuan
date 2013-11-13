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
public class Dialogs {
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
        builder.setTitle(mActivity.getString(R.string.confirm_delete_name, p.name));
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

    public void clearHistory() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(R.string.confirm_clear_history_message);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mFanTuanManager.clearHistory();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        builder.show();
    }
}
