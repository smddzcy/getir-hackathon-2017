package com.wow.wowmeet.utils;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.wow.wowmeet.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mahmutkaraca on 3/26/17.
 */

public class FilterDialog extends DialogFragment {

    public static FilterDialog newInstance() {
        FilterDialog dialog = new FilterDialog();

        return dialog;
    }

    @BindView(R.id.edtLocationFilter) EditText edtLocationFilter;
    @BindView(R.id.edtDateFilter) EditText edtDateFilter;
    @BindView(R.id.seekBarStartingHour) SeekBar seekBarStartingHour;
    @BindView(R.id.seekBarEndingHour) SeekBar seekBarEndingHour;
    @BindView(R.id.txtStartingHourText) TextView txtStartHour;
    @BindView(R.id.txtEndingHourText) TextView txtEndHour;

    private Calendar currentStartDateTime;
    private Calendar currentEndDateTime;

    private int startHour = 1;
    private int endHour = 2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_filter, container, false);

        ButterKnife.bind(this, v);

        currentStartDateTime = Calendar.getInstance();
        currentEndDateTime = Calendar.getInstance();
        startHour = currentStartDateTime.get(Calendar.HOUR_OF_DAY);
        endHour = startHour + 1;


        seekBarStartingHour.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                if(fromUser) {
                    startHour = i;
                    if(endHour <= startHour) {
                        endHour = Math.min(24, startHour + 1);
                        seekBarEndingHour.setProgress(endHour);
                        txtEndHour.setText(((endHour < 10) ? "0" : "") + endHour + ":00");
                    }
                    txtStartHour.setText(((startHour < 10) ? "0" : "") + startHour + ":00");
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        seekBarEndingHour.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                if(fromUser) {
                    endHour = i;
                    if(endHour <= startHour) {
                        startHour = Math.max(endHour - 1, 0);
                        seekBarStartingHour.setProgress(startHour);
                        txtStartHour.setText(((startHour < 10) ? "0" : "") + startHour + ":00");
                    }
                    txtEndHour.setText(((endHour < 10) ? "0" : "") + endHour + ":00");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        return v;
    }
}
