package com.jvvas.restnewgenstats.Dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;

import java.util.Objects;

public class TelephonesDialog extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(
                Objects.requireNonNull(getActivity()));
        builder.setTitle("Support Telephone")
                .setMessage("Ιωάννης Βασιλείου : 6949567989 (CU)\nΘοδωρής Μαρμαρίδης : 6972317572"+
                        " (WhatsUp)\nΣπύρος Μαντέλος : 6943448579 (WhatsUp)\n")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }
}