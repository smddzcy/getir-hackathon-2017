package com.wow.wowmeet.partials.dialogs;

import com.wow.wowmeet.data.EventTypeRepository;
import com.wow.wowmeet.models.Type;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ergunerdogmus on 26.03.2017.
 */

public class FilterDialogPresenter implements FilterDialogContract.Presenter {

    private EventTypeRepository eventTypeRepository;
    private FilterDialogContract.View view;

    public FilterDialogPresenter(FilterDialogContract.View view) {
        eventTypeRepository = new EventTypeRepository();
        this.view = view;
    }

    @Override
    public void start() {
        Single<List<Type>> singleTypes = eventTypeRepository.getTypes();

        DisposableSingleObserver<List<Type>> disposableSingleObserver =
                singleTypes.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Type>>() {
                    @Override
                    public void onSuccess(List<Type> value) {
                        view.updateEventTypes(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e.getMessage());
                    }
                });
    }

    @Override
    public void stop() {

    }
}
