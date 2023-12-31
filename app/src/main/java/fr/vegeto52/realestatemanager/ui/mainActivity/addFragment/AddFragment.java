package fr.vegeto52.realestatemanager.ui.mainActivity.addFragment;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.vegeto52.realestatemanager.EditDescriptionDialog;
import fr.vegeto52.realestatemanager.NotificationHelper;
import fr.vegeto52.realestatemanager.R;
import fr.vegeto52.realestatemanager.database.repository.ViewModelFactory;
import fr.vegeto52.realestatemanager.databinding.FragmentAddBinding;
import fr.vegeto52.realestatemanager.databinding.FragmentEditBinding;
import fr.vegeto52.realestatemanager.model.Photo;
import fr.vegeto52.realestatemanager.model.RealEstate;
import fr.vegeto52.realestatemanager.model.ResultsGeocodingApi;
import fr.vegeto52.realestatemanager.ui.cameraActivity.CameraActivity;
import fr.vegeto52.realestatemanager.ui.mainActivity.editFragment.EditFragment;

public class AddFragment extends Fragment implements AddFragmentPhotoAdapter.OnEditDescriptionClickListener, EditDescriptionDialog.OnInputSelected {

    private static final int PICK_IMAGES_REQUEST_CODE = 1;
    private static final int CAMERA_ACTIVITY_REQUEST_CODE = 100;

    FragmentAddBinding mBinding;
    EditText mTypeEditText;
    EditText mDescriptionEditText;
    EditText mAddressEditText;
    EditText mSurfaceEditText;
    EditText mNumberOfRoomsEditText;
    EditText mPointsOfInterestEditText;
    EditText mDateOfEntryEditText;
    EditText mDateOfSaleEditText;
    EditText mAgentEditText;
    EditText mPriceEditText;
    Button mSaveButton;
    Button mCancelButton;
    Toolbar mToolbar;
    ImageButton mBackButton;
    RecyclerView mRecyclerViewViewPhoto;
    Button mSelectPhotosButton;
    Button mTakePhoto;
    List<Photo> mPhotoList = new ArrayList<>();
    List<Photo> mPhotoListRestored;
    String mDescription;
    int mPositionPhoto;

    private AddFragmentViewModel mAddFragmentViewModel;

    public AddFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mPhotoList = new ArrayList<>(savedInstanceState.getParcelableArrayList("PhotoListRestored"));
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mTypeEditText != null && mDescriptionEditText != null && mAddressEditText != null && mSurfaceEditText != null && mNumberOfRoomsEditText != null && mPointsOfInterestEditText != null && mDateOfEntryEditText != null && mDateOfSaleEditText != null && mAgentEditText != null && mPriceEditText != null) {
            outState.putString("TypeEditText", mTypeEditText.getText().toString());
            outState.putString("DescriptionEditText", mDescriptionEditText.getText().toString());
            outState.putString("AddressEditText", mAddressEditText.getText().toString());
            outState.putString("SurfaceEditText", mSurfaceEditText.getText().toString());
            outState.putString("NumberOfRoomsEditText", mNumberOfRoomsEditText.getText().toString());
            outState.putString("PointsOfInterestEditText", mPointsOfInterestEditText.getText().toString());
            outState.putString("DateOfEntryEditText", mDateOfEntryEditText.getText().toString());
            outState.putString("DateOfSaleEditText", mDateOfSaleEditText.getText().toString());
            outState.putString("AgentEditText", mAgentEditText.getText().toString());
            outState.putString("PriceEditText", mPriceEditText.getText().toString());
            outState.putParcelableArrayList("PhotoListRestored", (ArrayList<? extends Parcelable>) mPhotoList);
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            mTypeEditText.setText(savedInstanceState.getString("TypeEditText"));
            mDescriptionEditText.setText(savedInstanceState.getString("DescriptionEditText"));
            mAddressEditText.setText(savedInstanceState.getString("AddressEditText"));
            mSurfaceEditText.setText(savedInstanceState.getString("SurfaceEditText"));
            mNumberOfRoomsEditText.setText(savedInstanceState.getString("NumberOfRoomsEditText"));
            mPointsOfInterestEditText.setText(savedInstanceState.getString("PointsOfInterestEditText"));
            mDateOfEntryEditText.setText(savedInstanceState.getString("DateOfEntryEditText"));
            mDateOfSaleEditText.setText(savedInstanceState.getString("DateOfSaleEditText"));
            mAgentEditText.setText(savedInstanceState.getString("AgentEditText"));
            mPriceEditText.setText(savedInstanceState.getString("PriceEditText"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentAddBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();

        mTypeEditText = view.findViewById(R.id.type_add_fragment);
        mDescriptionEditText = view.findViewById(R.id.description_add_fragment);
        mAddressEditText = view.findViewById(R.id.address_add_fragment);
        mSurfaceEditText = view.findViewById(R.id.surface_add_fragment);
        mNumberOfRoomsEditText = view.findViewById(R.id.number_of_rooms_add_fragment);
        mPointsOfInterestEditText = view.findViewById(R.id.points_of_interest_add_fragment);
        mDateOfEntryEditText = view.findViewById(R.id.date_of_entry_add_fragment);
        mDateOfSaleEditText = view.findViewById(R.id.date_of_sale_add_fragment);
        mAgentEditText = view.findViewById(R.id.agent_add_fragment);
        mPriceEditText = view.findViewById(R.id.price_add_fragment);
        mRecyclerViewViewPhoto = view.findViewById(R.id.recyclerview_photo_add_fragment);

        mSelectPhotosButton = view.findViewById(R.id.select_photos_button_add_fragment);
        mTakePhoto = view.findViewById(R.id.take_photo_button_add_fragment);
        mSaveButton = view.findViewById(R.id.save_button_add_fragment);
        mCancelButton = view.findViewById(R.id.cancel_button_add_fragment);

        mToolbar = view.findViewById(R.id.add_fragment_toolbar);
        mBackButton = view.findViewById(R.id.add_fragment_back_button);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
    }

    private void initViewModel() {
        mAddFragmentViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(requireContext())).get(AddFragmentViewModel.class);
        initToolbar();
        initButton();
        initRecyclerView();
    }

    private void initToolbar() {
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().onBackPressed();
            }
        });
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        AddFragmentPhotoAdapter.OnRemovePhotoClickListener removePhotoClickListener = new AddFragmentPhotoAdapter.OnRemovePhotoClickListener() {
            @Override
            public void onRemoveClick(int position) {
                mPhotoList.remove(position);
                mRecyclerViewViewPhoto.getAdapter().notifyDataSetChanged();
                mBinding.photoCarouselEmptyAddFragment.setVisibility(mPhotoList.isEmpty() ? View.VISIBLE : View.GONE);
            }
        };
        mBinding.photoCarouselEmptyAddFragment.setVisibility(mPhotoList.isEmpty() ? View.VISIBLE : View.GONE);
        AddFragmentPhotoAdapter addFragmentPhotoAdapter;
        addFragmentPhotoAdapter = new AddFragmentPhotoAdapter(mPhotoList, removePhotoClickListener);
        addFragmentPhotoAdapter.setOnEditDescriptionClickListener(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(), layoutManager.getOrientation());
        mRecyclerViewViewPhoto.addItemDecoration(dividerItemDecoration);
        mRecyclerViewViewPhoto.setLayoutManager(layoutManager);
        mRecyclerViewViewPhoto.setAdapter(addFragmentPhotoAdapter);

    }

    private void initButton() {
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().onBackPressed();
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPhotoList.isEmpty()) {
                    Toast.makeText(getContext(), "Need one photo with description", Toast.LENGTH_LONG).show();
                } else {
                    boolean photoWithDescription = false;
                    for (Photo photo : mPhotoList) {
                        if (photo.getDescription() != null) {
                            photoWithDescription = true;
                        }
                    }
                    if (photoWithDescription) {
                        RealEstate newRealEstate = new RealEstate();
                        newRealEstate.setType(mTypeEditText.getText().toString());
                        newRealEstate.setDescription(mDescriptionEditText.getText().toString());
                        newRealEstate.setAddress(mAddressEditText.getText().toString());
                        if (!TextUtils.isEmpty(mSurfaceEditText.getText().toString().trim())) {
                            try {
                                newRealEstate.setSurface(Double.parseDouble(mSurfaceEditText.getText().toString()));
                            } catch (NumberFormatException e) {
                                mSurfaceEditText.setError("Invalid");
                                return;
                            }
                        } else {
                            mSurfaceEditText.setText(null);
                        }
                        if (!TextUtils.isEmpty(mNumberOfRoomsEditText.getText().toString().trim())) {
                            try {
                                newRealEstate.setNumberOfRooms(Integer.parseInt(mNumberOfRoomsEditText.getText().toString()));
                            } catch (NumberFormatException e) {
                                mNumberOfRoomsEditText.setError("Invalid");
                                return;
                            }
                        }
                        newRealEstate.setPointsOfInterest(mPointsOfInterestEditText.getText().toString());
                        newRealEstate.setDateOfEntry(mDateOfEntryEditText.getText().toString());
                        newRealEstate.setDateOfSale(mDateOfSaleEditText.getText().toString());
                        newRealEstate.setAgent(mAgentEditText.getText().toString());
                        if (!TextUtils.isEmpty(mPriceEditText.getText().toString())) {
                            try {
                                newRealEstate.setPrice(Double.parseDouble(mPriceEditText.getText().toString()));
                            } catch (NumberFormatException e) {
                                mPriceEditText.setError("Invalid");
                            }
                        }
                        if (!TextUtils.isEmpty(mDateOfSaleEditText.getText().toString())) {
                            newRealEstate.setStatut(true);
                        } else {
                            newRealEstate.setStatut(false);
                        }
                        newRealEstate.setPhoto("TODO");

                        if (!TextUtils.isEmpty(mAddressEditText.getText().toString())) {
                            mAddFragmentViewModel.getGeocoding(mAddressEditText.getText().toString(), getViewLifecycleOwner(), new AddFragmentViewModel.GeocodingCallback() {
                                @Override
                                public void onGeocodingComplete(Double latitude, Double longitude) {
                                    newRealEstate.setLatitude(latitude);
                                    newRealEstate.setLongitude(longitude);
                                    long realEstateId = mAddFragmentViewModel.insertRealEstateAndGetId(newRealEstate);
                                    if (realEstateId != -1) {
                                        for (Photo photo : mPhotoList) {
                                            photo.setRealEstateId(realEstateId);
                                            mAddFragmentViewModel.insertPhoto(photo);
                                        }
                                        NotificationHelper.showNotification(getContext(), "Success", "RealEstate added");
                                    }
                                    if (getFragmentManager() != null) {
                                        getFragmentManager().popBackStack();
                                    }
                                }
                            });
                        } else {
                            long realEstateId = mAddFragmentViewModel.insertRealEstateAndGetId(newRealEstate);
                            if (realEstateId != -1) {
                                for (Photo photo : mPhotoList) {
                                    photo.setRealEstateId(realEstateId);
                                    mAddFragmentViewModel.insertPhoto(photo);
                                }
                                NotificationHelper.showNotification(getContext(), "Success", "RealEstate added");
                            }
                            if (getFragmentManager() != null) {
                                getFragmentManager().popBackStack();
                            }
                        }
                    } else {
                        Toast.makeText(getContext(), "Need one photo with description", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        mSelectPhotosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        mTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, PICK_IMAGES_REQUEST_CODE);
    }

    private void openCamera() {
        Intent intent = new Intent(getContext(), CameraActivity.class);
        startActivityForResult(intent, CAMERA_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGES_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            ClipData clipData = data.getClipData();
            if (clipData != null) {
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    Uri imageUri = clipData.getItemAt(i).getUri();
                    Photo photo = new Photo();
                    photo.setUriPhoto(imageUri);
                    mPhotoList.add(photo);
                }
            } else {
                Uri imageUri = data.getData();
                Photo photo = new Photo();
                photo.setUriPhoto(imageUri);
                mPhotoList.add(photo);
            }
            mRecyclerViewViewPhoto.getAdapter().notifyDataSetChanged();
            mBinding.photoCarouselEmptyAddFragment.setVisibility(mPhotoList.isEmpty() ? View.VISIBLE : View.GONE);
        } else if (requestCode == CAMERA_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            List<Uri> capturedPhotoUris = data.getParcelableArrayListExtra("capturedPhotoUris");
            for (Uri uri : capturedPhotoUris) {
                Photo photo = new Photo();
                photo.setUriPhoto(uri);
                mPhotoList.add(photo);
            }
            mRecyclerViewViewPhoto.getAdapter().notifyDataSetChanged();
            mBinding.photoCarouselEmptyAddFragment.setVisibility(mPhotoList.isEmpty() ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void sendInput(String input) {
        mDescription = input;
        if (TextUtils.isEmpty(input)) {
            mPhotoList.get(mPositionPhoto).setDescription(null);
        } else {
            mPhotoList.get(mPositionPhoto).setDescription(mDescription);
        }
        mRecyclerViewViewPhoto.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onEditDescriptionClick(int position) {
        mPositionPhoto = position;
        EditDescriptionDialog editDescriptionDialog = new EditDescriptionDialog();
        editDescriptionDialog.setTargetFragment(AddFragment.this, 0);
        Bundle args = new Bundle();
        args.putInt("position", position);
        editDescriptionDialog.setArguments(args);
        editDescriptionDialog.show(getFragmentManager(), "EditDescriptionDialog");
    }
}