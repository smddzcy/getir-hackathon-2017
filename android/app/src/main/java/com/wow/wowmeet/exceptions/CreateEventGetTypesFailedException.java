package com.wow.wowmeet.exceptions;

/**
 * Created by ergunerdogmus on 26.03.2017.
 */

public class CreateEventGetTypesFailedException extends BaseException{
    public CreateEventGetTypesFailedException(String responseBody) {
        super(responseBody);
    }
}
