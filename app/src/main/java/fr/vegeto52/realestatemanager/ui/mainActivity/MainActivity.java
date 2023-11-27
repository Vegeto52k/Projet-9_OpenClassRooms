package fr.vegeto52.realestatemanager.ui.mainActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import fr.vegeto52.realestatemanager.R;
import fr.vegeto52.realestatemanager.ui.mainActivity.listViewFragment.ListViewFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (getResources().getConfiguration().smallestScreenWidthDp >= 600){

        } else {
            Fragment fragment = null;
            fragment = new ListViewFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_main_activity, fragment)
                    .commit();
        }
    }
}