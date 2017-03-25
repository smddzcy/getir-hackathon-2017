package com.wow.wowmeet.screens.main.drawer;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wow.wowmeet.R;
import com.wow.wowmeet.models.User;
import com.wow.wowmeet.screens.login.LoginActivity;
import com.wow.wowmeet.utils.SharedPreferencesUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrawerFragment extends Fragment implements DrawerContract.View {

    public static final String ARG_USER = "com.wow.wowmeet.MainActivity.DrawerFragment.User";

    private User user;

    private DrawerContract.Presenter presenter;

    public static DrawerFragment newInstance(User user) {
        DrawerFragment fragment = new DrawerFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    public DrawerFragment() {}

    @BindView(R.id.txtUsernameDrawer) TextView txtUsername;
    @BindView(R.id.imgDrawerProfilePicture) ImageView imgProfile;
    @BindView(R.id.frameUserBackground) FrameLayout userBackgroundFrame;
    @BindView(R.id.listDrawerOptions) ListView drawerOptions;

    private ArrayAdapter<String> drawerOptionsArrayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        user = (User) getArguments().getSerializable(ARG_USER);
        View v = inflater.inflate(R.layout.fragment_drawer, container, false);
        ButterKnife.bind(this, v);

        SharedPreferencesUtil sharedPreferencesUtil = SharedPreferencesUtil.getInstance(getContext());
        presenter = new DrawerPresenter(this, sharedPreferencesUtil);

        txtUsername.setText(user.getEmail());

        final String[] options = getResources().getStringArray(R.array.drawer_options_array);

        drawerOptionsArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, options);
        drawerOptions.setAdapter(drawerOptionsArrayAdapter);

        drawerOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                presenter.onItemClick(i, options[i]);
            }
        });


        return v;
    }

    @Override
    public void setPresenter(DrawerContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showError(String e) {

    }

    @Override
    public void goLogin() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
