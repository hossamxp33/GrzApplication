package com.codesroots.osamaomar.grz.presentationn.screens.feature.home.offerfragment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codesroots.osamaomar.grz.R;
import com.codesroots.osamaomar.grz.models.entities.Product;
import com.codesroots.osamaomar.grz.models.helper.PreferenceHelper;
import com.codesroots.osamaomar.grz.presentationn.screens.feature.home.productdetailsfragment.ProductDetailsFragment;

import java.text.DecimalFormat;
import java.util.List;

import static com.codesroots.osamaomar.grz.models.entities.names.PRODUCT_ID;

public class AllOffersAdapter extends RecyclerView.Adapter<AllOffersAdapter.ViewHolder> {

    private Context context;
    private List<Product> offersData;
    private float priceafteroffer = 0;

    public AllOffersAdapter(Context mcontext, List<Product> offers) {
        context = mcontext;
        offersData = offers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offer_item_adapter, parent, false);

        return new ViewHolder(view);
    }


    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        if (offersData.get(position).getPhotos().size() > 0)
            Glide.with(context.getApplicationContext())
                    .load(offersData.get(position).getPhotos().get(0).getPhoto()).placeholder(R.drawable.product).dontAnimate()
                    .into(holder.Image);
        holder.ratingBar.setRating(offersData.get(position).getRate());
        holder.rateCount.setText("(" + offersData.get(position).getRatecount() + ")");
        holder.name.setText(offersData.get(position).getName());
        holder.discount.setText(offersData.get(position).getDiscountpercentage() + " " + "%");


        if (PreferenceHelper.getCurrencyValue() > 0) {
            holder.oldprice.setText(offersData.get(position).getPricewithoutcoin() * PreferenceHelper.getCurrencyValue() + " " + PreferenceHelper.getCurrency());
            holder.price.setText((new DecimalFormat("##.##").format(offersData.get(position).getAfteroffer() * PreferenceHelper.getCurrencyValue())
                    + " " +PreferenceHelper.getCurrency()));
        } else {
            holder.oldprice.setText(offersData.get(position).getPricewithoutcoin() + " " + context.getText(R.string.realcoin));
            holder.price.setText((new DecimalFormat("##.##").format(offersData.get(position).getAfteroffer() ))+ " " +
                    context.getText(R.string.realcoin));
        }
        Fragment fragment = new ProductDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PRODUCT_ID, offersData.get(position).getProductid());

        fragment.setArguments(bundle);
        holder.mView.setOnClickListener(v -> ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().
                replace(R.id.fragment, fragment)
                .addToBackStack(null).commit());

//        holder.ratingBar.setOnTouchListener((v, event) -> {
//            if (event.getAction() == MotionEvent.ACTION_UP) {
//                Intent intent = new Intent(context, RateActivity.class);
//                intent.putExtra(PRODUCT_ID,offersData.get(position).getProductid());
//                context.startActivity(intent);
//            }
//            return true;
//        });

    }

    @Override
    public int getItemCount() {
        return offersData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final View mView;
        private ImageView Image;
        private TextView name, rateCount, amount, price, oldprice, discount;
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
            discount = mView.findViewById(R.id.discount);
            oldprice = mView.findViewById(R.id.old_price);
            oldprice.setPaintFlags(oldprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
}