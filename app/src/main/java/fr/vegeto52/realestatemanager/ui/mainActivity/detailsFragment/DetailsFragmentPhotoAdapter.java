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

import fr.vegeto52.realestatemanager.R;

/**
 * Created by Vegeto52-PC on 17/11/2023.
 */
public class DetailsFragmentPhotoAdapter extends RecyclerView.Adapter<DetailsFragmentPhotoAdapter.ViewHolder> {

    Context mContext;
    ArrayList<String> mUriArrayList;
    OnItemClickListener mOnItemClickListener;

    public DetailsFragmentPhotoAdapter(Context context, ArrayList<String> arrayList){
        this.mContext = context;
        this.mUriArrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_carousel_photo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(mContext).load(mUriArrayList.get(position)).into(holder.mImageView);
        holder.mImageView.setOnClickListener(view -> mOnItemClickListener.onClick(holder.mImageView, mUriArrayList.get(position)));
    }

    @Override
    public int getItemCount() {
        return mUriArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.photo_real_estate_item);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(ImageView imageView, String uri);
    }
}
