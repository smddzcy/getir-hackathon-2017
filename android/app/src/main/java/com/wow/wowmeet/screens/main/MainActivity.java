package com.wow.wowmeet.screens.main;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wow.wowmeet.R;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private MainContract.Presenter presenter;

    @BindView(R.id.viewPager) ViewPager viewPager;
    MainPagerAdapter mainPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainPresenter presenter = new MainPresenter(this);
        setPresenter(presenter);

        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mainPagerAdapter);
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onError(Throwable t) {
        String errorMessage = t.getMessage(); //TODO

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Hata!")
                .setMessage(errorMessage)
                .create();
        dialog.show();
    }

    @Override
    public void refreshListAndMap(ArrayList arr) {

    }
}
