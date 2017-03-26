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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.wow.wowmeet.R;
import com.wow.wowmeet.base.BaseActivity;
import com.wow.wowmeet.models.Event;
import com.wow.wowmeet.models.User;
import com.wow.wowmeet.partials.list.ListContract;
import com.wow.wowmeet.partials.list.ListFragment;
import com.wow.wowmeet.screens.createevent.CreateEventActivity;
import com.wow.wowmeet.screens.main.drawer.DrawerFragment;
import com.wow.wowmeet.screens.main.map.MapContract;
import com.wow.wowmeet.screens.main.map.MapFragment;
import com.wow.wowmeet.utils.Constants;
import com.wow.wowmeet.utils.DialogHelper;
import com.wow.wowmeet.partials.dialogs.FilterDialog;
import com.wow.wowmeet.utils.UserProvider;

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

    private MapFragment.OnRefreshListRequestedListener onRefreshListRequestedListenerForMap = new MapFragment.OnRefreshListRequestedListener() {

        @Override
        public void onRefreshListRequested(double lat, double lng, int radius) {
            presenter.onRefreshListAndMap(lat, lng, radius);
        }
    };

    private ListFragment.OnRefreshListRequestedListener onRefreshListRequestedListenerForList = new ListFragment.OnRefreshListRequestedListener() {
        @Override
        public void onRefreshListRequested() {
            presenter.onRefreshListAndMap();
        }
    };

    private User user;

    @BindView(R.id.fab) FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Intent i = getIntent();
        user = (User) i.getSerializableExtra(Constants.INTENT_EXTRA_USER);
        UserProvider.getInstance().setUser(user);

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

        mf.setOnRefreshListRequestedListener(onRefreshListRequestedListenerForMap);
        lf.setOnRefreshListRequestedListener(onRefreshListRequestedListenerForList);

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
        int action = item.getItemId();
        if(action == R.id.action_filter) {
            FilterDialog filterDialog = FilterDialog.newInstance();
            filterDialog.setOnFilterDialogResultListener(presenter);
            filterDialog.show(getSupportFragmentManager(), "FilterDialogTest");

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
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
        DialogHelper.showAlertDialogWithError(this, errorMessage);
    }

    @Override
    public void refreshListAndMap(List<Event> arr) {
        events.clear();
        events.addAll(arr);

        listContractView.stopRefreshLayoutRefreshingAnimation();

        listContractView.showEvents(events);
        mapContractView.showEvents(events);
    }

    @Override
    public void goCreateEventActivity() {
        Intent intent = new Intent(this, CreateEventActivity.class);
        intent.putExtra(Constants.INTENT_EXTRA_USER, user);
        startActivity(intent);
    }

    @Override
    public void showLoading() {
        showLoadingView();
    }

    @Override
    public void hideLoading() {
        hideLoadingView();
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
