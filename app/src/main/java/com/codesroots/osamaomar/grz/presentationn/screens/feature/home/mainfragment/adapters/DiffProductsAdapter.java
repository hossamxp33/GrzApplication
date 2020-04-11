package com.codesroots.osamaomar.grz.presentationn.screens.feature.home.mainfragment.adapters;

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
import com.codesroots.osamaomar.grz.models.entities.MainView;
import com.codesroots.osamaomar.grz.presentationn.screens.feature.home.productfragment.ProductsFragment;

import java.util.List;

import static com.codesroots.osamaomar.grz.models.entities.names.SUBCATES_NAME;
import static com.codesroots.osamaomar.grz.models.entities.names.SUB_CAT_ID;

public class DiffProductsAdapter extends RecyclerView.Adapter<DiffProductsAdapter.ViewHolder>  {

    private Context context;
    private List<MainView.SubcatsBeanX> subcatsBeans;
    public DiffProductsAdapter(Context mcontext, List<MainView.SubcatsBeanX> categories1) {
        context = mcontext;
        subcatsBeans = categories1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.diff_products_adapter_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder,final int position) {
        if (position==0)
        {
            ViewGroup.LayoutParams layoutParams = holder.Image.getLayoutParams();;
            layoutParams.height=(layoutParams.height*2)+15;
            layoutParams.width=layoutParams.width*2;
            holder.Image.setLayoutParams(layoutParams);
        }

        holder.name.setText(subcatsBeans.get(position).getName());
        Glide.with(context).load(subcatsBeans.get(position).getPhoto()).dontAnimate().placeholder(R.drawable.dept1).into(holder.Image);

        holder.mView.setOnClickListener(v ->
        {
            Bundle bundle = new Bundle();
            bundle.putInt(SUB_CAT_ID,subcatsBeans.get(position).getId());
            bundle.putString(SUBCATES_NAME,subcatsBeans.get(position).getName());
            Fragment fragment  = new ProductsFragment();
            fragment.setArguments(bundle);
            ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment,fragment).addToBackStack(null).commit();
        });
    }

    @Override
    public int getItemCount() {
        return subcatsBeans.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final View mView;
        private ImageView Image;
        private TextView name;

        ViewHolder(View view) {
            super(view);
            mView = view;
            Image = mView.findViewById(R.id.item_img);
            name = mView.findViewById(R.id.name);
        }
    }
}