package fr.vegeto52.realestatemanager.ui.mainActivity.addFragment;

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
 * The AddFragmentPhotoAdapter class extends RecyclerView.Adapter and is responsible for displaying photos
 * in a RecyclerView in the AddFragment. It includes interfaces OnEditDescriptionClickListener and OnRemovePhotoClickListener
 * to handle click events on edit and remove buttons.
 */
public class AddFragmentPhotoAdapter extends RecyclerView.Adapter<AddFragmentPhotoAdapter.ViewHolder> {

    public OnEditDescriptionClickListener mEditDescriptionClickListener;
    public OnRemovePhotoClickListener mRemovePhotoClickListener;

    // Interface to handle click events on edit button
    public interface OnEditDescriptionClickListener {
        void onEditDescriptionClick(int position);
    }

    // Interface to handle click events on remove button
    public interface OnRemovePhotoClickListener {
        void onRemoveClick(int position);
    }

    /**
     * Sets the listener for edit button click events.
     *
     * @param listener Listener for edit button click events.
     */
    public void setOnEditDescriptionClickListener(OnEditDescriptionClickListener listener) {
        mEditDescriptionClickListener = listener;
    }

    // List of photos to be displayed
    private static List<Photo> mPhotoList;

    /**
     * Constructor for AddFragmentPhotoAdapter.
     *
     * @param photoList                List of photos to be displayed.
     * @param removePhotoClickListener Listener for remove button click events.
     */
    public AddFragmentPhotoAdapter(List<Photo> photoList, OnRemovePhotoClickListener removePhotoClickListener) {
        mPhotoList = photoList;
        mRemovePhotoClickListener = removePhotoClickListener;
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
    public AddFragmentPhotoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_carousel_photo_add_fragment, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder   The ViewHolder that should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull AddFragmentPhotoAdapter.ViewHolder holder, int position) {
        holder.displayPhoto(mPhotoList.get(position));

        holder.deleteButtonCarousel.setOnClickListener(view -> {
            if (mRemovePhotoClickListener != null) {
                mRemovePhotoClickListener.onRemoveClick(holder.getAdapterPosition());
                holder.textDescription.setText("");
            }
        });

        holder.editDescription.setOnClickListener(view -> {
            if (mEditDescriptionClickListener != null) {
                mEditDescriptionClickListener.onEditDescriptionClick(holder.getAdapterPosition());
            }
        });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mPhotoList.size();
    }

    /**
     * The ViewHolder class represents each item in the RecyclerView.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView photoCarousel;
        public ImageButton deleteButtonCarousel;
        public ImageButton editDescription;
        public View blackBand;
        public TextView textDescription;

        /**
         * Constructor for ViewHolder.
         *
         * @param itemView The View for each item in the RecyclerView.
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            photoCarousel = itemView.findViewById(R.id.item_photo_carousel_add_fragment);
            deleteButtonCarousel = itemView.findViewById(R.id.item_photo_delete_button_add_fragment);
            editDescription = itemView.findViewById(R.id.item_photo_edit_description_button_add_fragment);
            blackBand = itemView.findViewById(R.id.item_photo_black_band_add_fragment);
            textDescription = itemView.findViewById(R.id.item_photo_text_description_add_fragment);
        }

        /**
         * Displays the photo in the item view.
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

            Glide.with(itemView.getContext())
                    .load(photo.getUriPhoto())
                    .centerCrop()
                    .into(photoCarousel);
        }
    }
}
