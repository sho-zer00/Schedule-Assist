package com.example.android.sample.sotuken;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

/**
 * Created by sho on 2017/07/26.
 */

public class PlanDoFragment extends DialogFragment {

    int selected = 0;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final String[] items = {"仕事","勉強","趣味","買い物"};
        //ダイアログを生成
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder.setTitle("やる事")
                .setSingleChoiceItems(items, selected,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selected = which;
                            }
                        })
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText txtTime = (EditText)getActivity().findViewById(R.id.txtPlan);
                                txtTime.setText(String.format("%s",items[selected]));
                            }
                        })
                .create();
    }
}
