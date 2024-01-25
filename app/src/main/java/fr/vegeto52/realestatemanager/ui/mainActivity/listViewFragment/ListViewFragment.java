package fr.vegeto52.realestatemanager.ui.mainActivity.listViewFragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.vegeto52.realestatemanager.R;
import fr.vegeto52.realestatemanager.database.repository.ViewModelFactory;
import fr.vegeto52.realestatemanager.databinding.FragmentListViewBinding;
import fr.vegeto52.realestatemanager.model.Photo;
import fr.vegeto52.realestatemanager.model.RealEstate;
import fr.vegeto52.realestatemanager.ui.mainActivity.addFragment.AddFragment;
import fr.vegeto52.realestatemanager.ui.mainActivity.locationFragment.LocationFragment;
import fr.vegeto52.realestatemanager.ui.mainActivity.simulatorFragment.SimulatorFragment;

/**
 * The ListViewFragment class extends Fragment and represents the fragment displaying a list of real estate properties.
 * It includes functionality for filtering and adding properties to the list.
 */
public class ListViewFragment extends Fragment {


    private FragmentListViewBinding mBinding;
    private View mView;
    private RecyclerView mRecyclerView;
    ListViewRealEstateAdapter mListViewRealEstateAdapter;
    private List<RealEstate> mRealEstateList = new ArrayList<>();
    private List<RealEstate> mRealEstateListStatic;
    private List<Photo> mPhotoList;

    // UI elements
    private ImageButton mAddButton, mFilterButton;
    private BottomNavigationView mBottomNavigationView;
    private ScrollView mFilterScrollView;
    private EditText mFilterPriceMinimum, mFilterPriceMaximum, mFilterSurfaceMinimum, mFilterSurfaceMaximum, mFilterNumberOfRoomsMinimum, mFilterNumberOfRoomsMaximum, mFilterPhotosMinimum;
    private RadioGroup mFilterRadioGroup;
    private RadioButton mFilterRadioButtonAvailable;
    private RadioButton mFilterRadioButtonUnavailable;
    private Button mFilterCancelButton, mFilterValidateButton, mFilterResetButton;

    // Flag indicating whether the device is a tablet
    private boolean mIsTablet;

    /**
     * Called when the fragment is created. Restores saved instance state if available.
     *
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mRealEstateList = savedInstanceState.getParcelableArrayList("RealEstateList");
        }
    }

    /**
     * Called to save the current dynamic state of the fragment into the given Bundle.
     *
     * @param outState Bundle in which to place the saved state.
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("RealEstateList", (ArrayList<? extends Parcelable>) mRealEstateList);
        if (mFilterPriceMinimum != null && mFilterPriceMaximum != null && mFilterSurfaceMinimum != null && mFilterSurfaceMaximum != null && mFilterNumberOfRoomsMinimum != null && mFilterNumberOfRoomsMaximum != null && mFilterPhotosMinimum != null) {
            outState.putString("FilterPriceMinimum", mFilterPriceMinimum.getText().toString());
            outState.putString("FilterPriceMaximum", mFilterPriceMaximum.getText().toString());
            outState.putString("FilterSurfaceMinimum", mFilterSurfaceMinimum.getText().toString());
            outState.putString("FilterSurfaceMaximum", mFilterSurfaceMaximum.getText().toString());
            outState.putString("FilterNumberOfRoomsMinimum", mFilterNumberOfRoomsMinimum.getText().toString());
            outState.putString("FilterNumberOfRoomsMaximum", mFilterNumberOfRoomsMaximum.getText().toString());
            outState.putString("FilterPhotosMinimum", mFilterPhotosMinimum.getText().toString());
        }
    }

    /**
     * Called to restore the saved instance state.
     *
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     */
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            mFilterPriceMinimum.setText(savedInstanceState.getString("FilterPriceMinimum"));
            mFilterPriceMaximum.setText(savedInstanceState.getString("FilterPriceMaximum"));
            mFilterSurfaceMinimum.setText(savedInstanceState.getString("FilterSurfaceMinimum"));
            mFilterSurfaceMaximum.setText(savedInstanceState.getString("FilterSurfaceMaximum"));
            mFilterNumberOfRoomsMinimum.setText(savedInstanceState.getString("FilterNumberOfRoomsMinimum"));
            mFilterNumberOfRoomsMaximum.setText(savedInstanceState.getString("FilterNumberOfRoomsMaximum"));
            mFilterPhotosMinimum.setText(savedInstanceState.getString("FilterPhotosMinimum"));
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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentListViewBinding.inflate(inflater, container, false);
        mView = mBinding.getRoot();

        // Initialize UI elements
        mRecyclerView = mBinding.recyclerviewFragmentListView;
        mAddButton = mBinding.listViewFragmentAddButton;
        mFilterButton = mBinding.listViewFragmentFilterButton;
        mBottomNavigationView = mBinding.bottomNavigationFragmentList;

        mFilterScrollView = mBinding.layoutFilterFragmentList;
        mFilterPriceMinimum = mBinding.filterPriceMinimumFragmentList;
        mFilterPriceMaximum = mBinding.filterPriceMaximumFragmentList;
        mFilterSurfaceMinimum = mBinding.filterSurfaceMinimumFragmentList;
        mFilterSurfaceMaximum = mBinding.filterSurfaceMaximumFragmentList;
        mFilterNumberOfRoomsMinimum = mBinding.filterNumberOfRoomsMinimumFragmentList;
        mFilterNumberOfRoomsMaximum = mBinding.filterNumberOfRoomsMaximumFragmentList;
        mFilterPhotosMinimum = mBinding.filterPhotosMinimumEditFragmentList;
        mFilterRadioGroup = mBinding.filterRadiogroupFragmentList;
        mFilterRadioButtonAvailable = mBinding.filterRadiobuttonAvailableFragmentList;
        mFilterRadioButtonUnavailable = mBinding.filterRadiobuttonUnavailableFragmentList;
        mFilterCancelButton = mBinding.filterCancelButtonFragmentList;
        mFilterValidateButton = mBinding.filterValidateButtonFragmentList;
        mFilterResetButton = mBinding.filterResetButtonFragmentList;

        // Check if the device is a tablet
        int smallestScreenWidthDp = getResources().getConfiguration().smallestScreenWidthDp;
        mIsTablet = smallestScreenWidthDp >= 600;

        return mView;
    }

    /**
     * Called when the fragment's activity has been created and this fragment's view hierarchy instantiated.
     *
     * @param view               The created view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
    }

    /**
     * Initializes the ViewModel for the ListViewFragment.
     */
    private void initViewModel() {
        ListViewViewModel listViewViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(requireContext())).get(ListViewViewModel.class);
        listViewViewModel.getListViewMutableLiveData().observe(getViewLifecycleOwner(), listViewViewState -> {
            mRealEstateListStatic = new ArrayList<>(listViewViewState.getRealEstateList());
            mPhotoList = listViewViewState.getPhotoList();
            initRecyclerView();
            initToolbar();
            initBottomNavigationView();
            mBinding.fragmentListViewEmpty.setVisibility(mRealEstateListStatic.isEmpty() ? View.VISIBLE : View.GONE);
        });
    }

    /**
     * Initializes the toolbar, including the "Add" and "Filter" buttons.
     */
    private void initToolbar() {
        mAddButton.setOnClickListener(view -> {
            Fragment fragment = new AddFragment();
            if (view.getContext() instanceof AppCompatActivity) {
                ((AppCompatActivity) view.getContext()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_main_activity, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        mFilterButton.setOnClickListener(view -> initFilterView());
    }

    /**
     * Initializes the RecyclerView for displaying the list of real estate properties.
     */
    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        if (mRealEstateList.isEmpty()) {
            mListViewRealEstateAdapter = new ListViewRealEstateAdapter(mRealEstateListStatic, mPhotoList, mIsTablet);
        } else {
            mListViewRealEstateAdapter = new ListViewRealEstateAdapter(mRealEstateList, mPhotoList, mIsTablet);

        }
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(), layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mListViewRealEstateAdapter);
    }

    /**
     * Initializes the BottomNavigationView for navigating between different fragments.
     */
    private void initBottomNavigationView() {
        mBottomNavigationView.setSelectedItemId(R.id.menu_bottom_navigation_list);
        mBottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment;
            int id = item.getItemId();

            if (id == R.id.menu_bottom_navigation_location) {
                // Handling navigation to the LocationFragment
                String fragmentTag = "LOCATION_FRAGMENT";
                LocationFragment locationFragment = (LocationFragment) requireActivity().getSupportFragmentManager().findFragmentByTag(fragmentTag);
                if (locationFragment == null) {
                    fragment = new LocationFragment();
                    if (getActivity() instanceof AppCompatActivity) {
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_main_activity, fragment, fragmentTag)
                                .addToBackStack(fragmentTag)
                                .commit();
                    }
                } else {
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_main_activity, locationFragment, fragmentTag)
                            .addToBackStack(fragmentTag)
                            .commit();
                }
            } else if (id == R.id.menu_bottom_navigation_simulator) {
                // Handling navigation to the SimulatorFragment
                String fragmentTag = "SIMULATOR_FRAGMENT";
                SimulatorFragment simulatorFragment = (SimulatorFragment) requireActivity().getSupportFragmentManager().findFragmentByTag(fragmentTag);
                if (simulatorFragment == null) {
                    fragment = new SimulatorFragment();
                    if (getActivity() instanceof AppCompatActivity) {
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_main_activity, fragment, fragmentTag)
                                .addToBackStack(fragmentTag)
                                .commit();
                    }
                } else {
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_main_activity, simulatorFragment, fragmentTag)
                            .addToBackStack(fragmentTag)
                            .commit();
                }
            }
            return false;
        });
    }

    /**
     * Initializes the filter view, making it visible and setting up listeners for cancel, validate, and reset buttons.
     */
    private void initFilterView() {
        mFilterScrollView.setVisibility(View.VISIBLE);
        if (mFilterRadioButtonAvailable.isChecked()) {
            mFilterRadioGroup.check(R.id.filter_radiobutton_available_fragment_list);
        } else if (mFilterRadioButtonUnavailable.isChecked()) {
            mFilterRadioGroup.check(R.id.filter_radiobutton_unavailable_fragment_list);
        } else {
            mFilterRadioGroup.check(R.id.filter_radiobutton_both_fragment_list);
        }

        mFilterCancelButton.setOnClickListener(view -> initFilterCancelButton());

        mFilterValidateButton.setOnClickListener(view -> initFilterValidateButton());

        mFilterResetButton.setOnClickListener(view -> initFilterResetButton());
    }

    /**
     * Handles canceling the filter, making the filter view invisible.
     */
    private void initFilterCancelButton() {
        mFilterScrollView.setVisibility(View.INVISIBLE);
    }

    /**
     * Handles validating the filter criteria and updating the RecyclerView accordingly.
     */
    private void initFilterValidateButton() {
        // Code for validating filter criteria and updating RecyclerView
        mFilterScrollView.setVisibility(View.INVISIBLE);
        mRealEstateList = new ArrayList<>(mRealEstateListStatic);
        Iterator<RealEstate> iterator = mRealEstateList.iterator();
        while (iterator.hasNext()) {
            RealEstate realEstate = iterator.next();
            if (!mFilterPriceMinimum.getText().toString().isEmpty() && realEstate.getPrice() == null) {
                iterator.remove();
                continue;
            } else if (!mFilterPriceMinimum.getText().toString().isEmpty() && realEstate.getPrice() != null) {
                Double filterPriceMinimum = Double.parseDouble(mFilterPriceMinimum.getText().toString());
                if (filterPriceMinimum.compareTo(realEstate.getPrice()) > 0) {
                    iterator.remove();
                    continue;
                }
            }
            if (!mFilterPriceMaximum.getText().toString().isEmpty() && realEstate.getPrice() == null) {
                iterator.remove();
                continue;
            } else if (!mFilterPriceMaximum.getText().toString().isEmpty() && realEstate.getPrice() != null) {
                Double filterPriceMaximum = Double.parseDouble(mFilterPriceMaximum.getText().toString());
                if (filterPriceMaximum.compareTo(realEstate.getPrice()) < 0) {
                    iterator.remove();
                    continue;
                }
            }
            if (!mFilterSurfaceMinimum.getText().toString().isEmpty() && realEstate.getSurface() == null) {
                iterator.remove();
                continue;
            } else if (!mFilterSurfaceMinimum.getText().toString().isEmpty() && realEstate.getSurface() != null) {
                Double filterSurfaceMinimum = Double.parseDouble(mFilterSurfaceMinimum.getText().toString());
                if (filterSurfaceMinimum.compareTo(realEstate.getSurface()) > 0) {
                    iterator.remove();
                    continue;
                }
            }
            if (!mFilterSurfaceMaximum.getText().toString().isEmpty() && realEstate.getSurface() == null) {
                iterator.remove();
                continue;
            } else if (!mFilterSurfaceMaximum.getText().toString().isEmpty() && realEstate.getSurface() != null) {
                Double filterSurfaceMaximum = Double.parseDouble(mFilterSurfaceMaximum.getText().toString());
                if (filterSurfaceMaximum.compareTo(realEstate.getSurface()) < 0) {
                    iterator.remove();
                    continue;
                }
            }
            if (!mFilterNumberOfRoomsMinimum.getText().toString().isEmpty() && realEstate.getNumberOfRooms() == null) {
                iterator.remove();
                continue;
            } else if (!mFilterNumberOfRoomsMinimum.getText().toString().isEmpty() && realEstate.getNumberOfRooms() != null) {
                Integer filterNumberOfRoomsMinimum = Integer.parseInt(mFilterNumberOfRoomsMinimum.getText().toString());
                if (filterNumberOfRoomsMinimum.compareTo(realEstate.getNumberOfRooms()) > 0) {
                    iterator.remove();
                    continue;
                }
            }
            if (!mFilterNumberOfRoomsMaximum.getText().toString().isEmpty() && realEstate.getNumberOfRooms() == null) {
                iterator.remove();

            } else if (!mFilterNumberOfRoomsMaximum.getText().toString().isEmpty() && realEstate.getNumberOfRooms() != null) {
                Integer filterNumberOfRoomsMaximum = Integer.parseInt(mFilterNumberOfRoomsMaximum.getText().toString());
                if (filterNumberOfRoomsMaximum.compareTo(realEstate.getNumberOfRooms()) < 0) {
                    iterator.remove();

                }
            }
            if (!mFilterPhotosMinimum.getText().toString().isEmpty()) {
                int numberPhoto = 0;
                for (Photo photo : mPhotoList) {
                    if (photo.getRealEstateId() == realEstate.getId()) {
                        numberPhoto++;
                    }
                }
                if (numberPhoto < Integer.parseInt(mFilterPhotosMinimum.getText().toString())) {
                    iterator.remove();
                    continue;
                }
            }
            int selectedRadioButtonId = mFilterRadioGroup.getCheckedRadioButtonId();
            if (selectedRadioButtonId != -1) {
                RadioButton filterRadioButton = mView.findViewById(selectedRadioButtonId);
                if (filterRadioButton.getText().toString().equals("Available")) {
                    if (realEstate.isStatut()) {
                        iterator.remove();
                    }
                } else if (filterRadioButton.getText().toString().equals("Unavailable")) {
                    if (!realEstate.isStatut()) {
                        iterator.remove();
                    }
                }
            }
        }
        mRecyclerView.setAdapter(new ListViewRealEstateAdapter(mRealEstateList, mPhotoList, mIsTablet));
        mBinding.fragmentListViewEmpty.setVisibility(mRealEstateList.isEmpty() ? View.VISIBLE : View.GONE);
    }

    /**
     * Handles resetting the filter criteria, making the filter view invisible, and updating the RecyclerView.
     */
    private void initFilterResetButton() {
        // Code for resetting filter criteria and updating RecyclerView
        mFilterScrollView.setVisibility(View.INVISIBLE);
        mFilterPriceMinimum.setText("");
        mFilterPriceMaximum.setText("");
        mFilterSurfaceMinimum.setText("");
        mFilterSurfaceMaximum.setText("");
        mFilterNumberOfRoomsMinimum.setText("");
        mFilterNumberOfRoomsMaximum.setText("");
        mFilterPhotosMinimum.setText("");
        mFilterRadioGroup.check(R.id.filter_radiobutton_both_fragment_list);
        mRecyclerView.setAdapter(new ListViewRealEstateAdapter(mRealEstateListStatic, mPhotoList, mIsTablet));
        mRealEstateList = new ArrayList<>(mRealEstateListStatic);
        mBinding.fragmentListViewEmpty.setVisibility(mRealEstateListStatic.isEmpty() ? View.VISIBLE : View.GONE);
    }
}
