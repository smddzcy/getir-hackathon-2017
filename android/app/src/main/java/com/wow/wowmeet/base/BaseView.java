package com.wow.wowmeet.base;

import android.support.annotation.StringRes;

/**
 * Created by mahmutkaraca on 3/24/17.
 */

public interface BaseView<T extends BasePresenter> {
    void setPresenter(T presenter);
    void showError(String e);
    void showError(@StringRes int resource);
}
