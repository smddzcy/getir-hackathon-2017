package com.wow.wowmeet.exceptions;

/**
 * Created by ergunerdogmus on 25.03.2017.
 */

public class SendMessageFailedException extends BaseException{
    public SendMessageFailedException(String responseBody) {
        super(responseBody);
    }
}
