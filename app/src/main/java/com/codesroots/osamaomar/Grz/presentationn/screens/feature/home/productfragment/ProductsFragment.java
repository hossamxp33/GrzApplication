package com.codesroots.osamaomar.Grz.presentationn.screens.feature.home.productfragment;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.codesroots.osamaomar.Grz.R;
import com.codesroots.osamaomar.Grz.models.entities.Product;
import com.codesroots.osamaomar.Grz.models.helper.AddorRemoveFav;
import com.codesroots.osamaomar.Grz.presentationn.screens.feature.home.mainfragment.MainViewModelFactory;
import com.codesroots.osamaomar.Grz.presentationn.screens.feature.home.productfragment.adapters.AllProductsAdapter;

import java.util.List;

import static com.codesroots.osamaomar.Grz.models.entities.names.CAT_TYPE;
import static com.codesroots.osamaomar.Grz.models.entities.names.SUBCATES_NAME;
import static com.codesroots.osamaomar.Grz.models.entities.names.SUB_CAT_ID;

public class ProductsFragment extends Fragment implements AddorRemoveFav {

    private ProductsViewModel mViewModel;
    RecyclerView productsRecycle;
    ConstraintLayout filter_option;
    ImageView changeSpane, filter;
    boolean RecycleIsHorizental = true;
    int subCategry, userID, type;
    private List<Product> productsData;
    private FrameLayout progress;
    private TextView notfound, subcates_name, spillingfilter, pricefilter;
    private AllProductsAdapter AllProductsAdapter;
    private String title, name;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.products_fragment, container, false);
        initialize(view);
        //userID = PreferenceHelper.getUserId();
        userID = 1;

        mViewModel = ViewModelProviders.of(this, getViewModelFactory()).get(ProductsViewModel.class);
        mViewModel.getData(subCategry, userID, type);
        name = getArguments().getString("name");
        if (name != null)
            mViewModel.getSearchProductData(userID, "ar", name);
        else
            mViewModel.getData(subCategry, userID, type);


        mViewModel.productsMutableLiveData.observe(this, products ->
        {
            mViewModel.setResultData(products);
            productsData = products;
            progress.setVisibility(View.GONE);
            if (products.size() > 0) {
                productsRecycle.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                AllProductsAdapter = new AllProductsAdapter(getActivity(), 0, products, this);
                productsRecycle.setAdapter(AllProductsAdapter);
            } else {
                notfound.setVisibility(View.VISIBLE);
                changeSpane.setEnabled(false);
                filter.setEnabled(false);
            }
        });

        mViewModel.errorMessage.observe(this, throwable ->
        {
            progress.setVisibility(View.GONE);
            Toast.makeText(getActivity(), throwable, Toast.LENGTH_SHORT).show();
        });
        pricefilter.setOnClickListener(v ->
        {
            mViewModel.comparewithprice();
            filter_option.setVisibility(View.GONE);
            hideView(filter_option, 0);
        });
        spillingfilter.setOnClickListener(v ->
        {
            mViewModel.comparewithspilling();
            filter_option.setVisibility(View.GONE);
            hideView(filter_option, 0);
        });

        return view;
    }

    private void initialize(View view) {
        productsRecycle = view.findViewById(R.id.allProducts);
        changeSpane = view.findViewById(R.id.change_span);
        filter = view.findViewById(R.id.filter);
        filter_option = view.findViewById(R.id.filter_option);
        progress = view.findViewById(R.id.progress);
        spillingfilter = view.findViewById(R.id.spilingfilter);
        pricefilter = view.findViewById(R.id.pricefilter);
        notfound = view.findViewById(R.id.product_notfound);
        subcates_name = view.findViewById(R.id.subcates_name);
        subCategry = getArguments().getInt(SUB_CAT_ID, 0);
        type = getArguments().getInt(CAT_TYPE, 0);
        title = getArguments().getString(SUBCATES_NAME);

        String[] name_of_bookmarks = getResources().getStringArray(R.array.filters);
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), R.layout.simple_list_item, name_of_bookmarks);
        //  filter_option.setAdapter(arrayAdapter);
        //    filter_option.setOnItemClickListener((parent, view1, position, id) -> {});

        changeSpane.setOnClickListener(onClickListener);
        filter.setOnClickListener(onFilterClickListener);
        //   filter_option.setOnItemClickListener(AdapterView);
    }


    private MainViewModelFactory getViewModelFactory() {
        return new MainViewModelFactory(this.getActivity().getApplication());
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!RecycleIsHorizental) {
                productsRecycle.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                AllProductsAdapter = new AllProductsAdapter(getActivity(), 0, productsData, ProductsFragment.this);
                productsRecycle.setAdapter(AllProductsAdapter);
                RecycleIsHorizental = true;
            } else {
                productsRecycle.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                AllProductsAdapter = new AllProductsAdapter(getActivity(), 1, productsData, ProductsFragment.this);
                productsRecycle.setAdapter(AllProductsAdapter);
                RecycleIsHorizental = false;
            }
        }
    };

    private void hideView(final View view, int states) {
        Animation animation = null;
        if (states == 1)
            animation = AnimationUtils.loadAnimation(getActivity(), R.anim.animy);
        else
            animation = AnimationUtils.loadAnimation(getActivity(), R.anim.animyforimg);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }
        });
        view.startAnimation(animation);
    }

    private View.OnClickListener onFilterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (filter_option.getVisibility() == View.GONE) {
                filter_option.setVisibility(View.VISIBLE);
                hideView(filter_option, 1);
            } else {
                filter_option.setVisibility(View.GONE);
                hideView(filter_option, 0);
            }
        }
    };

    @Override
    public void onAddProductFav(Product product, int userid) {
        mViewModel.AddToFav(product.getProductid(), userid);
        mViewModel.addToFavMutableLiveData.observe(this, addToFavModel -> {
            productsData.get(productsData.indexOf(product)).setFavid(addToFavModel.getFavid());
            productsData.get(productsData.indexOf(product)).setFavoret(true);
            AllProductsAdapter.notifyItemChanged((productsData.indexOf(product)));
        });

    }

    @Override
    public void onRemoveProductFav(Product product, int favid) {

        mViewModel.DeleteFav(favid);
        mViewModel.deleteToFavMutableLiveData.observe(this, deletfav -> {
            if (deletfav.isSuccess()) {
                productsData.get(productsData.indexOf(product)).setFavid(0);
                productsData.get(productsData.indexOf(product)).setFavoret(false);
                AllProductsAdapter.notifyItemChanged((productsData.indexOf(product)));
            }
        });
    }
}
