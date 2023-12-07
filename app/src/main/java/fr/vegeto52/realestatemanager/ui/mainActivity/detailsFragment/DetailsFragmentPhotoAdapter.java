package fr.vegeto52.realestatemanager.ui.mainActivity.detailsFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import fr.vegeto52.realestatemanager.R;
import fr.vegeto52.realestatemanager.model.Photo;

/**
 * Created by Vegeto52-PC on 17/11/2023.
 */
public class DetailsFragmentPhotoAdapter extends RecyclerView.Adapter<DetailsFragmentPhotoAdapter.ViewHolder> {

    private static List<Photo> mPhotoList;

    public DetailsFragmentPhotoAdapter(List<Photo> photoList){
        mPhotoList = photoList;
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
    }

    @Override
    public int getItemCount() {
        return mPhotoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView photoCarousel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            photoCarousel = itemView.findViewById(R.id.item_photo_carousel_details_fragment);
        }

        public void displayPhoto(Photo photo){

        }
    }
}
