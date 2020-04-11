package com.codesroots.osamaomar.grz.presentationn.screens.feature.rate.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import com.codesroots.osamaomar.grz.R;
import com.codesroots.osamaomar.grz.models.entities.ProductRate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RateAdapter extends RecyclerView.Adapter<RateAdapter.ViewHolder>  {

    private Context context;
    private  List<ProductRate.DataBean> rates;
    private  String product_name;
    public RateAdapter(Context mcontext, List<ProductRate.DataBean> data) {
        context = mcontext;
        rates = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_rate, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder,final int position) {

        holder.comment.setText(rates.get(position).getFeedback());
        holder.user_name.setText(rates.get(position).getUser().getCustomer_email());
        holder.comment_date.setText(getdate(rates.get(position).getCreated_at()));
        holder.ratingBar.setRating(rates.get(position).getRated());
        holder.product_name.setText(product_name);
    }

    @Override
    public int getItemCount() {
        return rates.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final View mView;
        private RatingBar ratingBar;

        private TextView product_name,user_name,comment,comment_date;

        ViewHolder(View view) {
            super(view);
            mView = view;

            product_name = mView.findViewById(R.id.product_item);
            user_name = mView.findViewById(R.id.user_item);
            comment = mView.findViewById(R.id.comment_item);
            comment_date = mView.findViewById(R.id.date_item);
            ratingBar = mView.findViewById(R.id.rates);

        }
    }

    private String getdate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ");
        try {
            Date dateObj = sdf.parse(date);
            Log.d("newdatein", dateObj.getTime() + "");
            String timestamp = String.valueOf(dateObj.getTime());//  //Example -> in ms
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String dateString = formatter.format(new Date(Long.parseLong(timestamp)));
            return dateString;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}