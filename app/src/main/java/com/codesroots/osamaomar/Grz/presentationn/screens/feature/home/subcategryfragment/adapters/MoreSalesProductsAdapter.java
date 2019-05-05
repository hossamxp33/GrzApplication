package com.codesroots.osamaomar.Grz.presentationn.screens.feature.home.subcategryfragment.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
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
import com.bumptech.glide.Glide;
import com.codesroots.osamaomar.Grz.R;
import com.codesroots.osamaomar.Grz.models.entities.Product;
import com.codesroots.osamaomar.Grz.presentationn.screens.feature.home.productdetailsfragment.ProductDetailsFragment;
import com.codesroots.osamaomar.Grz.presentationn.screens.feature.home.subcategryfragment.SubcategryFragment;
import com.codesroots.osamaomar.Grz.presentationn.screens.feature.rate.RateActivity;
import java.util.List;

import static com.codesroots.osamaomar.Grz.models.entities.names.PRODUCT_ID;

public class MoreSalesProductsAdapter extends RecyclerView.Adapter<MoreSalesProductsAdapter.ViewHolder> {

    private Context context;
    private List<Product> products;
    SubcategryFragment SubcategryFragment;
  //  int userid = PreferenceHelper.getUserId();
    int userid = 1;

    public MoreSalesProductsAdapter(Context mcontext, List<Product> productsbyrate,
                                    SubcategryFragment subcategryFragment1) {
        context = mcontext;
        products = productsbyrate;
        SubcategryFragment = subcategryFragment1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.horizental_product_item_adapter, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.quntity.setText(context.getText(R.string.remendier) + " " + String.valueOf(products.get(position).getAmount()) + " " + context.getText(R.string.num));
        holder.price.setText(products.get(position).getPrice() + " " + context.getText(R.string.realcoin));
        holder.ratecount.setText("(" + products.get(position).getRatecount() + ")");
        holder.ratingBar.setRating(products.get(position).getRate());
        holder.name.setText(products.get(position).getName());
        Glide.with(context).load(products.get(position).getPhoto()).placeholder(R.drawable.product).into(holder.Image);
        if (products.get(position).isFavoret())
            holder.favorite.setImageResource(R.drawable.favoried);
        else
            holder.favorite.setImageResource(R.drawable.like);

            holder.favorite.setOnClickListener(v -> {
                if (userid>0) {
                    if (products.get(position).isFavoret()) {
                        SubcategryFragment.onAddProductFav(products.get(position),userid);
                    }
                    ////////////// delete here
                    else {
                        SubcategryFragment.onRemoveProductFav(products.get(position), products.get(position).getFavid());
                    }
                }
                else
                    Snackbar.make(v,context.getText(R.string.loginfirst),Snackbar.LENGTH_LONG).show();
            });

        holder.ratingBar.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                Intent intent = new Intent(context, RateActivity.class);
                intent.putExtra(PRODUCT_ID, products.get(position).getProductid());
                context.startActivity(intent);
            }
            return true;
        });

        Fragment fragment = new ProductDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PRODUCT_ID, products.get(position).getProductid());
        fragment.setArguments(bundle);
        holder.mView.setOnClickListener(v -> ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().
                replace(R.id.fragment, fragment)
                .addToBackStack(null).commit());
    }


    @Override
    public int getItemCount() {
        return products.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final View mView;
        private ImageView Image, favorite;
        private TextView name, price, quntity, ratecount;
        private RatingBar ratingBar;

        ViewHolder(View view) {
            super(view);
            mView = view;
            Image = mView.findViewById(R.id.item_img);
            name = mView.findViewById(R.id.item_name);
            price = mView.findViewById(R.id.item_price);
            quntity = mView.findViewById(R.id.quentity);
            ratecount = mView.findViewById(R.id.rate_count);
            ratingBar = mView.findViewById(R.id.rates);
            favorite = mView.findViewById(R.id.favorite);
        }
    }
}