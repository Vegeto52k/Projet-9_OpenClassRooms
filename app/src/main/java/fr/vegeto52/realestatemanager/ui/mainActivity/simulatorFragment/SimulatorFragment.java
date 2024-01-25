package fr.vegeto52.realestatemanager.ui.mainActivity.simulatorFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import fr.vegeto52.realestatemanager.R;
import fr.vegeto52.realestatemanager.databinding.FragmentSimulatorBinding;
import fr.vegeto52.realestatemanager.ui.mainActivity.listViewFragment.ListViewFragment;
import fr.vegeto52.realestatemanager.ui.mainActivity.locationFragment.LocationFragment;

/**
 * The SimulatorFragment class represents a fragment for loan simulation.
 * It allows users to calculate monthly payments and interest amounts based on loan parameters.
 */
public class SimulatorFragment extends Fragment {

    // View binding for the fragment
    private FragmentSimulatorBinding mBinding;

    // Bottom navigation view for navigation between fragments
    private BottomNavigationView mBottomNavigationView;

    // UI elements
    private EditText mEditLoan, mEditInitialPayment, mEditInterestRate, mEditLoanTerm;
    private Button mButtonCalculate, mButtonReset;
    private TextView mTextMonthlyPayment, mTextInterestAmount;

    /**
     * Default constructor for the SimulatorFragment class.
     */
    public SimulatorFragment() {
        // Required empty public constructor
    }

    /**
     * Called when the fragment is created.
     *
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Called to save the current dynamic state of the fragment into the given Bundle.
     *
     * @param outState Bundle in which to place the saved state.
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the state of EditText and TextView fields
        if (mEditLoan != null && mEditInitialPayment != null && mEditInterestRate != null && mEditLoanTerm != null && mTextMonthlyPayment != null && mTextInterestAmount != null) {
            outState.putString("EditLoan", mEditLoan.getText().toString());
            outState.putString("EditInitialPayment", mEditInitialPayment.getText().toString());
            outState.putString("EditInterestRate", mEditInterestRate.getText().toString());
            outState.putString("EditLoanTerm", mEditLoanTerm.getText().toString());
            outState.putString("TextMonthlyPayment", mTextMonthlyPayment.getText().toString());
            outState.putString("TextInterestAmout", mTextInterestAmount.getText().toString());
        }
    }

    /**
     * Called to restore the saved state of the fragment.
     *
     * @param savedInstanceState If non-null, this fragment is being reconstructed from a previous saved state as given here.
     */
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        // Restore the state of EditText and TextView fields
        if (savedInstanceState != null) {
            mEditLoan.setText(savedInstanceState.getString("EditLoan"));
            mEditInitialPayment.setText(savedInstanceState.getString("EditInitialPayment"));
            mEditInterestRate.setText(savedInstanceState.getString("EditInterestRate"));
            mEditLoanTerm.setText(savedInstanceState.getString("EditLoanTerm"));
            mTextMonthlyPayment.setText(savedInstanceState.getString("TextMonthlyPayment"));
            mTextInterestAmount.setText(savedInstanceState.getString("TextInterestAmout"));
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
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment using view binding
        mBinding = FragmentSimulatorBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();

        // Initialize UI elements
        mBottomNavigationView = mBinding.simulatorFragmentBottomNavigationView;
        mEditLoan = mBinding.simulatorFragmentEditLoan;
        mEditInitialPayment = mBinding.simulatorFragmentEditInitialPayment;
        mEditInterestRate = mBinding.simulatorFragmentEditInterestRate;
        mEditLoanTerm = mBinding.simulatorFragmentEditLoanTerm;
        mButtonCalculate = mBinding.simulatorFragmentButtonCalculate;
        mButtonReset = mBinding.simulatorFragmentButtonReset;
        mTextMonthlyPayment = mBinding.simulatorFragmentEditMonthlyPayment;
        mTextInterestAmount = mBinding.simulatorFragmentEditInterestAmount;

        // Initialize toolbar, bottom navigation view, and button click listeners
        initBottomNavigationView();
        initButton();

        return view;
    }

    /**
     * Initializes the bottom navigation view.
     * Handles fragment navigation based on user selection.
     */
    private void initBottomNavigationView() {
        // Set selected item in bottom navigation view
        mBottomNavigationView.setSelectedItemId(R.id.menu_bottom_navigation_simulator);
        // Set item selection listener for bottom navigation view
        mBottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment;
            int id = item.getItemId();
            if (id == R.id.menu_bottom_navigation_list) {
                // Navigate to ListViewFragment
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

            } else if (id == R.id.menu_bottom_navigation_location) {
                // Navigate to LocationFragment
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
            }
            return false;
        });
    }

    /**
     * Initializes button click listeners.
     */
    private void initButton() {
        // Set click listeners for calculation and reset buttons
        mButtonCalculate.setOnClickListener(view -> calculateLoan());
        mButtonReset.setOnClickListener(view -> resetEditText());
    }

    /**
     * Calculates the loan based on user input and displays the results.
     */
    @SuppressLint("SetTextI18n")
    private void calculateLoan() {
        // Check if all input fields are non-empty
        if (!TextUtils.isEmpty(mEditLoan.getText()) && !TextUtils.isEmpty(mEditInitialPayment.getText()) && !TextUtils.isEmpty(mEditInterestRate.getText()) && !TextUtils.isEmpty(mEditLoanTerm.getText())) {
            // Parse user input values
            int loanAmount = Integer.parseInt(mEditLoan.getText().toString());
            int initialPayment = Integer.parseInt(mEditInitialPayment.getText().toString());
            double interestRate = Double.parseDouble(mEditInterestRate.getText().toString());
            int loanTerm = Integer.parseInt(mEditLoanTerm.getText().toString());

            // Calculate loan parameters
            int principal = loanAmount - initialPayment;
            double monthlyInterestRate = interestRate / 100 / 12;
            int numberOfPayments = loanTerm * 12;

            double monthlyPayment = (principal * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -numberOfPayments));
            double totalInterest = (monthlyPayment * numberOfPayments) - principal;

            // Round the results to display
            long roundedMonthlyPayment = Math.round(monthlyPayment);
            long roundedTotalInterest = Math.round(totalInterest);

            // Display the results
            mTextMonthlyPayment.setText(roundedMonthlyPayment + " $/month");
            mTextInterestAmount.setText(roundedTotalInterest + " $");
        } else {
            // Display a toast if any input field is empty
            Toast.makeText(getContext(), "Complete all informations", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Resets all input fields to their default values.
     */
    @SuppressLint("SetTextI18n")
    private void resetEditText() {
        mEditLoan.setText(null);
        mEditInitialPayment.setText(null);
        mEditInterestRate.setText(null);
        mEditLoanTerm.setText(null);
        mTextMonthlyPayment.setText("Result");
        mTextInterestAmount.setText("Result");
    }
}