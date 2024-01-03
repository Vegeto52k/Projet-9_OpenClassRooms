package fr.vegeto52.realestatemanager.ui.mainActivity.listViewFragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.vegeto52.realestatemanager.R;
import fr.vegeto52.realestatemanager.database.repository.ViewModelFactory;
import fr.vegeto52.realestatemanager.databinding.FragmentListViewBinding;
import fr.vegeto52.realestatemanager.model.Photo;
import fr.vegeto52.realestatemanager.model.RealEstate;
import fr.vegeto52.realestatemanager.ui.mainActivity.locationFragment.LocationFragment;
import fr.vegeto52.realestatemanager.ui.mainActivity.addFragment.AddFragment;
import fr.vegeto52.realestatemanager.ui.mainActivity.simulatorFragment.SimulatorFragment;

/**
 * Created by Vegeto52-PC on 15/11/2023.
 */
public class ListViewFragment extends Fragment {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private FragmentListViewBinding mBinding;
    private View mView;
    private RecyclerView mRecyclerView;
    ListViewRealEstateAdapter mListViewRealEstateAdapter;
    private TextView mListViewEmpty;
    private ListViewViewModel mListViewViewModel;
    private List<RealEstate> mRealEstateList = new ArrayList<>();
    private List<RealEstate> mRealEstateListStatic;
    private List<Photo> mPhotoList;
    private Toolbar mToolbar;
    private ImageButton mAddButton, mFilterButton;
    private BottomNavigationView mBottomNavigationView;
    private ScrollView mFilterScrollView;
    private EditText mFilterPriceMinimum, mFilterPriceMaximum, mFilterSurfaceMinimum, mFilterSurfaceMaximum, mFilterNumberOfRoomsMinimum, mFilterNumberOfRoomsMaximum, mFilterPhotosMinimum;
    private RadioGroup mFilterRadioGroup;
    private RadioButton mFilterRadioButtonAvailable, mFilterRadioButtonBoth, mFilterRadioButtonUnavailable, mFilterRadioButton;
    private Button mFilterCancelButton, mFilterValidateButton, mFilterResetButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (savedInstanceState != null){
            mRealEstateList = savedInstanceState.getParcelableArrayList("realEstateList");
            mRecyclerView.setAdapter(new ListViewRealEstateAdapter(mRealEstateList, mPhotoList));
            mRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("realEstateList", (ArrayList<? extends Parcelable>) mRealEstateList);
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null){
            mRealEstateList = savedInstanceState.getParcelableArrayList("realEstateList");
            mRecyclerView.setAdapter(new ListViewRealEstateAdapter(mRealEstateList, mPhotoList));
            mRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentListViewBinding.inflate(inflater, container, false);
        mView = mBinding.getRoot();

        mRecyclerView = mView.findViewById(R.id.recyclerview_fragment_list_view);
        mToolbar = mView.findViewById(R.id.list_view_fragment_toolbar);
        mAddButton = mView.findViewById(R.id.list_view_fragment_add_button);
        mFilterButton = mView.findViewById(R.id.list_view_fragment_filter_button);
        mBottomNavigationView = mView.findViewById(R.id.bottom_navigation_fragment_list);

        mFilterScrollView = mView.findViewById(R.id.layout_filter_fragment_list);
        mFilterPriceMinimum = mView.findViewById(R.id.filter_price_minimum_fragment_list);
        mFilterPriceMaximum = mView.findViewById(R.id.filter_price_maximum_fragment_list);
        mFilterSurfaceMinimum = mView.findViewById(R.id.filter_surface_minimum_fragment_list);
        mFilterSurfaceMaximum = mView.findViewById(R.id.filter_surface_maximum_fragment_list);
        mFilterNumberOfRoomsMinimum = mView.findViewById(R.id.filter_number_of_rooms_minimum_fragment_list);
        mFilterNumberOfRoomsMaximum = mView.findViewById(R.id.filter_number_of_rooms_maximum_fragment_list);
        mFilterPhotosMinimum = mView.findViewById(R.id.filter_photos_minimum_edit_fragment_list);
        mFilterRadioGroup = mView.findViewById(R.id.filter_radiogroup_fragment_list);
        mFilterRadioButtonAvailable = mView.findViewById(R.id.filter_radiobutton_available_fragment_list);
        mFilterRadioButtonBoth = mView.findViewById(R.id.filter_radiobutton_both_fragment_list);
        mFilterRadioButtonUnavailable = mView.findViewById(R.id.filter_radiobutton_unavailable_fragment_list);
        mFilterCancelButton = mView.findViewById(R.id.filter_cancel_button_fragment_list);
        mFilterValidateButton = mView.findViewById(R.id.filter_validate_button_fragment_list);
        mFilterResetButton = mView.findViewById(R.id.filter_reset_button_fragment_list);

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
    }

    private void initViewModel(){
        mListViewViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(requireContext())).get(ListViewViewModel.class);
        mListViewViewModel.getListViewMutableLiveData().observe(getViewLifecycleOwner(), new Observer<ListViewViewState>() {
            @Override
            public void onChanged(ListViewViewState listViewViewState) {
                mRealEstateListStatic = new ArrayList<>(listViewViewState.getRealEstateList());
                mPhotoList = listViewViewState.getPhotoList();
                initRecyclerView();
                initToolbar();
                initBottomNavigationView();
                mBinding.fragmentListViewEmpty.setVisibility(mRealEstateListStatic.isEmpty() ? View.VISIBLE : View.GONE);
            }
        });
    }

    private void initToolbar(){
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new AddFragment();
                if (view.getContext() instanceof AppCompatActivity){
                    ((AppCompatActivity) view.getContext()).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_main_activity, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        mFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initFilterView();
            }
        });
    }

    private void initRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        mListViewRealEstateAdapter = new ListViewRealEstateAdapter(mRealEstateListStatic, mPhotoList);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(), layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mListViewRealEstateAdapter);
    }

    private void initBottomNavigationView(){
        mBottomNavigationView.setSelectedItemId(R.id.menu_bottom_navigation_list);
        mBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                int id = item.getItemId();
                if (id == R.id.menu_bottom_navigation_location){
                        fragment = new LocationFragment();
                        if (getActivity() instanceof AppCompatActivity){
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_main_activity, fragment)
                                    .addToBackStack(null)
                                    .commit();
                        }
                        return true;
                } else if (id == R.id.menu_bottom_navigation_simulator) {
                    fragment = new SimulatorFragment();
                    if (getActivity() instanceof AppCompatActivity){
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_main_activity, fragment)
                                .addToBackStack(null)
                                .commit();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void initFilterView(){
        mFilterScrollView.setVisibility(View.VISIBLE);
        mFilterRadioGroup.check(R.id.filter_radiobutton_both_fragment_list);

        mFilterCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initFilterCancelButton();
            }
        });

        mFilterValidateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initFilterValidateButton();
            }
        });

        mFilterResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initFilterResetButton();
            }
        });
    }

    private void initFilterCancelButton(){
        mFilterScrollView.setVisibility(View.INVISIBLE);
    }

    private void initFilterValidateButton(){
        mFilterScrollView.setVisibility(View.INVISIBLE);
        mRealEstateList = new ArrayList<>(mRealEstateListStatic);
        Iterator<RealEstate> iterator = mRealEstateList.iterator();
        while (iterator.hasNext()){
            RealEstate realEstate = iterator.next();
            if (!mFilterPriceMinimum.getText().toString().isEmpty() && realEstate.getPrice() == null){
                iterator.remove();
                continue;
            } else if (!mFilterPriceMinimum.getText().toString().isEmpty() && realEstate.getPrice() != null){
                Double filterPriceMinimum = Double.parseDouble(mFilterPriceMinimum.getText().toString());
                if (filterPriceMinimum.compareTo(realEstate.getPrice()) > 0){
                    iterator.remove();
                    continue;
                }
            }
            if (!mFilterPriceMaximum.getText().toString().isEmpty() && realEstate.getPrice() == null){
                iterator.remove();
                continue;
            } else if (!mFilterPriceMaximum.getText().toString().isEmpty() && realEstate.getPrice() != null) {
                Double filterPriceMaximum = Double.parseDouble(mFilterPriceMaximum.getText().toString());
                if (filterPriceMaximum.compareTo(realEstate.getPrice()) < 0){
                    iterator.remove();
                    continue;
                }
            }
            if (!mFilterSurfaceMinimum.getText().toString().isEmpty() && realEstate.getSurface() == null){
                iterator.remove();
                continue;
            } else if (!mFilterSurfaceMinimum.getText().toString().isEmpty() && realEstate.getSurface() != null) {
                Double filterSurfaceMinimum = Double.parseDouble(mFilterSurfaceMinimum.getText().toString());
                if (filterSurfaceMinimum.compareTo(realEstate.getSurface()) > 0){
                    iterator.remove();
                    continue;
                }
            }
            if (!mFilterSurfaceMaximum.getText().toString().isEmpty() && realEstate.getSurface() == null){
                iterator.remove();
                continue;
            } else if (!mFilterSurfaceMaximum.getText().toString().isEmpty() && realEstate.getSurface() != null) {
                Double filterSurfaceMaximum = Double.parseDouble(mFilterSurfaceMaximum.getText().toString());
                if (filterSurfaceMaximum.compareTo(realEstate.getSurface()) < 0){
                    iterator.remove();
                    continue;
                }
            }
            if (!mFilterNumberOfRoomsMinimum.getText().toString().isEmpty() && realEstate.getNumberOfRooms() == null){
                iterator.remove();
                continue;
            } else if (!mFilterNumberOfRoomsMinimum.getText().toString().isEmpty() && realEstate.getNumberOfRooms() != null) {
                Integer filterNumberOfRoomsMinimum = Integer.parseInt(mFilterNumberOfRoomsMinimum.getText().toString());
                if (filterNumberOfRoomsMinimum.compareTo(realEstate.getNumberOfRooms()) > 0){
                    iterator.remove();
                    continue;
                }
            }
            if (!mFilterNumberOfRoomsMaximum.getText().toString().isEmpty() && realEstate.getNumberOfRooms() == null){
                iterator.remove();

            } else if (!mFilterNumberOfRoomsMaximum.getText().toString().isEmpty() && realEstate.getNumberOfRooms() != null) {
                Integer filterNumberOfRoomsMaximum = Integer.parseInt(mFilterNumberOfRoomsMaximum.getText().toString());
                if (filterNumberOfRoomsMaximum.compareTo(realEstate.getNumberOfRooms()) < 0){
                    iterator.remove();

                }
            }
            if (!mFilterPhotosMinimum.getText().toString().isEmpty()){
                int numberPhoto = 0;
                for (Photo photo : mPhotoList){
                    if (photo.getRealEstateId() == realEstate.getId()){
                        numberPhoto++;
                    }
                }
                if (numberPhoto < Integer.parseInt(mFilterPhotosMinimum.getText().toString())){
                    iterator.remove();
                    continue;
                }
            }
            int selectedRadioButtonId = mFilterRadioGroup.getCheckedRadioButtonId();
            if (selectedRadioButtonId != -1){
                mFilterRadioButton = mView.findViewById(selectedRadioButtonId);
                if (mFilterRadioButton.getText().toString().equals("Available")){
                    if (realEstate.isStatut()){
                        iterator.remove();
                        continue;
                    }
                } else if (mFilterRadioButton.getText().toString().equals("Both")) {
                    continue;
                } else if (mFilterRadioButton.getText().toString().equals("Unavailable")) {
                    if (!realEstate.isStatut()){
                        iterator.remove();
                        continue;
                    }
                }
            }
        }
        mRecyclerView.setAdapter(new ListViewRealEstateAdapter(mRealEstateList, mPhotoList));
        mBinding.fragmentListViewEmpty.setVisibility(mRealEstateList.isEmpty() ? View.VISIBLE : View.GONE);
    }

    private void initFilterResetButton(){
        mFilterScrollView.setVisibility(View.INVISIBLE);
        mFilterPriceMinimum.setText("");
        mFilterPriceMaximum.setText("");
        mFilterSurfaceMinimum.setText("");
        mFilterSurfaceMaximum.setText("");
        mFilterNumberOfRoomsMinimum.setText("");
        mFilterNumberOfRoomsMaximum.setText("");
        mFilterPhotosMinimum.setText("");
        mFilterRadioGroup.check(R.id.filter_radiobutton_both_fragment_list);
        mRecyclerView.setAdapter(new ListViewRealEstateAdapter(mRealEstateListStatic, mPhotoList));
        mBinding.fragmentListViewEmpty.setVisibility(mRealEstateListStatic.isEmpty() ? View.VISIBLE : View.GONE);
    }
}
