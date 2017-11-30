package com.example.android.sample.sotuken;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Locale;

/**
 * Created by sho on 2017/07/26.
 */

public class PlanTimeFragment extends DialogFragment{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //現在の時刻を準備
        final Calendar cal = Calendar.getInstance();
        return new TimePickerDialog(
                getActivity(),R.style.TimePicker,
                new TimePickerDialog.OnTimeSetListener(){
                    //選択された時刻をテキストボックスに反映
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
                        TextView txtTime = (TextView) getActivity().findViewById(R.id.txtTime);
                        txtTime.setText(
                                String.format(Locale.JAPAN,"%02d:%02d",hourOfDay,minute));
                    }
                },
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
        );
    }
}
