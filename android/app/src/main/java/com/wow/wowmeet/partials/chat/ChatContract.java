package com.wow.wowmeet.partials.chat;

import com.wow.wowmeet.base.BasePresenter;
import com.wow.wowmeet.base.BaseView;
import com.wow.wowmeet.models.Message;

import java.util.List;

/**
 * Created by ergunerdogmus on 25.03.2017.
 */

interface ChatContract {

    interface View extends BaseView<Presenter> {
        void showMessages(List<Message> messages);

        void showLoading();

        void hideLoading();
    }

    interface Presenter extends BasePresenter {
        void sendMessage(String messageText, String toId, String token);
    }

}
