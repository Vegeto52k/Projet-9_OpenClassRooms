package fr.vegeto52.realestatemanager.ui.mainActivity.listViewFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.vegeto52.realestatemanager.R;
import fr.vegeto52.realestatemanager.database.repository.ViewModelFactory;
import fr.vegeto52.realestatemanager.databinding.FragmentListViewBinding;
import fr.vegeto52.realestatemanager.model.Photo;
import fr.vegeto52.realestatemanager.model.RealEstate;
import fr.vegeto52.realestatemanager.ui.mainActivity.addFragment.AddFragment;

/**
 * Created by Vegeto52-PC on 15/11/2023.
 */
public class ListViewFragment extends Fragment {

    private FragmentListViewBinding mBinding;
    private RecyclerView mRecyclerView;
    private TextView mListViewEmpty;
    private ListViewViewModel mListViewViewModel;
    private List<RealEstate> mRealEstateList;
    private List<Photo> mPhotoList;
    private Toolbar mToolbar;
    private ImageButton mAddButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentListViewBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();

        mRecyclerView = view.findViewById(R.id.recyclerview_fragment_list_view);
        mToolbar = view.findViewById(R.id.list_view_fragment_toolbar);
        mAddButton = view.findViewById(R.id.list_view_fragment_add_button);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
    }



    private void initViewModel(){
        mListViewViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(requireContext())).get(ListViewViewModel.class);
        mListViewViewModel.getListViewMutableLiveData().observe(getViewLifecycleOwner(), new Observer<ListViewViewState>() {
            @Override
            public void onChanged(ListViewViewState listViewViewState) {
                mRealEstateList = listViewViewState.getRealEstateList();
                mPhotoList = listViewViewState.getPhotoList();
                initRecyclerView();
                initToolbar();
                mBinding.fragmentListViewEmpty.setVisibility(mRealEstateList.isEmpty() ? View.VISIBLE : View.GONE);
            }
        });
    }

    private void initToolbar(){
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new AddFragment();
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

    private void initRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        ListViewRealEstateAdapter listViewRealEstateAdapter = new ListViewRealEstateAdapter(mRealEstateList, mPhotoList);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(), layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(listViewRealEstateAdapter);
    }
}
