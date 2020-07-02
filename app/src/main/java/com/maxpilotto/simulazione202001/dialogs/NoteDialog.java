package com.maxpilotto.simulazione202001.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.maxpilotto.simulazione202001.R;

public class NoteDialog extends DialogFragment {
    private Callback callback;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        EditText note = new EditText(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        note.setLayoutParams(params);

        return new AlertDialog.Builder(getContext())
                .setTitle(R.string.noteTitle)
                .setView(note)
                .setNegativeButton(R.string.no,(dialog, which) -> {
                    if (callback != null) {
                        callback.onSave("");
                    }
                })
                .setPositiveButton(R.string.save,(dialog, which) -> {
                    if (callback != null) {
                        callback.onSave(note.getText().toString());
                    }
                })
                .create();
    }

    public NoteDialog setCallback(Callback callback) {
        this.callback = callback;

        return this;
    }

    public interface Callback {
        void onSave(String note);
    }
}
