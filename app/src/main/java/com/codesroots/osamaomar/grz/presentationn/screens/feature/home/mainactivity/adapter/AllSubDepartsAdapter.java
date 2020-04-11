package com.codesroots.osamaomar.grz.presentationn.screens.feature.home.mainactivity.adapter;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codesroots.osamaomar.grz.R;
import com.codesroots.osamaomar.grz.models.entities.Sidemenu;
import com.codesroots.osamaomar.grz.presentationn.screens.feature.home.productfragment.ProductsFragment;

import java.util.List;

import static com.codesroots.osamaomar.grz.models.entities.names.SUBCATES_NAME;
import static com.codesroots.osamaomar.grz.models.entities.names.SUB_CAT_ID;


public class AllSubDepartsAdapter extends RecyclerView.Adapter<AllSubDepartsAdapter.ViewHolder>  {

    private Context context;
    private  List<Sidemenu.CategoryBean.SubcatsBean> subcats;
    public AllSubDepartsAdapter(Context mcontext, List<Sidemenu.CategoryBean.SubcatsBean> subcats1) {
        context = mcontext;
        subcats = subcats1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sub_departs_item_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.text.setText(subcats.get(position).getName());
        Glide.with(context).load(subcats.get(position).getIcon()).into(holder.Image);
        holder.mView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt(SUB_CAT_ID,subcats.get(position).getId());
            bundle.putString(SUBCATES_NAME,subcats.get(position).getName());
            Fragment fragment  = new ProductsFragment();
            fragment.setArguments(bundle);
            ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment,fragment).addToBackStack(null).commit();
        });

    }

    @Override
    public int getItemCount() {
        return subcats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        private ImageView Image;
        private TextView text;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            Image = mView.findViewById(R.id.item_img);
            text = mView.findViewById(R.id.name);
        }
    }
}