package com.maxpilotto.simulazione202001.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.maxpilotto.simulazione202001.R;

public class DeleteDialog extends DialogFragment {
    private Callback callback;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setTitle(R.string.deleteBook)
                .setMessage(R.string.deleteBookMsg)
                .setPositiveButton(R.string.yes,(dialog, which) -> {
                    if (callback != null) {
                        callback.onConfirm();
                    }

                    dialog.dismiss();
                })
                .setNegativeButton(R.string.no,(dialog, which) -> {
                    dialog.dismiss();
                })
                .create();
    }

    public DeleteDialog setCallback(Callback callback) {
        this.callback = callback;

        return this;
    }

    public interface Callback {
        void onConfirm();
    }
}
