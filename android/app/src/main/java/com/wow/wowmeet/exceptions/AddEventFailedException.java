package com.wow.wowmeet.exceptions;

/**
 * Created by ergunerdogmus on 25.03.2017.
 */

public class AddEventFailedException extends BaseException {
    public AddEventFailedException(String responseBody) {
        super(responseBody);
    }
}
