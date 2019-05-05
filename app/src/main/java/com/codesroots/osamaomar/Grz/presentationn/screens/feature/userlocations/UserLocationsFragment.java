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

import com.codesroots.osamaomar.Grz.R;
import com.codesroots.osamaomar.Grz.models.entities.OrderModel;
import com.codesroots.osamaomar.Grz.presentationn.screens.feature.getuserlocation.GetUserLocationActivity;
import com.codesroots.osamaomar.Grz.presentationn.screens.feature.userlocations.adapter.LocationsAdapter;

import static com.codesroots.osamaomar.Grz.models.entities.names.ORDER;

public class UserLocationsFragment extends Fragment {

    private UserLocationsViewModel mViewModel;
    private RecyclerView locations;
    private ImageView addLocation;
    public static UserLocationsFragment newInstance() {
        return new UserLocationsFragment();
    }
    OrderModel orderModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.user_locations_fragment, container, false);

        locations = view.findViewById(R.id.oldplaces);
        addLocation = view.findViewById(R.id.addlocation);
        orderModel = (OrderModel) getArguments().getSerializable(ORDER);

        addLocation.setOnClickListener(v -> {
            Intent  intent = new Intent(getActivity(), GetUserLocationActivity.class);
            getActivity().startActivityForResult(intent,115);
        });

        locations.setAdapter(new LocationsAdapter(getContext()));

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
