package fr.vegeto52.realestatemanager.ui.mainActivity.detailsFragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import fr.vegeto52.realestatemanager.R;
import fr.vegeto52.realestatemanager.database.repository.ViewModelFactory;
import fr.vegeto52.realestatemanager.databinding.FragmentDetailsBinding;
import fr.vegeto52.realestatemanager.model.Photo;
import fr.vegeto52.realestatemanager.model.RealEstate;
import fr.vegeto52.realestatemanager.ui.mainActivity.editFragment.EditFragment;

/**
 * The DetailsFragment class extends Fragment and displays detailed information about a RealEstate entity.
 * It includes a RecyclerView for displaying photos and provides an option to edit the RealEstate details.
 */
public class DetailsFragment extends Fragment {

    // View Binding
    FragmentDetailsBinding mBinding;

    // UI elements
    RecyclerView mRecyclerViewPhoto;
    TextView mTextDescription, mTextAdress, mTextSurface, mTextNumberOfRooms, mTextPointsOfInterest, mTextDateOfEntry, mTextDateOfSale, mTextAgent, mTextPrice;
    ImageView mImageApi;
    Toolbar mToolbar;
    ImageButton mBackButton, mEditButton;

    // ViewModel for handling data operations
    private DetailsFragmentViewModel mDetailsFragmentViewModel;

    // Data related to the displayed RealEstate
    private RealEstate mRealEstate;
    private Long mRealEstateId;
    private List<Photo> mPhotoList;

    // Flag indicating whether the device is a tablet
    private boolean mIsTablet;

    /**
     * Default constructor for the DetailsFragment class.
     */
    public DetailsFragment() {
        // Required empty public constructor
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
        mBinding = FragmentDetailsBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();

        // Initialize UI elements
        mRecyclerViewPhoto = mBinding.recyclerviewFragmentDetailsPhoto;
        mTextDescription = mBinding.descriptionDetailsFragment;
        mTextAdress = mBinding.adressDetailsFragment;
        mImageApi = mBinding.mapAdressDetailsFragment;
        mTextSurface = mBinding.surfaceDetailsFragment;
        mTextNumberOfRooms = mBinding.numberOfRoomsDetailsFragment;
        mTextPointsOfInterest = mBinding.pointsOfInterestDetailsFragment;
        mTextDateOfEntry = mBinding.dateOfEntryDetailsFragment;
        mTextDateOfSale = mBinding.dateOfSaleDetailsFragment;
        mTextAgent = mBinding.agentDetailsFragment;
        mTextPrice = mBinding.priceDetailsFragment;

        mToolbar = mBinding.detailsFragmentToolbar;
        mBackButton = mBinding.detailsFragmentBackButton;
        mEditButton = mBinding.detailsFragmentEditButton;

        // Retrieve RealEstate ID from arguments
        Bundle args = getArguments();
        if (args != null) {
            mRealEstateId = args.getLong("idRealEstate");
        }

        // Check if the device is a tablet
        int smallestScreenWidthDp = getResources().getConfiguration().smallestScreenWidthDp;
        mIsTablet = smallestScreenWidthDp >= 600;

        // Hide back button on tablets
        if (mIsTablet) {
            mBackButton.setVisibility(View.INVISIBLE);
        }

        return view;
    }

    /**
     * Called after the fragment's view has been created.
     *
     * @param view               The created view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mRealEstateId != null) {
            initViewModel();
        }
    }

    /**
     * Initializes the toolbar, setting up click listeners for back and edit buttons.
     */
    private void initToolbar() {
        mBackButton.setOnClickListener(view -> requireActivity().onBackPressed());
        mEditButton.setOnClickListener(view -> {
            Fragment fragment = new EditFragment();
            Bundle args = new Bundle();
            args.putLong("idRealEstate", mRealEstateId);
            fragment.setArguments(args);
            if (view.getContext() instanceof AppCompatActivity) {
                ((AppCompatActivity) view.getContext()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_main_activity, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    /**
     * Initializes the toolbar, ViewModel, and observes LiveData for RealEstate details and photos.
     */
    private void initViewModel() {
        mDetailsFragmentViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(requireContext())).get(DetailsFragmentViewModel.class);
        mDetailsFragmentViewModel.getRealEstateLiveData(mRealEstateId).observe(getViewLifecycleOwner(), realEstate -> {
            mRealEstate = realEstate;
            mDetailsFragmentViewModel.getListPhotoToRealEstate(mRealEstateId).observe(getViewLifecycleOwner(), photoList -> {
                mPhotoList = photoList;
                initUi();
                initRecyclerView();
                initToolbar();
            });
        });
    }

    /**
     * Initializes the UI elements with RealEstate data.
     */
    private void initUi() {
        mTextDescription.setText(!TextUtils.isEmpty(mRealEstate.getDescription()) ? mRealEstate.getDescription() : "Description not provided");
        mTextAdress.setText(!TextUtils.isEmpty(mRealEstate.getAddress()) ? mRealEstate.getAddress() : "Address not provided");
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        mTextSurface.setText((mRealEstate.getSurface() != null) ? (decimalFormat.format(mRealEstate.getSurface()) + " m²") : "Surface not provided");
        mTextNumberOfRooms.setText((mRealEstate.getNumberOfRooms() != null) ? String.valueOf(mRealEstate.getNumberOfRooms()) : "Number of rooms not provided");
        mTextPointsOfInterest.setText(!TextUtils.isEmpty(mRealEstate.getPointsOfInterest()) ? mRealEstate.getPointsOfInterest() : "Points of interest not provided");
        mTextDateOfEntry.setText(!TextUtils.isEmpty(mRealEstate.getDateOfEntry()) ? mRealEstate.getDateOfEntry() : "Date of entry not provided");
        mTextDateOfSale.setText(!TextUtils.isEmpty(mRealEstate.getDateOfSale()) ? mRealEstate.getDateOfSale() : "Available");
        mTextAgent.setText(!TextUtils.isEmpty(mRealEstate.getAgent()) ? mRealEstate.getAgent() : "Agent not provided");

        Double price = mRealEstate.getPrice();
        String priceText = (price != null) ? ("$" + NumberFormat.getNumberInstance(Locale.getDefault()).format(price)) : "Price not provided";
        mTextPrice.setText(priceText);
        initStaticMapsApi();
    }

    /**
     * Initializes the RecyclerView for displaying photos.
     */
    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        DetailsFragmentPhotoAdapter detailsFragmentPhotoAdapter = new DetailsFragmentPhotoAdapter(mPhotoList, mIsTablet);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(), layoutManager.getOrientation());
        mRecyclerViewPhoto.addItemDecoration(dividerItemDecoration);
        mRecyclerViewPhoto.setLayoutManager(layoutManager);
        mRecyclerViewPhoto.setAdapter(detailsFragmentPhotoAdapter);
        mBinding.photoCarouselEmptyDetailsFragment.setVisibility(mPhotoList.isEmpty() ? View.VISIBLE : View.GONE);
    }

    /**
     * Initializes the Static Maps API, displaying a map image based on the RealEstate address.
     */
    private void initStaticMapsApi(){
        mDetailsFragmentViewModel.getMapsStatic(mRealEstate.getAddress()).observe(getViewLifecycleOwner(), responseBodyResponse -> {
            if (responseBodyResponse.isSuccessful() && responseBodyResponse.body() != null){
                String imageUrl = responseBodyResponse.raw().request().url().toString();
                Glide.with(requireContext())
                        .load(imageUrl)
                        .into(mImageApi);
            } else {
                mImageApi.setImageResource(R.drawable.baseline_signal_cellular_connected_no_internet_4_bar_24);
            }
        });
    }
}