package fr.vegeto52.realestatemanager.ui.mainActivity.listViewFragment;

import android.net.Uri;
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

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import fr.vegeto52.realestatemanager.R;
import fr.vegeto52.realestatemanager.model.Photo;
import fr.vegeto52.realestatemanager.model.RealEstate;
import fr.vegeto52.realestatemanager.ui.mainActivity.detailsFragment.DetailsFragment;

/**
 * Created by Vegeto52-PC on 15/11/2023.
 */
public class ListViewRealEstateAdapter extends RecyclerView.Adapter<ListViewRealEstateAdapter.ViewHolder> {

    private static List<RealEstate> mRealEstateList;
    private static List<Photo> mPhotoList;


    public ListViewRealEstateAdapter(List<RealEstate> realEstateList, List<Photo> photoList) {
        mRealEstateList = realEstateList;
        mPhotoList = photoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fragment_list_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.displayRealEstate(mRealEstateList.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new DetailsFragment();
                Bundle args = new Bundle();
                args.putLong("idRealEstate", holder.id);
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
    }

    @Override
    public int getItemCount() {
        return mRealEstateList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView photoRealEstate;
        public TextView typeRealEstate;
        public TextView cityRealEstate;
        public TextView priceRealEstate;
        public ImageView iconSaleRealEstate;
        public long id;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            photoRealEstate = itemView.findViewById(R.id.photo_real_estate_item);
            typeRealEstate = itemView.findViewById(R.id.type_real_estate_item);
            cityRealEstate = itemView.findViewById(R.id.city_real_estate_item);
            priceRealEstate = itemView.findViewById(R.id.price_real_estate_item);
            iconSaleRealEstate = itemView.findViewById(R.id.icon_sale_real_estate_item);
        }

        public void displayRealEstate(RealEstate realEstate){

            id = realEstate.getId();

            typeRealEstate.setText(!TextUtils.isEmpty(realEstate.getType()) ? realEstate.getType() : "Type not provided");
            cityRealEstate.setText(!TextUtils.isEmpty(realEstate.getAddress()) ? realEstate.getAddress() : "Address not provided");
            Double price = realEstate.getPrice();
            String priceText = (price != null) ? ("$" + NumberFormat.getNumberInstance(Locale.getDefault()).format(price)) : "Price not provided";
            priceRealEstate.setText(priceText);
            if (realEstate.isStatut()){
                iconSaleRealEstate.setImageResource(R.drawable.baseline_cancel_24);
            } else {
                iconSaleRealEstate.setImageResource(R.drawable.baseline_check_circle_24);
            }

            Photo firstPhoto = getFirstPhotoForRealEstate(id);
            if (firstPhoto != null){
                Glide.with(itemView.getContext())
                        .load(firstPhoto.getUriPhoto())
                        .centerCrop()
                        .into(photoRealEstate);
            }
        }

        private Photo getFirstPhotoForRealEstate(long realEstateId){
            for (Photo photo : mPhotoList){
                if (photo.getRealEstateId() == realEstateId){
                    return photo;
                }
            }
            return null;
        }
    }
}
