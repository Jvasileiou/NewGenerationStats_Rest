package com.jvvas.restnewgenstats.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class AreYouSureDialog extends DialogFragment{

    private DialogListener listener ;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        AlertDialog.Builder builder = new AlertDialog.Builder(
                Objects.requireNonNull(getActivity()));
        builder.setTitle("Επιβεβαίωση Επιλογής")
                .setMessage("Εισαι Σιγουρος/-η ?")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do Nothing
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.changeTheStatusToFinal();
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (DialogListener) context ;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+ "must implement DialogListener");
        }
    }

    public interface DialogListener{
        void changeTheStatusToFinal();
    }
}
