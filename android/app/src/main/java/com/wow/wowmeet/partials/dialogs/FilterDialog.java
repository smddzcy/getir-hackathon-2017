package com.wow.wowmeet.partials.dialogs;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.wow.wowmeet.R;
import com.wow.wowmeet.models.Type;
import com.wow.wowmeet.screens.createevent.CreateEventActivity;

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

    private OnFilterDialogResultListener onFilterDialogResultListener;

    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            edtDateFilter.setText(day + "/" + (month + 1) + "/" + year);
            currentStartDateTime.set(Calendar.YEAR, year);
            currentStartDateTime.set(Calendar.MONTH, month);
            currentStartDateTime.set(Calendar.DAY_OF_MONTH, day);

            currentEndDateTime.set(Calendar.YEAR, year);
            currentEndDateTime.set(Calendar.MONTH, month);
            currentEndDateTime.set(Calendar.DAY_OF_MONTH, day);
        }
    };

    @BindView(R.id.edtLocationFilter) EditText edtLocationFilter;
    @BindView(R.id.edtDateFilter) EditText edtDateFilter;
    @BindView(R.id.seekBarStartingHour) SeekBar seekBarStartingHour;
    @BindView(R.id.seekBarEndingHour) SeekBar seekBarEndingHour;
    @BindView(R.id.seekBarRadius) SeekBar seekBarRadius;
    @BindView(R.id.txtStartingHourText) TextView txtStartHour;
    @BindView(R.id.txtEndingHourText) TextView txtEndHour;
    @BindView(R.id.txtRadiusText) TextView txtRadius;

    @BindView(R.id.btnFilterCancel) Button btnCancel;
    @BindView(R.id.btnFilterOk) Button btnOK;

    private Calendar currentStartDateTime;
    private Calendar currentEndDateTime;

    private int startHour = 1;
    private int endHour = 2;

    private int radius = 20;

    private Place place;

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

        seekBarRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                if(fromUser) {
                    radius = i;
                    txtRadius.setText(radius + " km");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onFilterDialogResultListener != null) {
                    currentStartDateTime.set(Calendar.HOUR_OF_DAY, startHour);
                    currentEndDateTime.set(Calendar.HOUR_OF_DAY, endHour);
                    onFilterDialogResultListener.onFilterDialogResult(currentStartDateTime, currentEndDateTime,
                            place, 10, /*TODO*/null);
                }
                dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        edtDateFilter.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus) {
                    openDatePickerDialog();
                }
            }
        });

        edtDateFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePickerDialog();
            }
        });

        edtLocationFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent i = new PlacePicker.IntentBuilder().build(getActivity());
                    startActivityForResult(i, CreateEventActivity.PLACE_PICKER_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                    //TODO handle error
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                    //TODO handle error 2
                }
            }
        });

        edtDateFilter.setKeyListener(null);
        edtLocationFilter.setKeyListener(null);

        return v;
    }

    public void setOnFilterDialogResultListener(OnFilterDialogResultListener onFilterDialogResultListener) {
        this.onFilterDialogResultListener = onFilterDialogResultListener;
    }

    private void openDatePickerDialog() {
        DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(currentStartDateTime);
        datePickerFragment.setOnDateSetListener(onDateSetListener);
        datePickerFragment.show(getFragmentManager(), "DatePickerDialog");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CreateEventActivity.PLACE_PICKER_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                place = PlacePicker.getPlace(getActivity(), data);
                edtLocationFilter.setText(place.getName());
            } else if(resultCode == PlacePicker.RESULT_ERROR) {
                //TODO handle error 3
            } else if(resultCode == Activity.RESULT_CANCELED) {
                //TODO handle cancellation
            }
        }
    }

    public interface OnFilterDialogResultListener {
        void onFilterDialogResult(@Nullable Calendar startDate, @Nullable Calendar endDate, @Nullable Place place, int radius,
                                  @Nullable Type type);
    }
}
