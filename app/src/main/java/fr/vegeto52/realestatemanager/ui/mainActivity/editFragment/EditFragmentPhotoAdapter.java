package fr.vegeto52.realestatemanager.ui.mainActivity.editFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import fr.vegeto52.realestatemanager.R;
import fr.vegeto52.realestatemanager.model.Photo;

/**
 * Created by Vegeto52-PC on 05/12/2023.
 */
public class EditFragmentPhotoAdapter extends RecyclerView.Adapter<EditFragmentPhotoAdapter.ViewHolder> {

    private static List<Photo> mPhotoList;
    private OnRemovePhotoClickListener mRemovePhotoClickListener;

    public interface OnRemovePhotoClickListener{
        void onRemoveClick(int position);
    }

    public EditFragmentPhotoAdapter(List<Photo> photoList, OnRemovePhotoClickListener removePhotoClickListener){
        mPhotoList = photoList;
        mRemovePhotoClickListener = removePhotoClickListener;
    }

    @NonNull
    @Override
    public EditFragmentPhotoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_carousel_photo_edit_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EditFragmentPhotoAdapter.ViewHolder holder, int position) {
            holder.displayPhoto(mPhotoList.get(position));

            Glide.with(holder.photoCarousel.getContext())
                    .load(mPhotoList.get(position).getUriPhoto())
                    .centerCrop()
                    .into(holder.photoCarousel);

            holder.deleteButtonCarousel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mRemovePhotoClickListener != null){
                        mRemovePhotoClickListener.onRemoveClick(holder.getAdapterPosition());
                    }
                }
            });
    }

    @Override
    public int getItemCount() {
        return mPhotoList.size();
    }

    public void setOnRemovePhotoClickLister(OnRemovePhotoClickListener listener){
        mRemovePhotoClickListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView photoCarousel;
        public ImageButton deleteButtonCarousel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            photoCarousel = itemView.findViewById(R.id.item_photo_carousel_edit_fragment);
            deleteButtonCarousel = itemView.findViewById(R.id.item_photo_delete_button_edit_fragment);
        }

        public void displayPhoto(Photo photo){

        }
    }
}
