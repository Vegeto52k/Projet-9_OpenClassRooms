package fr.vegeto52.realestatemanager.utils;

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

import fr.vegeto52.realestatemanager.R;

/**
 * The EditDescriptionDialog class extends DialogFragment and provides a dialog for editing a description.
 * It includes an interface OnInputSelected to communicate user input to the calling fragment.
 */
public class EditDescriptionDialog extends DialogFragment {

    // Interface to communicate user input to the calling fragment
    public interface OnInputSelected {
        void sendInput(String input);
    }

    // Listener for user input
    public OnInputSelected mOnInputSelected;

    // UI elements
    EditText mEditDescription;
    Button mValidateButton;
    Button mCancelButton;

    /**
     * Default constructor for the EditDescriptionDialog class.
     */
    public EditDescriptionDialog() {
    }

    /**
     * Called to create and return the view hierarchy associated with the dialog.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate views.
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return The View for the fragment's UI.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dialog_edit_description, container, false);

        // Initialize UI elements
        mEditDescription = view.findViewById(R.id.dialog_edit_description_edit_description);
        mValidateButton = view.findViewById(R.id.dialog_edit_description_button_validate);
        mCancelButton = view.findViewById(R.id.dialog_edit_description_button_cancel);

        // Set up button click listeners
        initButton();

        return view;
    }

    /**
     * Initializes click listeners for the dialog buttons.
     */
    private void initButton() {
        mCancelButton.setOnClickListener(view -> Objects.requireNonNull(getDialog()).dismiss());

        mValidateButton.setOnClickListener(view -> {
            String input = mEditDescription.getText().toString();
            mOnInputSelected.sendInput(input);
            Objects.requireNonNull(getDialog()).dismiss();
        });
    }

    /**
     * Called when the fragment is associated with an activity.
     *
     * @param context The context to attach.
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            // Set the calling fragment as the listener for user input
            mOnInputSelected = (OnInputSelected) getTargetFragment();
        } catch (ClassCastException ignored) {
        }
    }
}
