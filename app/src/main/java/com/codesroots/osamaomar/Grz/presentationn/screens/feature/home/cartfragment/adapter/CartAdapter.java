package com.codesroots.osamaomar.Grz.presentationn.screens.feature.home.cartfragment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codesroots.osamaomar.Grz.R;
import com.codesroots.osamaomar.Grz.models.entities.CartItems;
import com.codesroots.osamaomar.Grz.models.entities.OrderModel;
import com.codesroots.osamaomar.Grz.models.entities.Product;
import com.codesroots.osamaomar.Grz.models.entities.Products;
import com.codesroots.osamaomar.Grz.models.helper.AddorRemoveCallbacks;
import com.codesroots.osamaomar.Grz.models.helper.PreferenceHelper;
import com.codesroots.osamaomar.Grz.presentationn.screens.feature.home.productfragment.ProductsFragment;
import com.codesroots.osamaomar.Grz.presentationn.screens.feature.home.productfragment.ProductsViewModel;
import com.codesroots.osamaomar.Grz.presentationn.screens.feature.home.productfragment.adapters.AllProductsAdapter;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context context;
    private List<Product> dataBeans;
    public List<OrderModel.productSize> products = new ArrayList<>();

    public CartAdapter(Context mcontext, List<Product> productsbysubcats1) {
        context = mcontext;
        dataBeans = productsbysubcats1;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item_adapter, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        try {

            holder.name.setText(dataBeans.get(position).getName());
            holder.amount.setText(context.getText(R.string.remendier) + " " +
                    String.valueOf(dataBeans.get(position).getAmount()) + " " + context.getText(R.string.num));

            products.add(new OrderModel.productSize(dataBeans.get(position).getProductid()));
            if (dataBeans.get(position).isHasoffer()) {
                if (PreferenceHelper.getCurrencyValue() > 0)
                    holder.price.setText(dataBeans.get(position).getAfteroffer() * PreferenceHelper.getCurrencyValue() + " " + PreferenceHelper.getCurrency());
                else
                    holder.price.setText(dataBeans.get(position).getPrice() + " " + context.getText(R.string.realcoin));

                //  holder.price.setText(dataBeans.get(position).getCurrentPrice() + " " + context.getText(R.string.realcoin));
                products.get(position).setTotal(String.valueOf(Integer.valueOf(holder.products_count.getText().toString()) *
                        dataBeans.get(position).getAfteroffer()));
                products.get(position).setNotice("خصم بسبب العرض رقم " + dataBeans.get(position).getOfferid());
            } else {
                products.get(position).setTotal(String.valueOf(Integer.valueOf(holder.products_count.getText().toString()) *
                        Float.valueOf(dataBeans.get(position).getPrice())));

                if (PreferenceHelper.getCurrencyValue() > 0)
                    holder.price.setText(Float.valueOf(dataBeans.get(position).getPrice()) * PreferenceHelper.getCurrencyValue() + " " +
                            PreferenceHelper.getCurrency());
                else
                    holder.price.setText(dataBeans.get(position).getPrice() + " " + context.getText(R.string.realcoin));
            }

            holder.ratingBar.setRating(dataBeans.get(position).getRate());
            holder.rateCount.setText("(" + dataBeans.get(position).getRatecount() + ")");
            Glide.with(context.getApplicationContext())
                    .load(dataBeans.get(position).getPhoto()).placeholder(R.drawable.product).dontAnimate()
                    .into(holder.Image);
        } catch (Exception e) {
            Log.d("exception", e.getMessage());
        }

        holder.delete_item.setOnClickListener(v -> {
            PreferenceHelper.removeItemFromCart(dataBeans.get(position).getProductid());
            dataBeans.remove(position);
            ((AddorRemoveCallbacks) context).onRemoveProduct();
            notifyItemRemoved(position);
        });

        holder.quintityPlus.setOnClickListener(v ->
        {
            if (Integer.valueOf(holder.products_count.getText().toString()) + 1 <= dataBeans.get(position).getAmount()) {
                holder.products_count.setText(String.valueOf(Integer.valueOf(holder.products_count.getText().toString()) + 1));
                products.get(position).setAmount(Integer.valueOf(holder.products_count.getText().toString()));
                if (dataBeans.get(position).isHasoffer()) {
                    products.get(position).setTotal(String.valueOf(Integer.valueOf(holder.products_count.getText().toString()) *
                            dataBeans.get(position).getAfteroffer()));
                    products.get(position).setNotice("خصم بسبب العرض رقم " + dataBeans.get(position).getOfferid());
                } else
                    products.get(position).setTotal(String.valueOf(Integer.valueOf(holder.products_count.getText().toString()) *
                            Float.valueOf(dataBeans.get(position).getPrice())));

            } else
                Toast.makeText(context, context.getText(R.string.requestnotallow), Toast.LENGTH_SHORT).show();
        });


        holder.quintityMinus.setOnClickListener(v -> {
            int newValue = Integer.valueOf(holder.products_count.getText().toString()) - 1;
            if (newValue > 0) {
                holder.products_count.setText(String.valueOf(newValue));
                products.get(position).setAmount(Integer.valueOf(holder.products_count.getText().toString()));
                if (dataBeans.get(position).isHasoffer()) {
                    products.get(position).setTotal(String.valueOf(Integer.valueOf(holder.products_count.getText().toString()) *
                            dataBeans.get(position).getAfteroffer()));

                    products.get(position).setNotice("خصم بسبب العرض رقم " + dataBeans.get(position).getOfferid());
                } else
                    products.get(position).setTotal(String.valueOf(Integer.valueOf(holder.products_count.getText().toString()) *
                            Float.valueOf(dataBeans.get(position).getPrice())));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataBeans.size();
    }

    private float getPriceAfterDiscount(String Percentage, String oldPrice) {
        float offer = Float.valueOf(oldPrice) * Integer.valueOf(Percentage) / 100;
        return Float.valueOf(oldPrice) - offer;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final View mView;
        private ImageView Image, favorite, delete_item, quintityMinus, quintityPlus;
        private TextView name, rateCount, amount, price;
        private RatingBar ratingBar;
        private EditText products_count;

        ViewHolder(View view) {
            super(view);
            mView = view;
            Image = mView.findViewById(R.id.item_img);
            name = mView.findViewById(R.id.item_name);
            price = mView.findViewById(R.id.item_price);
            amount = mView.findViewById(R.id.quentity);
            rateCount = mView.findViewById(R.id.rate_count);
            ratingBar = mView.findViewById(R.id.rates);
            favorite = mView.findViewById(R.id.favorite);
            delete_item = mView.findViewById(R.id.delete_item);
            quintityMinus = mView.findViewById(R.id.quintityMinus);
            quintityPlus = mView.findViewById(R.id.quintityPlus);
            products_count = mView.findViewById(R.id.quintity_value);
        }
    }
}