package fr.vegeto52.realestatemanager.ui.mainActivity.photoViewFragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import fr.vegeto52.realestatemanager.R;
import fr.vegeto52.realestatemanager.databinding.FragmentPhotoViewBinding;

/**
 * The PhotoViewFragment class represents a fragment for viewing a single photo.
 * It includes a toolbar with a back button for navigation.
 */
public class PhotoViewFragment extends Fragment {

    // View binding for the fragment
    FragmentPhotoViewBinding mBinding;

    // ImageView to display the photo
    ImageView mPhotoView;

    // Toolbar for the fragment
    Toolbar mToolbar;

    // Back button on the toolbar
    ImageButton mBackButton;

    // URI of the photo to be displayed
    Uri mUriPhoto;

    /**
     * Default constructor for the PhotoViewFragment class.
     */
    public PhotoViewFragment() {
        // Required empty public constructor
    }

    /**
     * Called when the fragment is created.
     *
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Called to save the current dynamic state of the fragment into the given Bundle.
     *
     * @param outState Bundle in which to place the saved state.
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("uriPhoto", mUriPhoto);
    }

    /**
     * Called to create and return the view hierarchy associated with the fragment.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate views.
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return The View for the fragment's UI.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment using view binding
        mBinding = FragmentPhotoViewBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();

        // Initialize UI elements
        mPhotoView = mBinding.photoViewFragmentPhotoView;
        mToolbar = mBinding.photoViewFragmentToolbar;
        mBackButton = mBinding.photoViewFragmentBackButton;

        // Retrieve the photo URI from the arguments
        Bundle args = getArguments();
        if (args != null) {
            mUriPhoto = args.getParcelable("uriPhoto");
        }
        return view;
    }

    /**
     * Called after the view has been created.
     *
     * @param view               The View returned by onCreateView.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the toolbar and UI elements
        initToolbar();
        initUI();
    }

    /**
     * Initializes the toolbar by setting up the back button click listener.
     */
    private void initToolbar() {
        mBackButton.setOnClickListener(view -> requireActivity().onBackPressed());
    }

    /**
     * Initializes the UI by loading and displaying the photo using Glide.
     */
    private void initUI() {
        Glide.with(requireContext())
                .load(mUriPhoto)
                .error(R.drawable.baseline_cancel_24)
                .into(mPhotoView);
    }
}