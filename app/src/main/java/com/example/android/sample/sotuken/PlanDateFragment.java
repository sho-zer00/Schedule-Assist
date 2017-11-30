package com.example.android.sample.sotuken;

/**
 * Created by sho on 2017/07/26.
 */

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by sho on 2017/07/26.
 */

public class PlanDateFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //今日の日付を準備
        final Calendar cal = Calendar.getInstance();
        return new DatePickerDialog(
                getActivity(),R.style.DialogTheme,
                new DatePickerDialog.OnDateSetListener(){
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                        TextView txtDate = (TextView) getActivity().findViewById(R.id.txtDate);
                        txtDate.setText(String.format(Locale.JAPAN,"%02d/%02d/%02d",year,monthOfYear+1,dayOfMonth));
                    }
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
        );
    }
}