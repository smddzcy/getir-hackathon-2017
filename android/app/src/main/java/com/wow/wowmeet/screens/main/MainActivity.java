package com.wow.wowmeet.screens.main;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.wow.wowmeet.R;
import com.wow.wowmeet.base.BaseActivity;
import com.wow.wowmeet.models.User;
import com.wow.wowmeet.screens.login.LoginActivity;
import com.wow.wowmeet.screens.main.drawer.DrawerFragment;
import com.wow.wowmeet.utils.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainContract.View {

    private MainContract.Presenter presenter;

    @BindView(R.id.viewPager) ViewPager viewPager;
    MainPagerAdapter mainPagerAdapter;
    @BindView(R.id.drawerLayout) DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Intent i = getIntent();
        User user = (User) i.getSerializableExtra(Constants.INTENT_EXTRA_USER);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.drawerContainer, DrawerFragment.newInstance(user), "DRAWER")
                .commit();

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
    public void showError(String errorMessage) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Hata!")
                .setMessage(errorMessage)
                .create();
        dialog.show();
    }

    @Override
    public void refreshListAndMap(ArrayList arr) {

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
