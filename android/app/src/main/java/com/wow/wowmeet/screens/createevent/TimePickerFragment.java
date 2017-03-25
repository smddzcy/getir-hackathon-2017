package com.wow.wowmeet.screens.createevent;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.app.TimePickerDialog;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by mahmutkaraca on 3/25/17.
 */

public class TimePickerFragment extends DialogFragment {

    public static final String ARG_INITIAL_HOUR = "com.wow.wowmeet.screens.createevent.TimePickerFragment.argInitialHour";
    public static final String ARG_INITIAL_MINUTE = "com.wow.wowmeet.screens.createevent.TimePickerFragment.argInitialMinute";

    private TimePickerDialog.OnTimeSetListener onTimeSetListener;

    public static TimePickerFragment newInstance(Calendar calendar) {
        TimePickerFragment fragment = new TimePickerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INITIAL_HOUR, calendar.get(Calendar.HOUR_OF_DAY));
        args.putInt(ARG_INITIAL_MINUTE, calendar.get(Calendar.MINUTE));
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        int hour = args.getInt(ARG_INITIAL_HOUR);
        int min = args.getInt(ARG_INITIAL_MINUTE);

        return new TimePickerDialog(getActivity(), onTimeSetListener, hour, min,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void setOnTimeSetListener(TimePickerDialog.OnTimeSetListener onTimeSetListener) {
        this.onTimeSetListener = onTimeSetListener;
    }
}
