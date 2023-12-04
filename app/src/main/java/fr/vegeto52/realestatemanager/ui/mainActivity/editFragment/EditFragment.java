package fr.vegeto52.realestatemanager.ui.mainActivity.editFragment;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fr.vegeto52.realestatemanager.R;
import fr.vegeto52.realestatemanager.database.repository.ViewModelFactory;
import fr.vegeto52.realestatemanager.databinding.FragmentEditBinding;
import fr.vegeto52.realestatemanager.model.RealEstate;

public class EditFragment extends Fragment {

    private static final int PICK_IMAGES_REQUEST_CODE = 1;

    FragmentEditBinding mBinding;
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
    RecyclerView mRecyclerViewPhoto;
    Button mSaveButton;
    Button mCancelButton;
    Toolbar mToolbar;
    ImageButton mBackButton;
    Button mSelectPhotosButton;
    private EditFragmentViewModel mEditFragmentViewModel;
    private RealEstate mRealEstate;
    Long mRealEstateId;
    List<Uri> mUriList = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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

        Bundle args = getArguments();
        if (args != null){
            mRealEstateId = args.getLong("idRealEstate");
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
    }

    private void initViewModel(){
        mEditFragmentViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(requireContext())).get(EditFragmentViewModel.class);
        mEditFragmentViewModel.getRealEstateLiveData(mRealEstateId).observe(getViewLifecycleOwner(), new Observer<RealEstate>() {
            @Override
            public void onChanged(RealEstate realEstate) {
                mRealEstate = realEstate;
                initUi();
                initToolbar();
                initButton();
            }
        });
    }

    private void initToolbar(){
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().onBackPressed();
            }
        });
    }

    private void initUi(){
        mTypeEditText.setText(!TextUtils.isEmpty(mRealEstate.getType()) ? mRealEstate.getType() : "");
        mDescriptionEditText.setText(!TextUtils.isEmpty(mRealEstate.getDescription()) ? mRealEstate.getDescription() : "");
        mAddressEditText.setText(!TextUtils.isEmpty(mRealEstate.getAddress()) ? mRealEstate.getAddress() : "");
        mSurfaceEditText.setText(!TextUtils.isEmpty(String.valueOf(mRealEstate.getSurface())) ? String.valueOf(mRealEstate.getSurface()) : "");
        mNumberOfRoomsEditText.setText(!TextUtils.isEmpty(String.valueOf(mRealEstate.getNumberOfRooms())) ? String.valueOf(mRealEstate.getNumberOfRooms()) : "");
        mPointsOfInterestEditText.setText(!TextUtils.isEmpty(mRealEstate.getPointsOfInterest()) ? mRealEstate.getPointsOfInterest() : "");
        mDateOfEntryEditText.setText(!TextUtils.isEmpty(mRealEstate.getDateOfEntry()) ? mRealEstate.getDateOfEntry() : "");
        mDateOfSaleEditText.setText(!TextUtils.isEmpty(mRealEstate.getDateOfSale()) ? mRealEstate.getDateOfSale() : "");
        mAgentEditText.setText(!TextUtils.isEmpty(mRealEstate.getAgent()) ? mRealEstate.getAgent() : "");
        if (!TextUtils.isEmpty(String.valueOf(mRealEstate.getPrice()))){
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
            String priceFormate = numberFormat.format(mRealEstate.getPrice());
            priceFormate = priceFormate.replaceAll(",", "");
            mPriceEditText.setText(priceFormate);
        } else {
            mPriceEditText.setText("");
        }
    }

    private void initButton(){
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().onBackPressed();
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RealEstate updatedRealEstate = new RealEstate();
                updatedRealEstate.setId(mRealEstateId);
                updatedRealEstate.setType(mTypeEditText.getText().toString());
                updatedRealEstate.setDescription(mDescriptionEditText.getText().toString());
                updatedRealEstate.setAddress(mAddressEditText.getText().toString());
                try {
                    updatedRealEstate.setSurface(Double.parseDouble(mSurfaceEditText.getText().toString()));
                } catch (NumberFormatException e){
                    mSurfaceEditText.setError("Invalid");
                    return;
                }
                try {
                    updatedRealEstate.setNumberOfRooms(Integer.parseInt(mNumberOfRoomsEditText.getText().toString()));
                } catch (NumberFormatException e){
                    mNumberOfRoomsEditText.setError("Invalid");
                }
                updatedRealEstate.setPointsOfInterest(mPointsOfInterestEditText.getText().toString());
                updatedRealEstate.setDateOfEntry(mDateOfEntryEditText.getText().toString());
                updatedRealEstate.setDateOfSale(mDateOfSaleEditText.getText().toString());
                updatedRealEstate.setAgent(mAgentEditText.getText().toString());
                try {
                    updatedRealEstate.setPrice(Double.parseDouble(mPriceEditText.getText().toString()));
                } catch (NumberFormatException e) {
                    mPriceEditText.setError("Invalid");
                }
                if (!TextUtils.isEmpty(mDateOfSaleEditText.getText().toString())){
                    updatedRealEstate.setStatut(true);
                } else {
                    updatedRealEstate.setStatut(false);
                }
                updatedRealEstate.setPhoto("TODO");

                mEditFragmentViewModel.updateRealEstate(updatedRealEstate);
                getFragmentManager().popBackStack();
            }
        });

        mSelectPhotosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
    }

    private void openGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, PICK_IMAGES_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGES_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            ClipData clipData = data.getClipData();
            if (clipData != null){
                for (int i = 0; i < clipData.getItemCount(); i++){
                    Uri imageUri = clipData.getItemAt(i).getUri();
                    mUriList.add(imageUri);
                }
            } else {
                Uri imageUri = data.getData();
                mUriList.add(imageUri);
            }
        }
    }
}