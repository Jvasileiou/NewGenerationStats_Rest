package com.jvvas.restnewgenstats.Dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class TelephonesDialog extends DialogFragment {

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