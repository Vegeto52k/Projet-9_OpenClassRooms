package fr.vegeto52.realestatemanager.ui.locationFragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import fr.vegeto52.realestatemanager.BuildConfig;
import fr.vegeto52.realestatemanager.LiveDataObserver;
import fr.vegeto52.realestatemanager.R;
import fr.vegeto52.realestatemanager.api.GeocodingApi;
import fr.vegeto52.realestatemanager.api.RetrofitService;
import fr.vegeto52.realestatemanager.database.repository.ViewModelFactory;
import fr.vegeto52.realestatemanager.databinding.FragmentLocationBinding;
import fr.vegeto52.realestatemanager.model.RealEstate;
import fr.vegeto52.realestatemanager.model.ResultsGeocodingApi;
import fr.vegeto52.realestatemanager.ui.mainActivity.detailsFragment.DetailsFragment;
import fr.vegeto52.realestatemanager.ui.mainActivity.listViewFragment.ListViewFragment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LocationFragment extends Fragment  implements OnMapReadyCallback {

    FragmentLocationBinding mBinding;
    private MapView mMapView;
    private Toolbar mToolbar;
    private BottomNavigationView mBottomNavigationView;
    private GoogleMap mGoogleMap;
    private LocationViewModel mLocationViewModel;
    private Location mLocation;
    private LatLng mUserLocation;
    private List<RealEstate> mRealEstateList;


    public LocationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentLocationBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();

        mMapView = view.findViewById(R.id.location_fragment_mapview);
        mToolbar = view.findViewById(R.id.location_fragment_toolbar);
        mBottomNavigationView = view.findViewById(R.id.bottom_navigation_fragment_location);

        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        initToolbar();
        initBottomNavigationView();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @SuppressLint("MissingPermission")
    private void initViewModel(){
        mLocationViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(requireContext())).get(LocationViewModel.class);

        LiveDataObserver.observeOnce(mLocationViewModel.getLocationViewState(), new Observer<LocationViewState>() {
            @Override
            public void onChanged(LocationViewState locationViewState) {
                mLocation = locationViewState.getLocation();
                double userLatitude = mLocation.getLatitude();
                double userLongitude = mLocation.getLongitude();
                mUserLocation = new LatLng(userLatitude, userLongitude);
                mRealEstateList = locationViewState.getRealEstateList();
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(mUserLocation, 17);
                mGoogleMap.animateCamera(cameraUpdate);
                mGoogleMap.setMyLocationEnabled(true);
                initMarkersRealEstate();
            }
        });
    }

    private void initToolbar(){

    }

    private void initBottomNavigationView(){
        mBottomNavigationView.setSelectedItemId(R.id.menu_bottom_navigation_location);
        mBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                int id = item.getItemId();
                if (id == R.id.menu_bottom_navigation_list){
                    fragment = new ListViewFragment();
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
     private void initMarkersRealEstate(){
        for (RealEstate realEstate : mRealEstateList){
            if (realEstate.getLongitude() != null && realEstate.getLatitude() != null){
                double latitude = realEstate.getLatitude();
                double longitude = realEstate.getLongitude();
                LatLng locationRealEstate = new LatLng(latitude, longitude);
                if (realEstate.isStatut()){
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

    private void setMarkerClickListener(){
        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                if (marker.getTag() != null){
                    Fragment fragment = new DetailsFragment();
                    Bundle args = new Bundle();
                    args.putLong("idRealEstate", (Long) marker.getTag());
                    fragment.setArguments(args);

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

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mGoogleMap = googleMap;
        if (areLocationPermissionsGranted()){
            initViewModel();
            setMarkerClickListener();
        } else {
            Toast.makeText(getContext(), "Location permission required", Toast.LENGTH_LONG).show();
        }
    }

    private boolean areLocationPermissionsGranted(){
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
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}