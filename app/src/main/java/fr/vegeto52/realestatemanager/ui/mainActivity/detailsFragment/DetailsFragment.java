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

import fr.vegeto52.realestatemanager.BuildConfig;
import fr.vegeto52.realestatemanager.R;
import fr.vegeto52.realestatemanager.database.api.MapsStaticApi;
import fr.vegeto52.realestatemanager.database.api.RetrofitService;
import fr.vegeto52.realestatemanager.database.repository.ViewModelFactory;
import fr.vegeto52.realestatemanager.databinding.FragmentDetailsBinding;
import fr.vegeto52.realestatemanager.model.Photo;
import fr.vegeto52.realestatemanager.model.RealEstate;
import fr.vegeto52.realestatemanager.ui.mainActivity.editFragment.EditFragment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsFragment extends Fragment {

    FragmentDetailsBinding mBinding;
    RecyclerView mRecyclerViewPhoto;
    TextView mTextDescription, mTextAdress, mTextSurface, mTextNumberOfRooms, mTextPointsOfInterest, mTextDateOfEntry, mTextDateOfSale, mTextAgent, mTextPrice;
    ImageView mImageApi;
    Toolbar mToolbar;
    ImageButton mBackButton, mEditButton;
    private DetailsFragmentViewModel mDetailsFragmentViewModel;
    private RealEstate mRealEstate;
    private Long mRealEstateId;
    String MAPS_API_KEY = BuildConfig.MAPS_API_KEY;
    private List<Photo> mPhotoList;
    private boolean mIsTablet;

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentDetailsBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();

        mRecyclerViewPhoto = view.findViewById(R.id.recyclerview_fragment_details_photo);
        mTextDescription = view.findViewById(R.id.description_details_fragment);
        mTextAdress = view.findViewById(R.id.adress_details_fragment);
        mImageApi = view.findViewById(R.id.map_adress_details_fragment);
        mTextSurface = view.findViewById(R.id.surface_details_fragment);
        mTextNumberOfRooms = view.findViewById(R.id.number_of_rooms_details_fragment);
        mTextPointsOfInterest = view.findViewById(R.id.points_of_interest_details_fragment);
        mTextDateOfEntry = view.findViewById(R.id.date_of_entry_details_fragment);
        mTextDateOfSale = view.findViewById(R.id.date_of_sale_details_fragment);
        mTextAgent = view.findViewById(R.id.agent_details_fragment);
        mTextPrice = view.findViewById(R.id.price_details_fragment);

        mToolbar = view.findViewById(R.id.details_fragment_toolbar);
        mBackButton = view.findViewById(R.id.details_fragment_back_button);
        mEditButton = view.findViewById(R.id.details_fragment_edit_button);

        Bundle args = getArguments();
        if (args != null) {
            mRealEstateId = args.getLong("idRealEstate");
        }

        int smallestScreenWidthDp = getResources().getConfiguration().smallestScreenWidthDp;
        mIsTablet = smallestScreenWidthDp >= 600;

        if (mIsTablet) {
            mBackButton.setVisibility(View.INVISIBLE);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mRealEstateId != null) {
            initViewModel();
        }
    }

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

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        DetailsFragmentPhotoAdapter detailsFragmentPhotoAdapter = new DetailsFragmentPhotoAdapter(mPhotoList, mIsTablet);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(), layoutManager.getOrientation());
        mRecyclerViewPhoto.addItemDecoration(dividerItemDecoration);
        mRecyclerViewPhoto.setLayoutManager(layoutManager);
        mRecyclerViewPhoto.setAdapter(detailsFragmentPhotoAdapter);
        mBinding.photoCarouselEmptyDetailsFragment.setVisibility(mPhotoList.isEmpty() ? View.VISIBLE : View.GONE);
    }

    private void initStaticMapsApi() {
        if (TextUtils.isEmpty(mRealEstate.getAddress())) {
            mImageApi.setImageResource(R.drawable.baseline_not_listed_location_24);
        } else {
            MapsStaticApi mapsStaticApi = RetrofitService.getRetrofitInstance().create(MapsStaticApi.class);
            Call<ResponseBody> call = mapsStaticApi.getStaticMaps(mRealEstate.getAddress(), 18, "200x200", "color:blue|" + mRealEstate.getAddress(), MAPS_API_KEY);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        String imageUrl = response.raw().request().url().toString();

                        Glide.with(requireContext())
                                .load(imageUrl)
                                .into(mImageApi);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    mImageApi.setImageResource(R.drawable.baseline_signal_cellular_connected_no_internet_4_bar_24);
                }
            });
        }
    }
}