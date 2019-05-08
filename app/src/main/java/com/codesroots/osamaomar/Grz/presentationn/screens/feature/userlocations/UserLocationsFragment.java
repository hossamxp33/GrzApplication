package com.codesroots.osamaomar.Grz.presentationn.screens.feature.userlocations;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codesroots.osamaomar.Grz.R;
import com.codesroots.osamaomar.Grz.models.entities.OrderModel;
import com.codesroots.osamaomar.Grz.models.entities.UserLocations;
import com.codesroots.osamaomar.Grz.models.helper.Locationclick;
import com.codesroots.osamaomar.Grz.models.helper.PreferenceHelper;
import com.codesroots.osamaomar.Grz.presentationn.screens.feature.getuserlocation.GetUserLocationActivity;
import com.codesroots.osamaomar.Grz.presentationn.screens.feature.home.mainfragment.MainViewModelFactory;
import com.codesroots.osamaomar.Grz.presentationn.screens.feature.userlocations.adapter.LocationsAdapter;

import static com.codesroots.osamaomar.Grz.models.entities.names.ORDER;

public class UserLocationsFragment extends Fragment implements Locationclick {

    private UserLocationsViewModel mViewModel;
    private RecyclerView locations;
    private ImageView addLocation;
    OrderModel orderModel;
    LocationsAdapter locationsAdapter;
    TextView notfound;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.user_locations_fragment, container, false);

        locations = view.findViewById(R.id.oldplaces);
        addLocation = view.findViewById(R.id.addlocation);
        notfound = view.findViewById(R.id.notfond);
        orderModel = (OrderModel) getArguments().getSerializable(ORDER);

        mViewModel = ViewModelProviders.of(this,getViewModelFactory()).get(UserLocationsViewModel.class);
        mViewModel.retrieveUserLocations(PreferenceHelper.getUserId());
        mViewModel.locations.observe(this,dataBeans ->
                {
                    if (dataBeans.size()>0) {
                        locationsAdapter = new LocationsAdapter(getContext(), dataBeans, this);
                        locations.setAdapter(locationsAdapter);
                    }
                    else
                        notfound.setVisibility(View.VISIBLE);
                });

        mViewModel.error.observe(this,throwable ->
                Toast.makeText(getActivity(),getText(R.string.error_in_data),Toast.LENGTH_SHORT).show()
                );
        addLocation.setOnClickListener(v -> {
            Intent  intent = new Intent(getActivity(), GetUserLocationActivity.class);
            getActivity().startActivityForResult(intent,115);
        });

        return view;
    }


    private MainViewModelFactory getViewModelFactory() {
        return new MainViewModelFactory(this.getActivity().getApplication());
    }

    @Override
    public void onlocationchoicw(UserLocations.DataBean location) {
        Toast.makeText(getActivity(),"saasd",Toast.LENGTH_SHORT).show();
    }
}
