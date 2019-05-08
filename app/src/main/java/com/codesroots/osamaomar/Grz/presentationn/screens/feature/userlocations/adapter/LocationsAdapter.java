package com.codesroots.osamaomar.Grz.presentationn.screens.feature.userlocations.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codesroots.osamaomar.Grz.R;
import com.codesroots.osamaomar.Grz.models.entities.Favoriets;
import com.codesroots.osamaomar.Grz.models.entities.UserLocations;
import com.codesroots.osamaomar.Grz.presentationn.screens.feature.home.favorite.FavoritesViewModel;
import com.codesroots.osamaomar.Grz.presentationn.screens.feature.home.productdetailsfragment.ProductDetailsFragment;
import com.codesroots.osamaomar.Grz.presentationn.screens.feature.rate.RateActivity;
import com.codesroots.osamaomar.Grz.presentationn.screens.feature.userlocations.UserLocationsFragment;

import java.util.List;

import static com.codesroots.osamaomar.Grz.models.entities.names.PRODUCT_ID;


public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.ViewHolder> {

    private Context context;
    private List<UserLocations.DataBean> places;
    private UserLocationsFragment userLocationsFragment;


    public LocationsAdapter(Context mcontext, List<UserLocations.DataBean> dataBeans,UserLocationsFragment fragment) {
        context = mcontext;
        places = dataBeans;
        userLocationsFragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.place_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.location.setText(places.get(position).getAddress());
        holder.mView.setOnClickListener(v -> userLocationsFragment.onlocationchoicw(places.get(position)));
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        private TextView location;

        ViewHolder(View view) {
            super(view);
            mView = view;
            location = mView.findViewById(R.id.location);
        }
    }
}