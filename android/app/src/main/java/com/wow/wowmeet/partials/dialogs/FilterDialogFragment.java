package com.wow.wowmeet.partials.dialogs;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.wow.wowmeet.R;
import com.wow.wowmeet.models.Type;
import com.wow.wowmeet.screens.createevent.CreateEventActivity;
import com.wow.wowmeet.utils.DialogHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mahmutkaraca on 3/26/17.
 */

public class FilterDialogFragment extends DialogFragment implements FilterDialogContract.View {

    public static final String ARG_DATE_DAY = "com.wow.wowmeet.partials.dialogs.FilterDialogFragment.argDateDay";
    public static final String ARG_DATE_MONTH = "com.wow.wowmeet.partials.dialogs.FilterDialogFragment.argDateMonth";
    public static final String ARG_DATE_YEAR = "com.wow.wowmeet.partials.dialogs.FilterDialogFragment.argDateYear";

    public static final String ARG_DATE_START_HOUR = "com.wow.wowmeet.partials.dialogs.FilterDialogFragment.argDateStartHour";
    public static final String ARG_DATE_END_HOUR = "com.wow.wowmeet.partials.dialogs.FilterDialogFragment.argDateEndHour";

    public static final String ARG_PLACE_LAT = "com.wow.wowmeet.partials.dialogs.FilterDialogFragment.argPlaceLat";
    public static final String ARG_PLACE_LNG = "com.wow.wowmeet.partials.dialogs.FilterDialogFragment.argPlaceLng";
    public static final String ARG_PLACE_NAME = "com.wow.wowmeet.partials.dialogs.FilterDialogFragment.argPlaceName";

    public static final String ARG_PLACE_RADIUS = "com.wow.wowmeet.partials.dialogs.FilterDialogFragment.argPlaceRadius";
    public static final String ARG_TYPE = "com.wow.wowmeet.partials.dialogs.FilterDialogFragment.argType";
    private FilterDialogContract.Presenter presenter;

    public static FilterDialogFragment newInstance() {
        FilterDialogFragment dialog = new FilterDialogFragment();

        return dialog;
    }

    public static FilterDialogFragment newInstance(FilterInfo filterInfo) {
        FilterDialogFragment dialog = new FilterDialogFragment();
        Bundle args = new Bundle();

        args.putInt(ARG_DATE_DAY, filterInfo.dateStart.get(Calendar.DAY_OF_MONTH));
        args.putInt(ARG_DATE_MONTH, filterInfo.dateStart.get(Calendar.MONTH));
        args.putInt(ARG_DATE_YEAR, filterInfo.dateStart.get(Calendar.YEAR));

        args.putInt(ARG_DATE_START_HOUR, filterInfo.dateStart.get(Calendar.HOUR_OF_DAY));
        args.putInt(ARG_DATE_END_HOUR, filterInfo.dateEnd.get(Calendar.HOUR_OF_DAY));

        args.putDouble(ARG_PLACE_LAT, filterInfo.place.getLatLng().latitude);
        args.putDouble(ARG_PLACE_LNG, filterInfo.place.getLatLng().longitude);
        args.putString(ARG_PLACE_NAME, (String) filterInfo.place.getName());

        args.putInt(ARG_PLACE_RADIUS, filterInfo.radius);
        args.putSerializable(ARG_TYPE, filterInfo.type);

        dialog.setArguments(args);
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
    @BindView(R.id.spinnerActivityTypeFilter)
    Spinner spinnerActivityTypeFilter;

    @BindView(R.id.btnFilterCancel) Button btnCancel;
    @BindView(R.id.btnFilterOk) Button btnOK;

    private Calendar currentStartDateTime;
    private Calendar currentEndDateTime;

    private int startHour = 1;
    private int endHour = 2;

    private int radius = 20;

    private Place place;
    private double previousLat;
    private double previousLng;

    private boolean placeChanged;

    private ArrayAdapter<Type> spinnerAdapter;
    private List<Type> eventTypes;

    private void parseArguments(Bundle args) {
        int day = args.getInt(ARG_DATE_DAY);
        int month = args.getInt(ARG_DATE_MONTH);
        int year = args.getInt(ARG_DATE_YEAR);

        int startHour = args.getInt(ARG_DATE_START_HOUR);
        int endHour = args.getInt(ARG_DATE_END_HOUR);

        double lat = args.getDouble(ARG_PLACE_LAT);
        double lng = args.getDouble(ARG_PLACE_LNG);
        String name = args.getString(ARG_PLACE_NAME);

        int radius = args.getInt(ARG_PLACE_RADIUS);
        Type type = (Type) args.getSerializable(ARG_TYPE);

        currentStartDateTime.set(year, month, day, startHour, 0);
        currentEndDateTime.set(year, month, day, endHour, 0);
        this.startHour = startHour;
        this.endHour = endHour;

        this.edtLocationFilter.setText(name);
        previousLat = lat;
        previousLng = lng;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_filter, container, false);
        ButterKnife.bind(this, v);

        presenter = new FilterDialogPresenter(this);
        this.setPresenter(presenter);

        currentStartDateTime = Calendar.getInstance();
        currentEndDateTime = Calendar.getInstance();
        startHour = currentStartDateTime.get(Calendar.HOUR_OF_DAY);
        endHour = startHour + 1;
        if(getArguments() != null) {
            parseArguments(getArguments());
        }

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
                    DialogHelper.showAlertDialogWithError(getActivity(), e.getLocalizedMessage());
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                    DialogHelper.showAlertDialogWithError(getActivity(), e.getLocalizedMessage());
                }
            }
        });

        edtDateFilter.setKeyListener(null);
        edtLocationFilter.setKeyListener(null);

        eventTypes = new ArrayList<>();
        spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, eventTypes);
        spinnerActivityTypeFilter.setAdapter(spinnerAdapter);
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
                DialogHelper.showAlertDialogWithError(getActivity(), PlacePicker.getStatus(getActivity(), data).getStatusMessage());
            } else if(resultCode == Activity.RESULT_CANCELED) {
                DialogHelper.showToastMessage(getActivity(), getString(R.string.place_selection_cancelled_info_text));
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    public void setPresenter(FilterDialogContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showError(String e) {

    }

    @Override
    public void showError(@StringRes int resource) {

    }

    @Override
    public void updateEventTypes(List<Type> eventTypes) {
        this.eventTypes.clear();
        this.eventTypes.addAll(eventTypes);
        spinnerAdapter.notifyDataSetChanged();
    }

    public interface OnFilterDialogResultListener {
        void onFilterDialogResult(@Nullable Calendar startDate, @Nullable Calendar endDate, @Nullable Place place, int radius,
                                  @Nullable Type type);
    }

    public static class FilterInfo {
        public Calendar dateStart;
        public Calendar dateEnd;
        public Place place;
        public int radius;
        public Type type;
    }
}
