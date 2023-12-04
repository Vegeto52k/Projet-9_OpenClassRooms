package fr.vegeto52.realestatemanager.ui.mainActivity.detailsFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import fr.vegeto52.realestatemanager.BuildConfig;
import fr.vegeto52.realestatemanager.R;
import fr.vegeto52.realestatemanager.api.MapsStaticApi;
import fr.vegeto52.realestatemanager.api.RetrofitService;
import fr.vegeto52.realestatemanager.database.repository.ViewModelFactory;
import fr.vegeto52.realestatemanager.databinding.FragmentDetailsBinding;
import fr.vegeto52.realestatemanager.model.RealEstate;
import fr.vegeto52.realestatemanager.ui.mainActivity.editFragment.EditFragment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsFragment extends Fragment {

    FragmentDetailsBinding mBinding;
    RecyclerView mRecyclerViewPhoto;
    TextView mTextDescription;
    TextView mTextAdress;
    ImageView mImageApi;
    TextView mTextSurface;
    TextView mTextNumberOfRooms;
    TextView mTextPointsOfInterest;
    TextView mTextDateOfEntry;
    TextView mTextDateOfSale;
    TextView mTextAgent;
    TextView mTextPrice;
    Toolbar mToolbar;
    ImageButton mBackButton;
    ImageButton mEditButton;
    private DetailsFragmentViewModel mDetailsFragmentViewModel;
    private RealEstate mRealEstate;
    Long mRealEstateId;
    String MAPS_API_KEY = BuildConfig.MAPS_API_KEY;

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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
        if (args != null){
            mRealEstateId = args.getLong("idRealEstate");
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
    }

    private void initToolbar(){
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().onBackPressed();
            }
        });
        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new EditFragment();
                Bundle args = new Bundle();
                args.putLong("idRealEstate", mRealEstate.getId());
                fragment.setArguments(args);
                if (view.getContext() instanceof AppCompatActivity){
                    ((AppCompatActivity) view.getContext()).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_main_activity, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
//        ((AppCompatActivity) requireActivity()).setSupportActionBar(mToolbar);
//        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                requireActivity().onBackPressed();
//            }
//        });
    }

    private void initViewModel(){
            mDetailsFragmentViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(requireContext())).get(DetailsFragmentViewModel.class);
            mDetailsFragmentViewModel.getRealEstateLiveData(mRealEstateId).observe(getViewLifecycleOwner(), new Observer<RealEstate>() {
                @Override
                public void onChanged(RealEstate realEstate) {
                    mRealEstate = realEstate;
                    initUi();
                    initToolbar();
                }
            });
    }

    private void initUi(){
        mTextDescription.setText(!TextUtils.isEmpty(mRealEstate.getDescription()) ? mRealEstate.getDescription() : "Description not provided");
        mTextAdress.setText(!TextUtils.isEmpty(mRealEstate.getAddress()) ? mRealEstate.getAddress() : "Address not provided");
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        mTextSurface.setText(!TextUtils.isEmpty(String.valueOf(mRealEstate.getSurface())) ? decimalFormat.format(mRealEstate.getSurface()) + " sq ft" : "Surface not provided");
        mTextNumberOfRooms.setText(!TextUtils.isEmpty(String.valueOf(mRealEstate.getNumberOfRooms())) ? String.valueOf(mRealEstate.getNumberOfRooms()) : "Number of rooms not provided");
        mTextPointsOfInterest.setText(!TextUtils.isEmpty(mRealEstate.getPointsOfInterest()) ? mRealEstate.getPointsOfInterest() : "Points of interest not provided");
        mTextDateOfEntry.setText(!TextUtils.isEmpty(mRealEstate.getDateOfEntry()) ? mRealEstate.getDateOfEntry() : "Date of entry not provided");
        mTextDateOfSale.setText(!TextUtils.isEmpty(mRealEstate.getDateOfSale()) ? mRealEstate.getDateOfSale() : "Available");
        mTextAgent.setText(!TextUtils.isEmpty(mRealEstate.getAgent()) ? mRealEstate.getAgent() : "Agent not provided");
        if (!TextUtils.isEmpty(String.valueOf(mRealEstate.getPrice()))){
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
            String priceFormate = numberFormat.format(mRealEstate.getPrice());
            mTextPrice.setText("$" + priceFormate);
        } else {
            mTextPrice.setText("");
        }

        initStaticMapsApi();
    //    initCarouselPhoto();
    }

    private void initStaticMapsApi(){
        if (mRealEstate.getAddress().equals("Address not provided")){
            mImageApi.setImageResource(R.drawable.baseline_not_listed_location_24);
        } else {
            MapsStaticApi mapsStaticApi = RetrofitService.getRetrofitInstance().create(MapsStaticApi.class);
            Call<ResponseBody> call = mapsStaticApi.getStaticMaps(mRealEstate.getAddress(), 18, "200x200", "color:blue|" + mRealEstate.getAddress(), MAPS_API_KEY);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful() && response.body() != null){
                        String imageUrl = response.raw().request().url().toString();

                        Glide.with(getContext())
                                .load(imageUrl)
                                .into(mImageApi);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    mImageApi.setImageResource(R.drawable.baseline_signal_cellular_connected_no_internet_4_bar_24);
                }
            });
        }
    }

    private void initCarouselPhoto(){
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("https://www.facilogi.com/blog/wp-content/uploads/2019/03/qualifier-un-bien-immobilier-1.jpg");
        arrayList.add("https://e-immobilier.credit-agricole.fr/var/caeimmo/storage/images/conseils/marche/y-a-t-il-une-bonne-saison-pour-acheter-un-bien-immobilier/22818-1-fre-FR/Y-a-t-il-une-bonne-saison-pour-acheter-un-bien-immobilier.jpg");
        arrayList.add("https://media.paruvendu.fr/cms/pictures//2015120215362761.jpg");

        DetailsFragmentPhotoAdapter detailsFragmentPhotoAdapter = new DetailsFragmentPhotoAdapter(getContext(), arrayList);
        mRecyclerViewPhoto.setAdapter(detailsFragmentPhotoAdapter);
    }
}