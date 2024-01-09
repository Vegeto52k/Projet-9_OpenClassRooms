package fr.vegeto52.realestatemanager.ui.mainActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import fr.vegeto52.realestatemanager.R;
import fr.vegeto52.realestatemanager.databinding.ActivityMainBinding;
import fr.vegeto52.realestatemanager.ui.mainActivity.detailsFragment.DetailsFragment;
import fr.vegeto52.realestatemanager.ui.mainActivity.listViewFragment.ListViewFragment;

public class MainActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mIsTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fr.vegeto52.realestatemanager.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        int smallestScreenWidthDp = getResources().getConfiguration().smallestScreenWidthDp;
        mIsTablet = smallestScreenWidthDp >= 600;

        enableMyLocation();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void enableMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
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

    private void initUI() {
        String fragmentTag = "LISTVIEW_FRAGMENT";
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_main_activity) == null) {
            Fragment fragment = new ListViewFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_main_activity, fragment, fragmentTag)
                    .addToBackStack(fragmentTag)
                    .commit();
            if (mIsTablet) {
                String fragmentTag2 = "DETAILS_FRAGMENT";
                if (getSupportFragmentManager().findFragmentByTag(fragmentTag2) == null) {
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