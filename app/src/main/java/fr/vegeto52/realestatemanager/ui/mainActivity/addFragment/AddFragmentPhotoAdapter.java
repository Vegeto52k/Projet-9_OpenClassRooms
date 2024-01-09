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
 * Created by Vegeto52-PC on 19/12/2023.
 */
public class AddFragmentPhotoAdapter extends RecyclerView.Adapter<AddFragmentPhotoAdapter.ViewHolder> {

    public OnEditDescriptionClickListener mEditDescriptionClickListener;
    public OnRemovePhotoClickListener mRemovePhotoClickListener;

    public interface OnEditDescriptionClickListener {
        void onEditDescriptionClick(int position);
    }

    public interface OnRemovePhotoClickListener {
        void onRemoveClick(int position);
    }

    public void setOnEditDescriptionClickListener(OnEditDescriptionClickListener listener) {
        mEditDescriptionClickListener = listener;
    }

    private static List<Photo> mPhotoList;

    public AddFragmentPhotoAdapter(List<Photo> photoList, OnRemovePhotoClickListener removePhotoClickListener) {
        mPhotoList = photoList;
        mRemovePhotoClickListener = removePhotoClickListener;
    }

    @NonNull
    @Override
    public AddFragmentPhotoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_carousel_photo_add_fragment, parent, false);
        return new ViewHolder(view);
    }

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

    @Override
    public int getItemCount() {
        return mPhotoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView photoCarousel;
        public ImageButton deleteButtonCarousel;
        public ImageButton editDescription;
        public View blackBand;
        public TextView textDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            photoCarousel = itemView.findViewById(R.id.item_photo_carousel_add_fragment);
            deleteButtonCarousel = itemView.findViewById(R.id.item_photo_delete_button_add_fragment);
            editDescription = itemView.findViewById(R.id.item_photo_edit_description_button_add_fragment);
            blackBand = itemView.findViewById(R.id.item_photo_black_band_add_fragment);
            textDescription = itemView.findViewById(R.id.item_photo_text_description_add_fragment);
        }

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
