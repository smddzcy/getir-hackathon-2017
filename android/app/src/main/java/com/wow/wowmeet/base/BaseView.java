package com.wow.wowmeet.base;

/**
 * Created by mahmutkaraca on 3/24/17.
 */

public interface BaseView<T extends BasePresenter> {
    void setPresenter(T presenter);
    void showError(String e);
}
