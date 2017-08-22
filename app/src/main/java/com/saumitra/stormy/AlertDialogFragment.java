package com.saumitra.stormy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by saumitra on 20-08-2017.
 */

public class AlertDialogFragment extends DialogFragment// you need to extend it
{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context=getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.error_title).setMessage(R.string.Error_message)
        .setPositiveButton(R.string.Error_positive_button_text,null);// we can write null if we dont want to do anything specific
        AlertDialog dialog=builder.create();// this creates builder and then returns it
        return dialog;

    }
}
