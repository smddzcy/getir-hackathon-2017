package com.wow.wowmeet.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * Created by ergunerdogmus on 25.03.2017.
 */

public class DialogHelper {

    public static void showAlertDialogWithError(Context context, String errorMessage){
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Error!")
                .setMessage(errorMessage)
                .setNegativeButton("Understand", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setCancelable(true)
                .create();
        dialog.show();
    }

    public static void showToastMessage(Context context, String errorMessage) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
    }
}
