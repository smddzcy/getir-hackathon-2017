package com.wow.wowmeet.screens.createevent;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by mahmutkaraca on 3/25/17.
 */

public class DatePickerFragment extends DialogFragment {

    public static final String ARG_INITIAL_DATE_DAY = "com.wow.wowmeet.screens.createevent.DatePickerFragment.argInitialDateDay";
    public static final String ARG_INITIAL_DATE_MONTH = "com.wow.wowmeet.screens.createevent.DatePickerFragment.argInitialDateMonth";
    public static final String ARG_INITIAL_DATE_YEAR = "com.wow.wowmeet.screens.createevent.DatePickerFragment.argInitialDateYear";

    private DatePickerDialog.OnDateSetListener onDateSetListener;

    public static DatePickerFragment newInstance(Calendar c) {
        DatePickerFragment fragment = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INITIAL_DATE_DAY, c.get(Calendar.DAY_OF_MONTH));
        args.putInt(ARG_INITIAL_DATE_MONTH, c.get(Calendar.MONTH));
        args.putInt(ARG_INITIAL_DATE_YEAR, c.get(Calendar.YEAR));
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        int day = args.getInt(ARG_INITIAL_DATE_DAY);
        int month = args.getInt(ARG_INITIAL_DATE_MONTH);
        int year = args.getInt(ARG_INITIAL_DATE_YEAR);

        return new DatePickerDialog(getActivity(), onDateSetListener, year, month, day);
    }

    public void setOnDateSetListener(DatePickerDialog.OnDateSetListener onDateSetListener) {
        this.onDateSetListener = onDateSetListener;
    }
}
