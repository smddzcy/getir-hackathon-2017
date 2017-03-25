package com.wow.wowmeet.base;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wow.wowmeet.R;

/**
 * Created by mahmutkaraca on 3/25/17.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseLoadingView {

    private View loadingView;

    @Override
    protected void onStart() {
        super.onStart();
        initializeLoadingView();
    }

    @Override
    public void showLoadingView() {
        ViewGroup rootView = (ViewGroup) findViewById(android.R.id.content);
        rootView.addView(loadingView);
    }

    @Override
    public void hideLoadingView() {
        ViewGroup rootView = (ViewGroup) findViewById(android.R.id.content);
        rootView.removeView(loadingView);
    }

    private void initializeLoadingView() {
        ViewGroup rootView = (ViewGroup) findViewById(android.R.id.content);
        loadingView =
                LayoutInflater.from(this).inflate(R.layout.layout_loading, rootView, false);
    }

}
