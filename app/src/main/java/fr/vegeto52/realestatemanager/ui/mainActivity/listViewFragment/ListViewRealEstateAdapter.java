package fr.vegeto52.realestatemanager.ui.mainActivity.listViewFragment;

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

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import fr.vegeto52.realestatemanager.R;
import fr.vegeto52.realestatemanager.model.Photo;
import fr.vegeto52.realestatemanager.model.RealEstate;
import fr.vegeto52.realestatemanager.ui.mainActivity.detailsFragment.DetailsFragment;

/**
 * The ListViewRealEstateAdapter class is a RecyclerView adapter for displaying a list of RealEstate items.
 * It is used in the ListViewFragment to show a list of real estate properties.
 */
public class ListViewRealEstateAdapter extends RecyclerView.Adapter<ListViewRealEstateAdapter.ViewHolder> {

    // List of RealEstate items
    private static List<RealEstate> mRealEstateList;

    // List of Photo items
    private static List<Photo> mPhotoList;

    // Flag indicating whether the device is a tablet
    private static boolean mIsTablet;

    /**
     * Constructor for the ListViewRealEstateAdapter class.
     *
     * @param realEstateList List of RealEstate items to be displayed.
     * @param photoList      List of Photo items associated with RealEstate items.
     * @param isTablet       Flag indicating whether the device is a tablet.
     */
    public ListViewRealEstateAdapter(List<RealEstate> realEstateList, List<Photo> photoList, boolean isTablet) {
        mRealEstateList = realEstateList;
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
                .inflate(R.layout.item_fragment_list_view, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Called to display the data at the specified position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.displayRealEstate(mRealEstateList.get(position));

        // Set up click listener for item view to open DetailsFragment
        holder.itemView.setOnClickListener(view -> {
            Fragment fragment = new DetailsFragment();
            Bundle args = new Bundle();
            args.putLong("idRealEstate", holder.id);
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
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mRealEstateList.size();
    }

    /**
     * The ViewHolder class represents each item within the RecyclerView.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // UI elements for displaying RealEstate information
        public ImageView photoRealEstate;
        public TextView typeRealEstate;
        public TextView cityRealEstate;
        public TextView priceRealEstate;
        public ImageView iconSaleRealEstate;
        public long id;

        /**
         * Constructor for the ViewHolder class.
         *
         * @param itemView The view for each item within the RecyclerView.
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize UI elements
            photoRealEstate = itemView.findViewById(R.id.photo_real_estate_item);
            typeRealEstate = itemView.findViewById(R.id.type_real_estate_item);
            cityRealEstate = itemView.findViewById(R.id.city_real_estate_item);
            priceRealEstate = itemView.findViewById(R.id.price_real_estate_item);
            iconSaleRealEstate = itemView.findViewById(R.id.icon_sale_real_estate_item);
        }

        /**
         * Displays RealEstate information in the ViewHolder.
         *
         * @param realEstate The RealEstate object to be displayed.
         */
        public void displayRealEstate(RealEstate realEstate) {

            // Set the id
            id = realEstate.getId();

            // Set text for type, city, and price
            typeRealEstate.setText(!TextUtils.isEmpty(realEstate.getType()) ? realEstate.getType() : "Type not provided");
            cityRealEstate.setText(!TextUtils.isEmpty(realEstate.getAddress()) ? realEstate.getAddress() : "Address not provided");
            Double price = realEstate.getPrice();
            String priceText = (price != null) ? ("$" + NumberFormat.getNumberInstance(Locale.getDefault()).format(price)) : "Price not provided";
            priceRealEstate.setText(priceText);

            // Set the icon based on the status
            if (realEstate.isStatut()) {
                iconSaleRealEstate.setImageResource(R.drawable.baseline_cancel_24);
            } else {
                iconSaleRealEstate.setImageResource(R.drawable.baseline_check_circle_24);
            }

            // Load the first photo associated with the RealEstate
            Photo firstPhoto = getFirstPhotoForRealEstate(id);
            if (firstPhoto != null) {
                Glide.with(itemView.getContext())
                        .load(firstPhoto.getUriPhoto())
                        .centerCrop()
                        .into(photoRealEstate);
            }
        }

        /**
         * Returns the first photo associated with the given RealEstate ID.
         *
         * @param realEstateId The ID of the RealEstate.
         * @return The first Photo object associated with the RealEstate.
         */
        private Photo getFirstPhotoForRealEstate(long realEstateId) {
            for (Photo photo : mPhotoList) {
                if (photo.getRealEstateId() == realEstateId) {
                    return photo;
                }
            }
            return null;
        }
    }
}
