package fr.vegeto52.realestatemanager.ui.mainActivity.addFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import fr.vegeto52.realestatemanager.R;
import fr.vegeto52.realestatemanager.database.repository.ViewModelFactory;
import fr.vegeto52.realestatemanager.databinding.FragmentAddBinding;
import fr.vegeto52.realestatemanager.databinding.FragmentEditBinding;
import fr.vegeto52.realestatemanager.model.Photo;
import fr.vegeto52.realestatemanager.model.RealEstate;

public class AddFragment extends Fragment {

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

    private AddFragmentViewModel mAddFragmentViewModel;

    public AddFragment() {
        // Required empty public constructor
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

        mSaveButton = view.findViewById(R.id.save_button_add_fragment);
        mCancelButton = view.findViewById(R.id.cancel_button_add_fragment);

        mToolbar = view.findViewById(R.id.add_fragment_toolbar);
        mBackButton = view.findViewById(R.id.add_fragment_back_button);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewmodel();
    }

    private void initViewmodel(){
        mAddFragmentViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(requireContext())).get(AddFragmentViewModel.class);
        initToolbar();
        initButton();
    }

    private void initToolbar(){
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().onBackPressed();
            }
        });
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
                RealEstate newRealEstate = new RealEstate();
                newRealEstate.setType(mTypeEditText.getText().toString());
                newRealEstate.setDescription(mDescriptionEditText.getText().toString());
                newRealEstate.setAddress(mAddressEditText.getText().toString());
                if (!TextUtils.isEmpty(mSurfaceEditText.getText().toString().trim())){
                    try {
                        newRealEstate.setSurface(Double.parseDouble(mSurfaceEditText.getText().toString()));
                    } catch (NumberFormatException e){
                        mSurfaceEditText.setError("Invalid");
                        return;
                    }
                } else {
                    mSurfaceEditText.setText(null);
                }
                if (!TextUtils.isEmpty(mNumberOfRoomsEditText.getText().toString().trim())){
                    try {
                        newRealEstate.setNumberOfRooms(Integer.parseInt(mNumberOfRoomsEditText.getText().toString()));
                    } catch (NumberFormatException e){
                        mNumberOfRoomsEditText.setError("Invalid");
                        return;
                    }
                }
                newRealEstate.setPointsOfInterest(mPointsOfInterestEditText.getText().toString());
                newRealEstate.setDateOfEntry(mDateOfEntryEditText.getText().toString());
                newRealEstate.setDateOfSale(mDateOfSaleEditText.getText().toString());
                newRealEstate.setAgent(mAgentEditText.getText().toString());
                if (!TextUtils.isEmpty(mPriceEditText.getText().toString())){
                    try {
                        newRealEstate.setPrice(Double.parseDouble(mPriceEditText.getText().toString()));
                    } catch (NumberFormatException e){
                        mPriceEditText.setError("Invalid");
                    }
                }
                if (!TextUtils.isEmpty(mDateOfSaleEditText.getText().toString())){
                    newRealEstate.setStatut(true);
                } else {
                    newRealEstate.setStatut(false);
                }
                newRealEstate.setPhoto("TODO");

                mAddFragmentViewModel.insertRealEstate(newRealEstate);
                getFragmentManager().popBackStack();
            }
        });
    }
}