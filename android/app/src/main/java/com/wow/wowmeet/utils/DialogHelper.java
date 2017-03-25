package com.wow.wowmeet.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;

/**
 * Created by ergunerdogmus on 25.03.2017.
 */

public class DialogHelper {

    public static void showAlertDialogWithError(Context context, String errorMessage){
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Hata!")
                .setMessage(errorMessage)
                .setCancelable(true)
                .create();
        dialog.show();
    }
}
