package fr.vegeto52.realestatemanager.ui.mainActivity;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.Objects;

import fr.vegeto52.realestatemanager.R;
import fr.vegeto52.realestatemanager.databinding.FragmentPhotoViewBinding;


public class PhotoViewFragment extends Fragment {

    FragmentPhotoViewBinding mBinding;
    ImageView mPhotoView;
    Toolbar mToolbar;
    ImageButton mBackButton;
    Uri mUriPhoto;


    public PhotoViewFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("uriPhoto", mUriPhoto);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentPhotoViewBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();

        mPhotoView = view.findViewById(R.id.photo_view_fragment_photo_view);
        mToolbar = view.findViewById(R.id.photo_view_fragment_toolbar);
        mBackButton = view.findViewById(R.id.photo_view_fragment_back_button);

            Bundle args = getArguments();
            if (args != null){
                mUriPhoto = args.getParcelable("uriPhoto");
                Log.d("Vérification uri 3", "Uri : " + mUriPhoto);
            }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initToolbar();
        initUI();
    }

    private void initToolbar(){
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().onBackPressed();
            }
        });
    }

    private void initUI(){
        Log.d("Vérification uri", "Uri : " + mUriPhoto);
        Glide.with(requireContext())
                .load(mUriPhoto)
                .error(R.drawable.baseline_cancel_24)
                .into(mPhotoView);
    }
}