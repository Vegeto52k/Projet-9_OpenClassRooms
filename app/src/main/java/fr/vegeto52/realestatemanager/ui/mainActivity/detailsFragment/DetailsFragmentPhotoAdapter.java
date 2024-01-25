package fr.vegeto52.realestatemanager.ui.mainActivity.detailsFragment;

import android.os.Bundle;
import android.text.TextUtils;
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

import java.util.List;

import fr.vegeto52.realestatemanager.R;
import fr.vegeto52.realestatemanager.model.Photo;
import fr.vegeto52.realestatemanager.ui.mainActivity.photoViewFragment.PhotoViewFragment;

/**
 * The DetailsFragmentPhotoAdapter class extends RecyclerView.Adapter and is responsible for displaying photos
 * in a carousel within the DetailsFragment.
 */
public class DetailsFragmentPhotoAdapter extends RecyclerView.Adapter<DetailsFragmentPhotoAdapter.ViewHolder> {

    // List of photos to be displayed
    private static List<Photo> mPhotoList;

    // Flag indicating whether the device is a tablet
    private static boolean mIsTablet;

    /**
     * Constructor for the DetailsFragmentPhotoAdapter class.
     *
     * @param photoList List of photos to be displayed.
     * @param isTablet  Flag indicating whether the device is a tablet.
     */
    public DetailsFragmentPhotoAdapter(List<Photo> photoList, boolean isTablet) {
        mPhotoList = photoList;
        mIsTablet = isTablet;
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_carousel_photo_details_fragment, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Called to display the data at the specified position.
     *
     * @param holder   The ViewHolder that should be updated to represent the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.displayPhoto(mPhotoList.get(position));

        Glide.with(holder.photoCarousel.getContext())
                .load(mPhotoList.get(position).getUriPhoto())
                .centerCrop()
                .into(holder.photoCarousel);

        holder.itemView.setOnClickListener(view -> {
            Fragment fragment = new PhotoViewFragment();
            Bundle args = new Bundle();
            args.putParcelable("uriPhoto", mPhotoList.get(holder.getAdapterPosition()).getUriPhoto());
            fragment.setArguments(args);
            if (view.getContext() instanceof AppCompatActivity) {
                if (mIsTablet) {
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
        });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in the data set held by the adapter.
     */
    @Override
    public int getItemCount() {
        return mPhotoList.size();
    }

    /**
     * The ViewHolder class represents an item view in the RecyclerView and holds references to its UI elements.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // UI elements
        ImageView photoCarousel;
        View blackBand;
        TextView textDescription;

        /**
         * Constructor for the ViewHolder class.
         *
         * @param itemView The item view in the RecyclerView.
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize UI elements
            photoCarousel = itemView.findViewById(R.id.item_photo_carousel_details_fragment);
            blackBand = itemView.findViewById(R.id.item_photo_black_band_details_fragment);
            textDescription = itemView.findViewById(R.id.item_photo_text_description_details_fragment);
        }

        /**
         * Displays a photo in the item view, handling the visibility of the description.
         *
         * @param photo The Photo object to be displayed.
         */
        public void displayPhoto(Photo photo) {
            if (TextUtils.isEmpty(photo.getDescription())) {
                blackBand.setVisibility(View.GONE);
            } else {
                blackBand.setVisibility(View.VISIBLE);
                textDescription.setText(photo.getDescription());
            }
        }
    }
}
