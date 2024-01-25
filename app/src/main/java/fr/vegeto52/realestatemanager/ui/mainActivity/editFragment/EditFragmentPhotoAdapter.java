package fr.vegeto52.realestatemanager.ui.mainActivity.editFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import fr.vegeto52.realestatemanager.R;
import fr.vegeto52.realestatemanager.model.Photo;

/**
 * The EditFragmentPhotoAdapter class extends RecyclerView.Adapter and is responsible for displaying photos
 * in the EditFragment RecyclerView. It includes click listeners for removing a photo and editing its description.
 */
public class EditFragmentPhotoAdapter extends RecyclerView.Adapter<EditFragmentPhotoAdapter.ViewHolder> {

    /**
     * Interface definition for a callback to be invoked when a photo's description is edited.
     */
    public interface OnEditDescriptionClickListener {
        void onEditDescriptionClick(int position);
    }

    public OnEditDescriptionClickListener mEditDescriptionClickListener;

    /**
     * Sets the listener for editing photo descriptions.
     *
     * @param listener The listener to be set.
     */
    public void setOnEditDescriptionClickListener(OnEditDescriptionClickListener listener) {
        mEditDescriptionClickListener = listener;
    }

    private static List<Photo> mPhotoList;
    private final OnRemovePhotoClickListener mRemovePhotoClickListener;

    /**
     * Interface definition for a callback to be invoked when a photo is removed.
     */
    public interface OnRemovePhotoClickListener {
        void onRemoveClick(int position);
    }

    /**
     * Constructs an EditFragmentPhotoAdapter with the provided list of photos and a listener for removing photos.
     *
     * @param photoList                The list of photos to be displayed.
     * @param removePhotoClickListener The listener for removing photos.
     */
    public EditFragmentPhotoAdapter(List<Photo> photoList, OnRemovePhotoClickListener removePhotoClickListener) {
        mPhotoList = photoList;
        mRemovePhotoClickListener = removePhotoClickListener;
    }

    @NonNull
    @Override
    public EditFragmentPhotoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the RecyclerView
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_carousel_photo_edit_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EditFragmentPhotoAdapter.ViewHolder holder, int position) {
        // Display the photo in the item view
        holder.displayPhoto(mPhotoList.get(position));

        // Set up click listener for the delete button
        holder.deleteButtonCarousel.setOnClickListener(view -> {
            if (mRemovePhotoClickListener != null) {
                mRemovePhotoClickListener.onRemoveClick(holder.getAdapterPosition());
                holder.textDescription.setText("");
            }
        });

        // Set up click listener for the edit description button
        holder.editDescription.setOnClickListener(view -> {
            if (mEditDescriptionClickListener != null) {
                mEditDescriptionClickListener.onEditDescriptionClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPhotoList.size();
    }

    /**
     * The ViewHolder class represents each item in the RecyclerView and holds references to its views.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView photoCarousel;
        public ImageButton deleteButtonCarousel;
        public ImageButton editDescription;
        public View blackBand;
        public TextView textDescription;

        /**
         * Constructor for the ViewHolder.
         *
         * @param itemView The view representing each item in the RecyclerView.
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize views
            photoCarousel = itemView.findViewById(R.id.item_photo_carousel_edit_fragment);
            deleteButtonCarousel = itemView.findViewById(R.id.item_photo_delete_button_edit_fragment);
            editDescription = itemView.findViewById(R.id.item_photo_edit_description_button_edit_fragment);
            blackBand = itemView.findViewById(R.id.item_photo_black_band_edit_fragment);
            textDescription = itemView.findViewById(R.id.item_photo_text_description_edit_fragment);
        }

        /**
         * Displays the photo in the item view, including its description if available.
         *
         * @param photo The photo to be displayed.
         */
        public void displayPhoto(Photo photo) {
            if (TextUtils.isEmpty(photo.getDescription())) {
                blackBand.setVisibility(View.GONE);
            } else {
                blackBand.setVisibility(View.VISIBLE);
                textDescription.setText(photo.getDescription());
            }

            // Load the photo image using Glide
            Glide.with(itemView.getContext())
                    .load(photo.getUriPhoto())
                    .centerCrop()
                    .into(photoCarousel);
        }
    }
}
