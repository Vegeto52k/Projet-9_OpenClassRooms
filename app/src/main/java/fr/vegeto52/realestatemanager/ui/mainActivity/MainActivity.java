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

/**
 * The MainActivity class extends AppCompatActivity and serves as the main entry point for the application.
 * It initializes the UI and manages location permissions for displaying property listings and details.
 */
public class MainActivity extends AppCompatActivity {

    // Request code for location permission
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    // Flag to determine if the device is a tablet
    private boolean mIsTablet;

    /**
     * Called when the activity is starting. This is where most initialization should go.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           then this Bundle contains the data it most recently supplied in onSaveInstanceState.
     *                           Note: Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize view binding
        fr.vegeto52.realestatemanager.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Determine if the device is a tablet
        int smallestScreenWidthDp = getResources().getConfiguration().smallestScreenWidthDp;
        mIsTablet = smallestScreenWidthDp >= 600;

        // Enable location and initialize UI
        enableMyLocation();
    }

    /**
     * Called to retrieve per-instance state from an activity before being killed so that the state can be restored.
     *
     * @param outState Bundle in which to place your saved state.
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * Enable location permission and initialize UI accordingly.
     */
    private void enableMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            initUI();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
    }

    /**
     * Callback for the result from requesting permissions.
     *
     * @param requestCode  The request code passed in requestPermissions.
     * @param permissions  The requested permissions.
     * @param grantResults The grant results for the corresponding permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            initUI();
        }
    }

    /**
     * Initialize the UI by adding List View Fragment and Details Fragment (if tablet).
     */
    private void initUI() {
        String fragmentTag = "LISTVIEW_FRAGMENT";
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_main_activity) == null) {
            // Add List View Fragment
            Fragment fragment = new ListViewFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_main_activity, fragment, fragmentTag)
                    .addToBackStack(fragmentTag)
                    .commit();
            // If tablet, also add Details Fragment
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