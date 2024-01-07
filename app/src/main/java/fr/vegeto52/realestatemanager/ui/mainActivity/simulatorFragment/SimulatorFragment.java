package fr.vegeto52.realestatemanager.ui.mainActivity.simulatorFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.text.DecimalFormat;

import fr.vegeto52.realestatemanager.R;
import fr.vegeto52.realestatemanager.databinding.FragmentSimulatorBinding;
import fr.vegeto52.realestatemanager.ui.mainActivity.listViewFragment.ListViewFragment;
import fr.vegeto52.realestatemanager.ui.mainActivity.locationFragment.LocationFragment;


public class SimulatorFragment extends Fragment {

    private FragmentSimulatorBinding mBinding;
    private Toolbar mToolbar;
    private BottomNavigationView mBottomNavigationView;
    private EditText mEditLoan, mEditInitialPayment, mEditInterestRate, mEditLoanTerm;
    private Button mButtonCalculate, mButtonReset;
    private TextView mTextMonthlyPayment, mTextInterestAmount;


    public SimulatorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("Vérif Bottom", "Coco l'asticot");
        if (mEditLoan != null && mEditInitialPayment != null && mEditInterestRate != null && mEditLoanTerm != null && mTextMonthlyPayment != null && mTextInterestAmount != null){
            outState.putString("EditLoan", mEditLoan.getText().toString());
            outState.putString("EditInitialPayment", mEditInitialPayment.getText().toString());
            outState.putString("EditInterestRate", mEditInterestRate.getText().toString());
            outState.putString("EditLoanTerm", mEditLoanTerm.getText().toString());
            outState.putString("TextMonthlyPayment", mTextMonthlyPayment.getText().toString());
            outState.putString("TextInterestAmout", mTextInterestAmount.getText().toString());
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null){
            mEditLoan.setText(savedInstanceState.getString("EditLoan"));
            mEditInitialPayment.setText(savedInstanceState.getString("EditInitialPayment"));
            mEditInterestRate.setText(savedInstanceState.getString("EditInterestRate"));
            mEditLoanTerm.setText(savedInstanceState.getString("EditLoanTerm"));
            mTextMonthlyPayment.setText(savedInstanceState.getString("TextMonthlyPayment"));
            mTextInterestAmount.setText(savedInstanceState.getString("TextInterestAmout"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentSimulatorBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();

        mToolbar = view.findViewById(R.id.simulator_fragment_toolbar);
        mBottomNavigationView = view.findViewById(R.id.simulator_fragment_bottom_navigation_view);

        mEditLoan = view.findViewById(R.id.simulator_fragment_edit_loan);
        mEditInitialPayment = view.findViewById(R.id.simulator_fragment_edit_initial_payment);
        mEditInterestRate = view.findViewById(R.id.simulator_fragment_edit_interest_rate);
        mEditLoanTerm = view.findViewById(R.id.simulator_fragment_edit_loan_term);
        mButtonCalculate = view.findViewById(R.id.simulator_fragment_button_calculate);
        mButtonReset = view.findViewById(R.id.simulator_fragment_button_reset);
        mTextMonthlyPayment = view.findViewById(R.id.simulator_fragment_edit_monthly_payment);
        mTextInterestAmount = view.findViewById(R.id.simulator_fragment_edit_interest_amount);

        initToolbar();
        initBottomNavigationView();
        initButton();

        return view;
    }

    private void initToolbar(){

    }

    private void initBottomNavigationView(){
        mBottomNavigationView.setSelectedItemId(R.id.menu_bottom_navigation_simulator);
        mBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                int id = item.getItemId();
                if (id == R.id.menu_bottom_navigation_list){
                    String fragmentTag = "LISTVIEW_FRAGMENT";
                    ListViewFragment listViewFragment = (ListViewFragment) getActivity().getSupportFragmentManager().findFragmentByTag(fragmentTag);
                    if (listViewFragment == null){
                        fragment = new ListViewFragment();
                        if (getActivity() instanceof AppCompatActivity){
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_main_activity, fragment, fragmentTag)
                                    .addToBackStack(fragmentTag)
                                    .commit();
                        }
                    } else {
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_main_activity, listViewFragment, fragmentTag)
                                .addToBackStack(fragmentTag)
                                .commit();
                    }

                } else if (id == R.id.menu_bottom_navigation_location) {
                    String fragmentTag = "LOCATION_FRAGMENT";
                    LocationFragment locationFragment = (LocationFragment) getActivity().getSupportFragmentManager().findFragmentByTag(fragmentTag);
                    if (locationFragment == null){
                        fragment = new LocationFragment();
                        if (getActivity() instanceof AppCompatActivity){
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_main_activity, fragment, fragmentTag)
                                    .addToBackStack(fragmentTag)
                                    .commit();
                        }
                    } else {
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_main_activity, locationFragment, fragmentTag)
                                .addToBackStack(fragmentTag)
                                .commit();
                    }
                }
                return false;
            }
        });
    }

    private void initButton(){
        mButtonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateLoan();
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetEditText();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void calculateLoan(){
        if (!TextUtils.isEmpty(mEditLoan.getText()) && !TextUtils.isEmpty(mEditInitialPayment.getText()) && !TextUtils.isEmpty(mEditInterestRate.getText()) && !TextUtils.isEmpty(mEditLoanTerm.getText())){
            int loanAmount = Integer.parseInt(mEditLoan.getText().toString());
            int initialPayment = Integer.parseInt(mEditInitialPayment.getText().toString());
            double interestRate = Double.parseDouble(mEditInterestRate.getText().toString());
            int loanTerm = Integer.parseInt(mEditLoanTerm.getText().toString());

            int principal = loanAmount - initialPayment;
            double monthlyInterestRate = interestRate / 100 / 12;
            int numberOfPayments = loanTerm * 12;

            double monthlyPayment = (principal * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -numberOfPayments));
            double totalInterest = (monthlyPayment * numberOfPayments) - principal;

            DecimalFormat decimalFormat = new DecimalFormat("#.##");

            mTextMonthlyPayment.setText(decimalFormat.format(monthlyPayment) + " $/month");
            mTextInterestAmount.setText(decimalFormat.format(totalInterest) + " $");
        } else {
            Toast.makeText(getContext(), "Complete all informations", Toast.LENGTH_LONG).show();
        }
    }

    private void resetEditText(){
        mEditLoan.setText(null);
        mEditInitialPayment.setText(null);
        mEditInterestRate.setText(null);
        mEditLoanTerm.setText(null);
        mTextMonthlyPayment.setText("Result");
        mTextInterestAmount.setText("Result");
    }
}