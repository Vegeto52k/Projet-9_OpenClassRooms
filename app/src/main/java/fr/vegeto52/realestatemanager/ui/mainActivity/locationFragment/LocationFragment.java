package fr.vegeto52.realestatemanager.ui.mainActivity.locationFragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import fr.vegeto52.realestatemanager.utils.LiveDataObserver;
import fr.vegeto52.realestatemanager.R;
import fr.vegeto52.realestatemanager.database.repository.ViewModelFactory;
import fr.vegeto52.realestatemanager.databinding.FragmentLocationBinding;
import fr.vegeto52.realestatemanager.model.RealEstate;
import fr.vegeto52.realestatemanager.ui.mainActivity.detailsFragment.DetailsFragment;
import fr.vegeto52.realestatemanager.ui.mainActivity.listViewFragment.ListViewFragment;
import fr.vegeto52.realestatemanager.ui.mainActivity.simulatorFragment.SimulatorFragment;

/**
 * The LocationFragment class is a fragment that displays a map with real estate locations.
 * It implements OnMapReadyCallback to handle map initialization.
 */
public class LocationFragment extends Fragment implements OnMapReadyCallback {

    // View binding for the fragment
    FragmentLocationBinding mBinding;

    // MapView for displaying the map
    private MapView mMapView;

    // BottomNavigationView for navigation
    private BottomNavigationView mBottomNavigationView;

    // GoogleMap object for interacting with the map
    private GoogleMap mGoogleMap;

    // User's current location
    private Location mLocation;

    // LatLng object representing the user's location
    private LatLng mUserLocation;

    // List of real estate items
    private List<RealEstate> mRealEstateList;

    // Flag to check if the device is a tablet
    private boolean mIsTablet;

    /**
     * Default constructor for the LocationFragment class.
     * Required empty public constructor.
     */
    public LocationFragment() {
        // Required empty public constructor
    }

    /**
     * Called when the fragment is creating. Initialize necessary components.
     *
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * Called to save the current state of the fragment.
     *
     * @param outState Bundle in which to place the saved state.
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * Called to create and return the view hierarchy associated with the fragment.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate views.
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return The View for the fragment's UI.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentLocationBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();

        // Initialize UI elements
        mMapView = mBinding.locationFragmentMapview;
        mBottomNavigationView = mBinding.bottomNavigationFragmentLocation;

        // Initialize the MapView and set the callback for map readiness
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        // Check if the device is a tablet
        int smallestScreenWidthDp = getResources().getConfiguration().smallestScreenWidthDp;
        mIsTablet = smallestScreenWidthDp >= 600;

        // Initialize bottom navigation view
        initBottomNavigationView();

        return view;
    }

    /**
     * Called after the view has been created. Initialize the ViewModel for location data.
     *
     * @param view               The View returned by onCreateView.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * Initialize the ViewModel to observe location data changes.
     */
    @SuppressLint("MissingPermission")
    private void initViewModel() {
        // Create a ViewModel instance for location data
        LocationViewModel locationViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(requireContext())).get(LocationViewModel.class);

        // Observe the location data using LiveData
        LiveDataObserver.observeOnce(locationViewModel.getLocationViewState(), locationViewState -> {

            // Update user's location and real estate list
            mLocation = locationViewState.getLocation();
            double userLatitude = mLocation.getLatitude();
            double userLongitude = mLocation.getLongitude();
            mUserLocation = new LatLng(userLatitude, userLongitude);
            mRealEstateList = locationViewState.getRealEstateList();

            // Move camera to the user's location and enable location on the map
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(mUserLocation, 17);
            mGoogleMap.animateCamera(cameraUpdate);
            mGoogleMap.setMyLocationEnabled(true);

            // Initialize markers for real estate locations
            initMarkersRealEstate();
        });
    }

    /**
     * Initialize the bottom navigation view.
     */
    private void initBottomNavigationView() {
        // Set the selected item in the bottom navigation view
        mBottomNavigationView.setSelectedItemId(R.id.menu_bottom_navigation_location);

        // Set the item selection listener for bottom navigation view
        mBottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment;
            int id = item.getItemId();

            // Handle different bottom navigation items
            if (id == R.id.menu_bottom_navigation_list) {
                // Replace the current fragment with ListViewFragment
                String fragmentTag = "LISTVIEW_FRAGMENT";
                ListViewFragment listViewFragment = (ListViewFragment) requireActivity().getSupportFragmentManager().findFragmentByTag(fragmentTag);
                if (listViewFragment == null) {
                    fragment = new ListViewFragment();
                    if (getActivity() instanceof AppCompatActivity) {
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_main_activity, fragment, fragmentTag)
                                .addToBackStack(fragmentTag)
                                .commit();
                    }
                } else {
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_main_activity, listViewFragment, fragmentTag)
                            .addToBackStack(fragmentTag)
                            .commit();
                }
            } else if (id == R.id.menu_bottom_navigation_simulator) {
                // Replace the current fragment with SimulatorFragment
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
     * Initialize markers for real estate locations on the map.
     */
    private void initMarkersRealEstate() {
        for (RealEstate realEstate : mRealEstateList) {
            if (realEstate.getLongitude() != null && realEstate.getLatitude() != null) {
                double latitude = realEstate.getLatitude();
                double longitude = realEstate.getLongitude();
                LatLng locationRealEstate = new LatLng(latitude, longitude);

                // Add markers with different colors based on real estate status
                if (realEstate.isStatut()) {
                    MarkerOptions markerOptions = new MarkerOptions().position(locationRealEstate).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    Marker marker = mGoogleMap.addMarker(markerOptions);
                    assert marker != null;
                    marker.setTag(realEstate.getId());
                } else {
                    MarkerOptions markerOptions = new MarkerOptions().position(locationRealEstate).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    Marker marker = mGoogleMap.addMarker(markerOptions);
                    assert marker != null;
                    marker.setTag(realEstate.getId());
                }
                // Set a marker click listener
                setMarkerClickListener();
            }
        }
    }

    /**
     * Set a marker click listener to open the DetailsFragment when a marker is clicked.
     */
    private void setMarkerClickListener() {
        mGoogleMap.setOnMarkerClickListener(marker -> {
            if (marker.getTag() != null) {
                Fragment fragment = new DetailsFragment();
                Bundle args = new Bundle();
                args.putLong("idRealEstate", (Long) marker.getTag());
                fragment.setArguments(args);

                // Replace the current fragment with DetailsFragment
                if (mIsTablet) {
                    if (getActivity() instanceof AppCompatActivity) {
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frament_main_activity_2, fragment)
                                .addToBackStack(null)
                                .commit();
                    }
                } else {
                    if (getActivity() instanceof AppCompatActivity) {
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_main_activity, fragment)
                                .addToBackStack(null)
                                .commit();
                    }
                }
                return true;
            }
            return false;
        });
    }

    /**
     * Called when the map is ready to be used. Initialize the ViewModel if location permissions are granted.
     *
     * @param googleMap A non-null instance of GoogleMap associated with the MapView.
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mGoogleMap = googleMap;

        // Check if location permissions are granted
        if (areLocationPermissionsGranted()) {
            // Initialize ViewModel and set marker click listener
            initViewModel();
            setMarkerClickListener();
        } else {
            // Display a message if location permissions are not granted
            Toast.makeText(getContext(), "Location permission required", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Check if location permissions are granted.
     *
     * @return True if location permissions are granted, false otherwise.
     */
    private boolean areLocationPermissionsGranted() {
        return ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Called when the fragment is resumed. Resume the MapView.
     */
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    /**
     * Called when the fragment is paused. Pause the MapView.
     */
    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    /**
     * Called when the fragment is destroyed. Destroy the MapView if it exists.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMapView != null) {
            mMapView.onDestroy();
        }
    }

    /**
     * Called when the available memory is low. Notify the MapView.
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}