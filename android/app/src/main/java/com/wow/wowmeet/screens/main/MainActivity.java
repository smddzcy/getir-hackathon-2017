package com.wow.wowmeet.screens.main;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;

import com.wow.wowmeet.R;
import com.wow.wowmeet.base.BaseActivity;
import com.wow.wowmeet.models.Event;
import com.wow.wowmeet.models.User;
import com.wow.wowmeet.screens.main.drawer.DrawerFragment;
import com.wow.wowmeet.partials.list.ListContract;
import com.wow.wowmeet.partials.list.ListFragment;
import com.wow.wowmeet.screens.main.map.MapContract;
import com.wow.wowmeet.screens.main.map.MapFragment;
import com.wow.wowmeet.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainContract.View {

    private MainContract.Presenter presenter;

    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.drawerLayout) DrawerLayout drawerLayout;

    private MainPagerAdapter mainPagerAdapter;
    private ActionBarDrawerToggle drawerToggle;

    private ListContract.View listContractView;
    private MapContract.View mapContractView;

    private ArrayList<Event> events = new ArrayList<>();

    private MapFragment.OnRefreshListRequestedListener onRefreshListRequestedListener = new MapFragment.OnRefreshListRequestedListener() {
        @Override
        public void onRefreshListRequested() {
            //TODO
        }
    };

    @BindView(R.id.fab) FloatingActionButton fab;

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

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.open_drawer, R.string.close_drawer);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);

        final MainPresenter presenter = new MainPresenter(this);
        setPresenter(presenter);

        ListFragment lf = ListFragment.newInstance();
        MapFragment mf = MapFragment.newInstance();

        mf.setOnRefreshListRequestedListener(onRefreshListRequestedListener);

        listContractView = lf;
        mapContractView = mf;

        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(),
            lf, mf);

        viewPager.setAdapter(mainPagerAdapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onAddEventClicked();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
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
    public void refreshListAndMap(List<Event> arr) {
        events.clear();
        events.addAll(arr);

        listContractView.showEvents(events);
        mapContractView.showEvents(events);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mapContractView.onPermissionRequestResolved(requestCode, permissions, grantResults);

    }
}
