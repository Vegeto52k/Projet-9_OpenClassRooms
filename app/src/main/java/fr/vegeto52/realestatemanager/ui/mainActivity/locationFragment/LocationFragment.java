package fr.vegeto52.realestatemanager.ui.mainActivity.locationFragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
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

import fr.vegeto52.realestatemanager.LiveDataObserver;
import fr.vegeto52.realestatemanager.R;
import fr.vegeto52.realestatemanager.database.repository.ViewModelFactory;
import fr.vegeto52.realestatemanager.databinding.FragmentLocationBinding;
import fr.vegeto52.realestatemanager.model.RealEstate;
import fr.vegeto52.realestatemanager.ui.mainActivity.detailsFragment.DetailsFragment;
import fr.vegeto52.realestatemanager.ui.mainActivity.listViewFragment.ListViewFragment;
import fr.vegeto52.realestatemanager.ui.mainActivity.simulatorFragment.SimulatorFragment;


public class LocationFragment extends Fragment implements OnMapReadyCallback {

    FragmentLocationBinding mBinding;
    private MapView mMapView;
    private BottomNavigationView mBottomNavigationView;
    private GoogleMap mGoogleMap;
    private Location mLocation;
    private LatLng mUserLocation;
    private List<RealEstate> mRealEstateList;
    private boolean mIsTablet;


    public LocationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentLocationBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();

        mMapView = view.findViewById(R.id.location_fragment_mapview);
        mBottomNavigationView = view.findViewById(R.id.bottom_navigation_fragment_location);

        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        int smallestScreenWidthDp = getResources().getConfiguration().smallestScreenWidthDp;
        mIsTablet = smallestScreenWidthDp >= 600;

        initToolbar();
        initBottomNavigationView();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @SuppressLint("MissingPermission")
    private void initViewModel() {
        LocationViewModel locationViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(requireContext())).get(LocationViewModel.class);
        LiveDataObserver.observeOnce(locationViewModel.getLocationViewState(), locationViewState -> {
            mLocation = locationViewState.getLocation();
            double userLatitude = mLocation.getLatitude();
            double userLongitude = mLocation.getLongitude();
            mUserLocation = new LatLng(userLatitude, userLongitude);
            mRealEstateList = locationViewState.getRealEstateList();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(mUserLocation, 17);
            mGoogleMap.animateCamera(cameraUpdate);
            mGoogleMap.setMyLocationEnabled(true);
            initMarkersRealEstate();
        });
    }

    private void initToolbar() {

    }

    private void initBottomNavigationView() {
        mBottomNavigationView.setSelectedItemId(R.id.menu_bottom_navigation_location);
        mBottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment;
            int id = item.getItemId();
            if (id == R.id.menu_bottom_navigation_list) {
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

    private void initMarkersRealEstate() {
        for (RealEstate realEstate : mRealEstateList) {
            if (realEstate.getLongitude() != null && realEstate.getLatitude() != null) {
                double latitude = realEstate.getLatitude();
                double longitude = realEstate.getLongitude();
                LatLng locationRealEstate = new LatLng(latitude, longitude);
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
                setMarkerClickListener();
            }
        }
    }

    private void setMarkerClickListener() {
        mGoogleMap.setOnMarkerClickListener(marker -> {
            if (marker.getTag() != null) {
                Fragment fragment = new DetailsFragment();
                Bundle args = new Bundle();
                args.putLong("idRealEstate", (Long) marker.getTag());
                fragment.setArguments(args);
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

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mGoogleMap = googleMap;
        if (areLocationPermissionsGranted()) {
            initViewModel();
            setMarkerClickListener();
        } else {
            Toast.makeText(getContext(), "Location permission required", Toast.LENGTH_LONG).show();
        }
    }

    private boolean areLocationPermissionsGranted() {
        return ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMapView != null) {
            mMapView.onDestroy();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}