package com.wow.wowmeet.screens.profile;

import com.wow.wowmeet.data.profile.ProfileRepository;

/**
 * Created by ergunerdogmus on 25.03.2017.
 */

public class ProfilePresenter implements ProfileContract.Presenter {

    private ProfileRepository profileRepository;

    public ProfilePresenter() {
        this.profileRepository = new ProfileRepository();
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
