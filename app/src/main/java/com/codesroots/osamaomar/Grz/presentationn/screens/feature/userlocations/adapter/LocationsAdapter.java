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
import com.codesroots.osamaomar.Grz.presentationn.screens.feature.home.favorite.FavoritesViewModel;
import com.codesroots.osamaomar.Grz.presentationn.screens.feature.home.productdetailsfragment.ProductDetailsFragment;
import com.codesroots.osamaomar.Grz.presentationn.screens.feature.rate.RateActivity;

import java.util.List;

import static com.codesroots.osamaomar.Grz.models.entities.names.PRODUCT_ID;


public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.ViewHolder> {

    private Context context;
    private List<Favoriets.DataBean> productsbysubcats;
    FavoritesViewModel viewModel;

    public LocationsAdapter(Context mcontext) {
        context = mcontext;

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

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        private ImageView Image, favorite;
        private TextView name, rateCount, amount, price;
        private RatingBar ratingBar;

        ViewHolder(View view) {
            super(view);
            mView = view;
            Image = mView.findViewById(R.id.item_img);
            name = mView.findViewById(R.id.item_name);
            price = mView.findViewById(R.id.item_price);
            amount = mView.findViewById(R.id.quentity);
            rateCount = mView.findViewById(R.id.rate_count);
            ratingBar = mView.findViewById(R.id.rates);
            favorite = mView.findViewById(R.id.del_favorite);
        }
    }
}