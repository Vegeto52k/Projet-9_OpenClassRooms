package fr.vegeto52.realestatemanager.ui.mainActivity.detailsFragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import fr.vegeto52.realestatemanager.R;
import fr.vegeto52.realestatemanager.model.Photo;
import fr.vegeto52.realestatemanager.ui.mainActivity.PhotoViewFragment;

/**
 * Created by Vegeto52-PC on 17/11/2023.
 */
public class DetailsFragmentPhotoAdapter extends RecyclerView.Adapter<DetailsFragmentPhotoAdapter.ViewHolder> {

    private static List<Photo> mPhotoList;
    private static boolean mIsTablet;

    public DetailsFragmentPhotoAdapter(List<Photo> photoList, boolean isTablet){
        mPhotoList = photoList;
        mIsTablet = isTablet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_carousel_photo_details_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.displayPhoto(mPhotoList.get(position));

        Glide.with(holder.photoCarousel.getContext())
                .load(mPhotoList.get(position).getUriPhoto())
                .centerCrop()
                .into(holder.photoCarousel);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new PhotoViewFragment();
                Bundle args = new Bundle();
                args.putParcelable("uriPhoto", mPhotoList.get(holder.getAdapterPosition()).getUriPhoto());
                fragment.setArguments(args);
                if (view.getContext() instanceof AppCompatActivity){
                    if (mIsTablet){
                        ((AppCompatActivity) view.getContext()).getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frament_main_activity_2, fragment)
                                .addToBackStack(null)
                                .commit();
                    } else {
                        ((AppCompatActivity) view.getContext()).getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_main_activity, fragment)
                                .addToBackStack(null)
                                .commit();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPhotoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView photoCarousel;
        View blackBand;
        TextView textDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            photoCarousel = itemView.findViewById(R.id.item_photo_carousel_details_fragment);
            blackBand = itemView.findViewById(R.id.item_photo_black_band_details_fragment);
            textDescription = itemView.findViewById(R.id.item_photo_text_description_details_fragment);
        }

        public void displayPhoto(Photo photo){
            if (TextUtils.isEmpty(photo.getDescription())){
                blackBand.setVisibility(View.GONE);
            } else {
                blackBand.setVisibility(View.VISIBLE);
                textDescription.setText(photo.getDescription());
            }
        }
    }
}
