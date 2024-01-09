package fr.vegeto52.realestatemanager;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

/**
 * Created by Vegeto52-PC on 17/12/2023.
 */
public class EditDescriptionDialog extends DialogFragment {

    public interface OnInputSelected {
        void sendInput(String input);
    }

    public OnInputSelected mOnInputSelected;

    EditText mEditDescription;
    Button mValidateButton;
    Button mCancelButton;

    public EditDescriptionDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_edit_description, container, false);

        mEditDescription = view.findViewById(R.id.dialog_edit_description_edit_description);
        mValidateButton = view.findViewById(R.id.dialog_edit_description_button_validate);
        mCancelButton = view.findViewById(R.id.dialog_edit_description_button_cancel);

        initButton();

        return view;
    }

    private void initButton() {
        mCancelButton.setOnClickListener(view -> Objects.requireNonNull(getDialog()).dismiss());

        mValidateButton.setOnClickListener(view -> {
            String input = mEditDescription.getText().toString();
            mOnInputSelected.sendInput(input);
            Objects.requireNonNull(getDialog()).dismiss();
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mOnInputSelected = (OnInputSelected) getTargetFragment();
        } catch (ClassCastException ignored) {
        }
    }
}
