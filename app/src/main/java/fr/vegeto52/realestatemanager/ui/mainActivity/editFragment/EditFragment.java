package fr.vegeto52.realestatemanager.ui.mainActivity.editFragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import fr.vegeto52.realestatemanager.database.repository.ViewModelFactory;
import fr.vegeto52.realestatemanager.databinding.FragmentEditBinding;
import fr.vegeto52.realestatemanager.model.Photo;
import fr.vegeto52.realestatemanager.model.RealEstate;
import fr.vegeto52.realestatemanager.ui.cameraActivity.CameraActivity;
import fr.vegeto52.realestatemanager.utils.EditDescriptionDialog;
import fr.vegeto52.realestatemanager.utils.Utils;

/**
 * The EditFragment class extends Fragment and provides functionality for editing RealEstate details.
 * It includes UI elements such as text fields, buttons, and a photo carousel.
 */
public class EditFragment extends Fragment implements EditFragmentPhotoAdapter.OnEditDescriptionClickListener, EditDescriptionDialog.OnInputSelected {

    // Constants for request codes
    private static final int PICK_IMAGES_REQUEST_CODE = 1;
    private static final int CAMERA_ACTIVITY_REQUEST_CODE = 100;

    // View binding for the fragment
    FragmentEditBinding mBinding;

    // UI elements
    EditText mTypeEditText, mDescriptionEditText, mAddressEditText, mSurfaceEditText, mNumberOfRoomsEditText, mPointsOfInterestEditText, mDateOfEntryEditText, mDateOfSaleEditText, mAgentEditText, mPriceEditText;
    RecyclerView mRecyclerViewPhoto;
    Button mSaveButton, mCancelButton, mSelectPhotosButton, mTakePhoto;
    Toolbar mToolbar;
    ImageButton mBackButton;

    // ViewModel for managing data
    private EditFragmentViewModel mEditFragmentViewModel;

    // RealEstate object being edited
    private RealEstate mRealEstate;

    // ID of the RealEstate being edited
    Long mRealEstateId;

    // List of photos associated with the RealEstate
    List<Photo> mPhotoList = new ArrayList<>();

    // Position of the current photo being edited
    int mPositionPhoto;

    // Description associated with a photo
    String mDescription;

    // Flag to check if the state is restored
    private boolean saveRestored = false;

    /**
     * Called when the fragment is created. Restores the state if available.
     *
     * @param savedInstanceState A Bundle containing the saved state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mPhotoList = new ArrayList<>(Objects.requireNonNull(savedInstanceState.getParcelableArrayList("PhotoListRestored2")));
            saveRestored = true;
        }
    }

    /**
     * Called to save the current state of the fragment.
     *
     * @param outState A Bundle in which to place the saved state.
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mTypeEditText != null && mDescriptionEditText != null && mAddressEditText != null && mSurfaceEditText != null && mNumberOfRoomsEditText != null && mPointsOfInterestEditText != null && mDateOfEntryEditText != null && mDateOfSaleEditText != null && mAgentEditText != null && mPriceEditText != null) {
            outState.putString("TypeEditText2", mTypeEditText.getText().toString());
            outState.putString("DescriptionEditText2", mDescriptionEditText.getText().toString());
            outState.putString("AddressEditText2", mAddressEditText.getText().toString());
            outState.putString("SurfaceEditText2", mSurfaceEditText.getText().toString());
            outState.putString("NumberOfRoomsEditText2", mNumberOfRoomsEditText.getText().toString());
            outState.putString("PointsOfInterestEditText2", mPointsOfInterestEditText.getText().toString());
            outState.putString("DateOfEntryEditText2", mDateOfEntryEditText.getText().toString());
            outState.putString("DateOfSaleEditText2", mDateOfSaleEditText.getText().toString());
            outState.putString("AgentEditText2", mAgentEditText.getText().toString());
            outState.putString("PriceEditText2", mPriceEditText.getText().toString());
            outState.putParcelableArrayList("PhotoListRestored2", (ArrayList<? extends Parcelable>) mPhotoList);
        }
    }

    /**
     * Called when the view state of the fragment is restored.
     *
     * @param savedInstanceState A Bundle containing the saved state.
     */
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            mTypeEditText.setText(savedInstanceState.getString("TypeEditText2"));
            mDescriptionEditText.setText(savedInstanceState.getString("DescriptionEditText2"));
            mAddressEditText.setText(savedInstanceState.getString("AddressEditText2"));
            mSurfaceEditText.setText(savedInstanceState.getString("SurfaceEditText2"));
            mNumberOfRoomsEditText.setText(savedInstanceState.getString("NumberOfRoomsEditText2"));
            mPointsOfInterestEditText.setText(savedInstanceState.getString("PointsOfInterestEditText2"));
            mDateOfEntryEditText.setText(savedInstanceState.getString("DateOfEntryEditText2"));
            mDateOfSaleEditText.setText(savedInstanceState.getString("DateOfSaleEditText2"));
            mAgentEditText.setText(savedInstanceState.getString("AgentEditText2"));
            mPriceEditText.setText(savedInstanceState.getString("PriceEditText2"));
        }
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
        mBinding = FragmentEditBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();

        // Initialize UI elements
        mTypeEditText = mBinding.typeEditFragment;
        mDescriptionEditText = mBinding.descriptionEditFragment;
        mAddressEditText = mBinding.addressEditFragment;
        mSurfaceEditText = mBinding.surfaceEditFragment;
        mNumberOfRoomsEditText = mBinding.numberOfRoomsEditFragment;
        mPointsOfInterestEditText = mBinding.pointsOfInterestEditFragment;
        mDateOfEntryEditText = mBinding.dateOfEntryEditFragment;
        mDateOfSaleEditText = mBinding.dateOfSaleEditFragment;
        mAgentEditText = mBinding.agentEditFragment;
        mPriceEditText = mBinding.priceEditFragment;
        mRecyclerViewPhoto = mBinding.recyclerviewPhotoEditFragment;

        mSaveButton = mBinding.saveButtonEditFragment;
        mCancelButton = mBinding.cancelButtonEditFragment;

        mToolbar = mBinding.editFragmentToolbar;
        mBackButton = mBinding.editFragmentBackButton;

        mSelectPhotosButton = mBinding.selectPhotosButtonEditFragment;
        mTakePhoto = mBinding.takePhotoButtonEditFragment;

        // Get RealEstate ID from arguments
        Bundle args = getArguments();
        if (args != null) {
            mRealEstateId = args.getLong("idRealEstate");
        }
        return view;
    }

    /**
     * Called when the fragment's view has been created.
     *
     * @param view               The View returned by onCreateView.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
    }

    /**
     * Initializes the ViewModel and observes changes in RealEstate data.
     */
    private void initViewModel() {
        // Initialize ViewModel
        mEditFragmentViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(requireContext())).get(EditFragmentViewModel.class);

        // Observe RealEstate data changes
        mEditFragmentViewModel.getRealEstateLiveData(mRealEstateId).observe(getViewLifecycleOwner(), realEstate -> {
            mRealEstate = realEstate;

            // Observe list of photos associated with the RealEstate
            mEditFragmentViewModel.getListPhotoToRealEstate(mRealEstateId).observe(getViewLifecycleOwner(), photos -> {
                if (!saveRestored) {
                    mPhotoList = photos;
                    initUi();
                }

                initRecyclerView();
                initToolbar();
                initButton();

                mBinding.photoCarouselEmptyEditFragment.setVisibility(mPhotoList.isEmpty() ? View.VISIBLE : View.GONE);
            });
        });
    }

    /**
     * Initializes the toolbar with a click listener for the back button.
     */
    private void initToolbar() {
        // Set click listener for the back button
        mBackButton.setOnClickListener(view -> requireActivity().onBackPressed());
    }

    /**
     * Initializes the UI elements with data from the RealEstate object.
     */
    private void initUi() {
        // Set text for UI elements based on RealEstate data
        mTypeEditText.setText(!TextUtils.isEmpty(mRealEstate.getType()) ? mRealEstate.getType() : "");
        mDescriptionEditText.setText(!TextUtils.isEmpty(mRealEstate.getDescription()) ? mRealEstate.getDescription() : "");
        mAddressEditText.setText(!TextUtils.isEmpty(mRealEstate.getAddress()) ? mRealEstate.getAddress() : "");
        mSurfaceEditText.setText((mRealEstate.getSurface() != null) ? String.valueOf(mRealEstate.getSurface()) : "");
        mNumberOfRoomsEditText.setText((mRealEstate.getNumberOfRooms() != null) ? String.valueOf(mRealEstate.getNumberOfRooms()) : "");
        mPointsOfInterestEditText.setText(!TextUtils.isEmpty(mRealEstate.getPointsOfInterest()) ? mRealEstate.getPointsOfInterest() : "");
        mDateOfEntryEditText.setText(!TextUtils.isEmpty(mRealEstate.getDateOfEntry()) ? mRealEstate.getDateOfEntry() : "");
        mDateOfSaleEditText.setText(!TextUtils.isEmpty(mRealEstate.getDateOfSale()) ? mRealEstate.getDateOfSale() : "");
        mAgentEditText.setText(!TextUtils.isEmpty(mRealEstate.getAgent()) ? mRealEstate.getAgent() : "");

        Double price = mRealEstate.getPrice();
        String priceText = (price != null) ? (NumberFormat.getNumberInstance(Locale.getDefault()).format(price)) : "";
        priceText = priceText.replaceAll(",", "");
        mPriceEditText.setText(priceText);
    }

    /**
     * Initializes the RecyclerView for displaying photos.
     */
    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);

        // Listener for removing a photo
        @SuppressLint("NotifyDataSetChanged") EditFragmentPhotoAdapter.OnRemovePhotoClickListener removePhotoClickListener = position -> {
            mPhotoList.remove(position);
            Objects.requireNonNull(mRecyclerViewPhoto.getAdapter()).notifyDataSetChanged();
            if (mPhotoList.isEmpty()) {
                mBinding.photoCarouselEmptyEditFragment.setVisibility(View.VISIBLE);
            }
        };

        // Create and set up the adapter for the RecyclerView
        EditFragmentPhotoAdapter editFragmentPhotoAdapter = new EditFragmentPhotoAdapter(mPhotoList, removePhotoClickListener);
        editFragmentPhotoAdapter.setOnEditDescriptionClickListener(this);

        // Add divider decoration to the RecyclerView
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(), layoutManager.getOrientation());
        mRecyclerViewPhoto.addItemDecoration(dividerItemDecoration);

        // Set layout manager and adapter for the RecyclerView
        mRecyclerViewPhoto.setLayoutManager(layoutManager);
        mRecyclerViewPhoto.setAdapter(editFragmentPhotoAdapter);
    }

    /**
     * Initializes click listeners for various buttons in the fragment.
     */
    private void initButton() {
        // Cancel button click listener
        mCancelButton.setOnClickListener(view -> requireActivity().onBackPressed());

        // Save button click listener
        mSaveButton.setOnClickListener(view -> {
            if (mPhotoList.isEmpty()) {
                Toast.makeText(getContext(), "Need one photo with description", Toast.LENGTH_LONG).show();
            } else {
                // Check if at least one photo has a description
                boolean photoWithDescription = false;
                for (Photo photo : mPhotoList) {
                    if (photo.getDescription() != null) {
                        photoWithDescription = true;
                        break;
                    }
                }
                if (photoWithDescription) {
                    // Create an updated RealEstate object with edited details
                    RealEstate updatedRealEstate = new RealEstate();
                    updatedRealEstate.setId(mRealEstateId);
                    updatedRealEstate.setType(mTypeEditText.getText().toString());
                    updatedRealEstate.setDescription(mDescriptionEditText.getText().toString());
                    updatedRealEstate.setAddress(mAddressEditText.getText().toString());

                    if (!TextUtils.isEmpty(mSurfaceEditText.getText().toString().trim())) {
                        try {
                            updatedRealEstate.setSurface(Double.parseDouble(mSurfaceEditText.getText().toString()));
                        } catch (NumberFormatException e) {
                            mSurfaceEditText.setError("Invalid");
                            return;
                        }
                    }
                    if (!TextUtils.isEmpty(mNumberOfRoomsEditText.getText().toString().trim())) {
                        try {
                            updatedRealEstate.setNumberOfRooms(Integer.parseInt(mNumberOfRoomsEditText.getText().toString()));
                        } catch (NumberFormatException e) {
                            mNumberOfRoomsEditText.setError("Invalid");
                            return;
                        }
                    }
                    updatedRealEstate.setPointsOfInterest(mPointsOfInterestEditText.getText().toString());
                    updatedRealEstate.setDateOfEntry(mDateOfEntryEditText.getText().toString());
                    updatedRealEstate.setDateOfSale(mDateOfSaleEditText.getText().toString());
                    updatedRealEstate.setAgent(mAgentEditText.getText().toString());
                    if (!TextUtils.isEmpty(mPriceEditText.getText().toString().trim())) {
                        try {
                            updatedRealEstate.setPrice(Double.parseDouble(mPriceEditText.getText().toString()));
                        } catch (NumberFormatException e) {
                            mPriceEditText.setError("Invalid");
                        }
                    }
                    updatedRealEstate.setStatut(!TextUtils.isEmpty(mDateOfSaleEditText.getText().toString().trim()));

                    // Delete existing photos and insert new photos
                    mEditFragmentViewModel.deleteAllPhotos(mRealEstateId);
                    for (Photo photo2 : mPhotoList) {
                        mEditFragmentViewModel.insertPhoto(photo2);
                    }

                    // Update RealEstate data with geocoding if available
                    if (!TextUtils.isEmpty(mAddressEditText.getText().toString()) && Utils.isInternetAvailable2(requireContext())) {
                        mEditFragmentViewModel.getGeocoding(mAddressEditText.getText().toString(), (latitude, longitude) -> {
                            updatedRealEstate.setLatitude(latitude);
                            updatedRealEstate.setLongitude(longitude);
                            if (getFragmentManager() != null) {
                                mEditFragmentViewModel.updateRealEstate(updatedRealEstate);
                                getFragmentManager().popBackStack();
                            }
                        });
                    } else {
                        if (getFragmentManager() != null) {
                            mEditFragmentViewModel.updateRealEstate(updatedRealEstate);
                            getFragmentManager().popBackStack();
                        }
                    }
                } else {
                    Toast.makeText(getContext(), "Need one photo with description", Toast.LENGTH_LONG).show();
                }
            }

        });

        // Button to open gallery for selecting photos
        mSelectPhotosButton.setOnClickListener(view -> openGallery());

        // Button to open camera for capturing photos
        mTakePhoto.setOnClickListener(view -> openCamera());
    }

    /**
     * Opens the camera to capture photos.
     */
    private void openCamera() {
        Intent intent = new Intent(getContext(), CameraActivity.class);
        startActivityForResult(intent, CAMERA_ACTIVITY_REQUEST_CODE);
    }

    /**
     * Opens the gallery to select photos.
     */
    @SuppressLint("IntentReset")
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, PICK_IMAGES_REQUEST_CODE);
    }

    /**
     * Handles the result of activities such as gallery or camera.
     */
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGES_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            assert data != null;
            ClipData clipData = data.getClipData();
            if (clipData != null) {
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    Uri imageUri = clipData.getItemAt(i).getUri();
                    Photo photo = new Photo();
                    photo.setUriPhoto(imageUri);
                    photo.setRealEstateId(mRealEstateId);
                    mPhotoList.add(photo);
                }
            } else {
                Uri imageUri = data.getData();
                Photo photo = new Photo();
                photo.setUriPhoto(imageUri);
                photo.setRealEstateId(mRealEstateId);
                mPhotoList.add(photo);
            }
            Objects.requireNonNull(mRecyclerViewPhoto.getAdapter()).notifyDataSetChanged();
            mBinding.photoCarouselEmptyEditFragment.setVisibility(mPhotoList.isEmpty() ? View.VISIBLE : View.GONE);
        } else if (requestCode == CAMERA_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            List<Uri> capturedPhotoUris = data.getParcelableArrayListExtra("capturedPhotoUris");
            assert capturedPhotoUris != null;
            for (Uri uri : capturedPhotoUris) {
                Photo photo = new Photo();
                photo.setUriPhoto(uri);
                photo.setRealEstateId(mRealEstateId);
                mPhotoList.add(photo);
            }
            Objects.requireNonNull(mRecyclerViewPhoto.getAdapter()).notifyDataSetChanged();
            mBinding.photoCarouselEmptyEditFragment.setVisibility(mPhotoList.isEmpty() ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * Handles the click event when the user wants to edit the description of a photo.
     *
     * @param position The position of the clicked photo.
     */
    @Override
    public void onEditDescriptionClick(int position) {
        mPositionPhoto = position;
        EditDescriptionDialog editDescriptionDialog = new EditDescriptionDialog();
        editDescriptionDialog.setTargetFragment(EditFragment.this, 0);
        Bundle args = new Bundle();
        args.putInt("position", position);
        editDescriptionDialog.setArguments(args);
        assert getFragmentManager() != null;
        editDescriptionDialog.show(getFragmentManager(), "EditDescriptionDialog");
    }

    /**
     * Receives the user input from the EditDescriptionDialog and updates the description of the photo.
     *
     * @param input The user input for the description.
     */
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void sendInput(String input) {
        mDescription = input;
        if (TextUtils.isEmpty(input)) {
            mPhotoList.get(mPositionPhoto).setDescription(null);
        } else {
            mPhotoList.get(mPositionPhoto).setDescription(mDescription);
        }
        Objects.requireNonNull(mRecyclerViewPhoto.getAdapter()).notifyDataSetChanged();
    }
}