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

import fr.vegeto52.realestatemanager.EditDescriptionDialog;
import fr.vegeto52.realestatemanager.R;
import fr.vegeto52.realestatemanager.database.repository.ViewModelFactory;
import fr.vegeto52.realestatemanager.databinding.FragmentEditBinding;
import fr.vegeto52.realestatemanager.model.Photo;
import fr.vegeto52.realestatemanager.model.RealEstate;
import fr.vegeto52.realestatemanager.ui.cameraActivity.CameraActivity;

public class EditFragment extends Fragment implements EditFragmentPhotoAdapter.OnEditDescriptionClickListener, EditDescriptionDialog.OnInputSelected {

    private static final int PICK_IMAGES_REQUEST_CODE = 1;
    private static final int CAMERA_ACTIVITY_REQUEST_CODE = 100;

    FragmentEditBinding mBinding;
    EditText mTypeEditText, mDescriptionEditText, mAddressEditText, mSurfaceEditText, mNumberOfRoomsEditText, mPointsOfInterestEditText, mDateOfEntryEditText, mDateOfSaleEditText, mAgentEditText, mPriceEditText;
    RecyclerView mRecyclerViewPhoto;
    Button mSaveButton, mCancelButton, mSelectPhotosButton, mTakePhoto;
    Toolbar mToolbar;
    ImageButton mBackButton;
    private EditFragmentViewModel mEditFragmentViewModel;
    private RealEstate mRealEstate;
    Long mRealEstateId;
    List<Photo> mPhotoList = new ArrayList<>();
    int mPositionPhoto;
    String mDescription;
    private boolean saveRestored = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mPhotoList = new ArrayList<>(Objects.requireNonNull(savedInstanceState.getParcelableArrayList("PhotoListRestored2")));
            saveRestored = true;
        }
    }

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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentEditBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();

        mTypeEditText = view.findViewById(R.id.type_edit_fragment);
        mDescriptionEditText = view.findViewById(R.id.description_edit_fragment);
        mAddressEditText = view.findViewById(R.id.address_edit_fragment);
        mSurfaceEditText = view.findViewById(R.id.surface_edit_fragment);
        mNumberOfRoomsEditText = view.findViewById(R.id.number_of_rooms_edit_fragment);
        mPointsOfInterestEditText = view.findViewById(R.id.points_of_interest_edit_fragment);
        mDateOfEntryEditText = view.findViewById(R.id.date_of_entry_edit_fragment);
        mDateOfSaleEditText = view.findViewById(R.id.date_of_sale_edit_fragment);
        mAgentEditText = view.findViewById(R.id.agent_edit_fragment);
        mPriceEditText = view.findViewById(R.id.price_edit_fragment);
        mRecyclerViewPhoto = view.findViewById(R.id.recyclerview_photo_edit_fragment);

        mSaveButton = view.findViewById(R.id.save_button_edit_fragment);
        mCancelButton = view.findViewById(R.id.cancel_button_edit_fragment);

        mToolbar = view.findViewById(R.id.edit_fragment_toolbar);
        mBackButton = view.findViewById(R.id.edit_fragment_back_button);

        mSelectPhotosButton = view.findViewById(R.id.select_photos_button_edit_fragment);
        mTakePhoto = view.findViewById(R.id.take_photo_button_edit_fragment);

        Bundle args = getArguments();
        if (args != null) {
            mRealEstateId = args.getLong("idRealEstate");
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
    }

    private void initViewModel() {
        mEditFragmentViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(requireContext())).get(EditFragmentViewModel.class);
        mEditFragmentViewModel.getRealEstateLiveData(mRealEstateId).observe(getViewLifecycleOwner(), realEstate -> {
            mRealEstate = realEstate;
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

    private void initToolbar() {
        mBackButton.setOnClickListener(view -> requireActivity().onBackPressed());
    }

    private void initUi() {
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

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        @SuppressLint("NotifyDataSetChanged") EditFragmentPhotoAdapter.OnRemovePhotoClickListener removePhotoClickListener = position -> {
            mPhotoList.remove(position);
            Objects.requireNonNull(mRecyclerViewPhoto.getAdapter()).notifyDataSetChanged();
            if (mPhotoList.isEmpty()) {
                mBinding.photoCarouselEmptyEditFragment.setVisibility(View.VISIBLE);
            }
        };
        EditFragmentPhotoAdapter editFragmentPhotoAdapter = new EditFragmentPhotoAdapter(mPhotoList, removePhotoClickListener);
        editFragmentPhotoAdapter.setOnEditDescriptionClickListener(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(), layoutManager.getOrientation());
        mRecyclerViewPhoto.addItemDecoration(dividerItemDecoration);
        mRecyclerViewPhoto.setLayoutManager(layoutManager);
        mRecyclerViewPhoto.setAdapter(editFragmentPhotoAdapter);
    }

    private void initButton() {
        mCancelButton.setOnClickListener(view -> requireActivity().onBackPressed());

        mSaveButton.setOnClickListener(view -> {
            if (mPhotoList.isEmpty()) {
                Toast.makeText(getContext(), "Need one photo with description", Toast.LENGTH_LONG).show();
            } else {
                boolean photoWithDescription = false;
                for (Photo photo : mPhotoList) {
                    if (photo.getDescription() != null) {
                        photoWithDescription = true;
                        break;
                    }
                }
                if (photoWithDescription) {
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

                    mEditFragmentViewModel.deleteAllPhotos(mRealEstateId);
                    for (Photo photo2 : mPhotoList) {
                        mEditFragmentViewModel.insertPhoto(photo2);
                    }

                    if (!TextUtils.isEmpty(mAddressEditText.getText().toString())) {
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

        mSelectPhotosButton.setOnClickListener(view -> openGallery());

        mTakePhoto.setOnClickListener(view -> openCamera());
    }

    private void openCamera() {
        Intent intent = new Intent(getContext(), CameraActivity.class);
        startActivityForResult(intent, CAMERA_ACTIVITY_REQUEST_CODE);
    }

    @SuppressLint("IntentReset")
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, PICK_IMAGES_REQUEST_CODE);
    }

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