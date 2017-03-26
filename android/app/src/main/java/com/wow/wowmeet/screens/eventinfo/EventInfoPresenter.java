package com.wow.wowmeet.screens.eventinfo;

import com.wow.wowmeet.data.eventinfo.EventInfoRepository;
import com.wow.wowmeet.exceptions.BaseException;
import com.wow.wowmeet.models.Event;
import com.wow.wowmeet.models.User;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mahmutkaraca on 3/25/17.
 */

public class EventInfoPresenter implements EventInfoContract.Presenter {

    private EventInfoContract.View view;
    private Event event;
    private User user;

    private EventInfoRepository eventInfoRepository;
    private DisposableSingleObserver<String> disposableSingleJoinObserver;

    public EventInfoPresenter(EventInfoContract.View view, User user, Event event) {
        this.view = view;
        this.event = event;
        this.user = user;
        this.eventInfoRepository = new EventInfoRepository();
    }

    @Override
    public void start() {
        boolean isUserJoined = isUserJoined();
        view.showEventInfo(event, isUserJoined);
    }

    @Override
    public void stop() {
        if(disposableSingleJoinObserver != null)
            disposableSingleJoinObserver.dispose();
    }

    @Override
    public void onJoinClicked() {
        Single<String> singleJoinEvent = eventInfoRepository.joinEvent(event.get_id(), user.getToken());

        disposableSingleJoinObserver = singleJoinEvent
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<String>() {
                    @Override
                    public void onSuccess(String value) {
                        view.showEventInfo(event, true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(e instanceof BaseException) {
                            if(((BaseException)e).isUseResource()) {
                                view.showError(((BaseException) e).getErrorMessageResource());
                            }else {
                                view.showError(((BaseException) e).getErrorMessage());
                            }
                        }
                    }
                });
    }

    private boolean isUserJoined() {
        List<User> joinedUsers = event.getUsers();
        for(User u : joinedUsers){
            if(u.get_id().equals(user.get_id()))
                return true;
        }

        return false;
    }
}
