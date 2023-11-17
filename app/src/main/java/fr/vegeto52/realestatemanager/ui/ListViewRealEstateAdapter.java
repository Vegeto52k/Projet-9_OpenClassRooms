package fr.vegeto52.realestatemanager.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import fr.vegeto52.realestatemanager.R;
import fr.vegeto52.realestatemanager.model.RealEstate;

/**
 * Created by Vegeto52-PC on 15/11/2023.
 */
public class ListViewRealEstateAdapter extends RecyclerView.Adapter<ListViewRealEstateAdapter.ViewHolder> {

    private static List<RealEstate> mRealEstateList;

    public ListViewRealEstateAdapter(List<RealEstate> realEstateList) {
        mRealEstateList = realEstateList;
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

        Glide.with(holder.photoRestaurant.getContext())
                .load(mRealEstateList.get(position).getPhoto())
                .centerCrop()
                .into(holder.photoRestaurant);
    }

    @Override
    public int getItemCount() {
        return mRealEstateList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView photoRestaurant;
        public TextView nameRealEstate;
        public TextView cityRealEstate;
        public TextView priceRealEstate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            photoRestaurant = itemView.findViewById(R.id.photo_real_estate_item);
            nameRealEstate = itemView.findViewById(R.id.name_real_estate_item);
            cityRealEstate = itemView.findViewById(R.id.city_real_estate_item);
            priceRealEstate = itemView.findViewById(R.id.price_real_estate_item);
        }

        public void displayRealEstate(RealEstate realEstate){

            nameRealEstate.setText(realEstate.getType());
            cityRealEstate.setText(realEstate.getAddress());
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
            String priceFormate = numberFormat.format(realEstate.getPrice());
            priceRealEstate.setText("$" + priceFormate);
        }
    }
}
