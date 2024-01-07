package fr.vegeto52.realestatemanager.ui.mainActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import fr.vegeto52.realestatemanager.R;
import fr.vegeto52.realestatemanager.databinding.ActivityMainBinding;
import fr.vegeto52.realestatemanager.ui.mainActivity.detailsFragment.DetailsFragment;
import fr.vegeto52.realestatemanager.ui.mainActivity.listViewFragment.ListViewFragment;

public class MainActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private ActivityMainBinding mBinding;
    private FragmentContainerView mFragmentContainerView;
    private FragmentContainerView mFragmentContainerView2;
    private BottomNavigationView mBottomNavigationView;
    private boolean mIsTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        mFragmentContainerView = view.findViewById(R.id.fragment_main_activity);
        mFragmentContainerView2 = view.findViewById(R.id.frament_main_activity_2);

        int smallestScreenWidthDp = getResources().getConfiguration().smallestScreenWidthDp;
        mIsTablet = smallestScreenWidthDp >= 600;
//        mBottomNavigationView = view.findViewById(R.id.bottom_navigation_view_activity);

//        if (getResources().getConfiguration().smallestScreenWidthDp >= 600){
//
//        } else {
//            Fragment fragment = null;
//            fragment = new ListViewFragment();
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.fragment_main_activity, fragment)
//                    .commit();
//        }

        enableMyLocation();
    }



    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void enableMyLocation(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            initUI();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initUI();
            } else {
                initUI();
            }
        }
    }

    private void initUI(){
            String fragmentTag = "LISTVIEW_FRAGMENT";
            if (getSupportFragmentManager().findFragmentById(R.id.fragment_main_activity) == null){
                Fragment fragment = new ListViewFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_main_activity, fragment, fragmentTag)
                        .addToBackStack(fragmentTag)
                        .commit();
                if (mIsTablet){
                    String fragmentTag2 = "DETAILS_FRAGMENT";
                    if (getSupportFragmentManager().findFragmentByTag(fragmentTag2) == null){
                        Fragment fragment2 = new DetailsFragment();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frament_main_activity_2, fragment2, fragmentTag2)
                                .addToBackStack(null)
                                .commit();
                    }
                }
            }
    }
}