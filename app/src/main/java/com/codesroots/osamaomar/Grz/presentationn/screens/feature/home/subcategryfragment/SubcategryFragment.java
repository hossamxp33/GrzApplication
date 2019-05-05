package com.codesroots.osamaomar.Grz.presentationn.screens.feature.home.subcategryfragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.codesroots.osamaomar.Grz.R;
import com.codesroots.osamaomar.Grz.models.entities.Product;
import com.codesroots.osamaomar.Grz.models.entities.SubCategriesWithProducts;
import com.codesroots.osamaomar.Grz.models.helper.AddorRemoveFav;
import com.codesroots.osamaomar.Grz.models.helper.PreferenceHelper;
import com.codesroots.osamaomar.Grz.presentationn.screens.feature.home.mainactivity.MainActivity;
import com.codesroots.osamaomar.Grz.presentationn.screens.feature.home.mainfragment.MainViewModelFactory;
import com.codesroots.osamaomar.Grz.presentationn.screens.feature.home.productfragment.ProductsViewModelFactory;
import com.codesroots.osamaomar.Grz.presentationn.screens.feature.home.subcategryfragment.adapters.MoreSalesProductsAdapter;
import com.codesroots.osamaomar.Grz.presentationn.screens.feature.home.subcategryfragment.adapters.SubCatsAdapter;

import java.util.List;

import static com.codesroots.osamaomar.Grz.models.entities.names.CAT_ID;
import static com.codesroots.osamaomar.Grz.models.entities.names.CAT_NAME;

public class SubcategryFragment extends Fragment implements AddorRemoveFav {

    private SubCatesViewModel mViewModel;
    RecyclerView subCates, MoreSaleProducts;
    private int catid, userid = PreferenceHelper.getUserId();
    private TextView product_notfound, text;
    private FrameLayout progress;
    private MoreSalesProductsAdapter moreSalesProductsAdapter;
    private String categry_name;
    private List<Product> productsData;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.subcategry_fragment, container, false);
        subCates = view.findViewById(R.id.subcategry_Rec);
        product_notfound = view.findViewById(R.id.product_notfound);
        MoreSaleProducts = view.findViewById(R.id.product_Rec);
        progress = view.findViewById(R.id.progress);
        text = view.findViewById(R.id.text);
        catid = getArguments().getInt(CAT_ID, 0);
        categry_name = getArguments().getString(CAT_NAME);
        mViewModel = ViewModelProviders.of(this, getViewModelFactory()).get(SubCatesViewModel.class);
        mViewModel.getData(catid,1);// TODO: 24/04/2019  
        mViewModel.subCategriesWithProductsMutableLiveData.observe(this, subCategriesWithProducts ->
        {
            text.setVisibility(View.VISIBLE);
            progress.setVisibility(View.GONE);
            subCates.setAdapter(new SubCatsAdapter(getActivity(), subCategriesWithProducts.getData()));
            if (subCategriesWithProducts.getProducts().size() > 0) {
                moreSalesProductsAdapter = new MoreSalesProductsAdapter(getActivity(), subCategriesWithProducts.getProducts(), this);
                productsData = subCategriesWithProducts.getProducts();
                MoreSaleProducts.setAdapter(moreSalesProductsAdapter);
            } else
                product_notfound.setVisibility(View.VISIBLE);
        });

        mViewModel.throwableMutableLiveData.observe(this, throwable ->
        {
            progress.setVisibility(View.GONE);
            Snackbar.make(view, throwable.toString(), Snackbar.LENGTH_SHORT).show();
        });
        return view;
    }

    private MainViewModelFactory getViewModelFactory() {
        return new MainViewModelFactory(this.getActivity().getApplication());

    }

    @Override
    public void onAddProductFav(Product product, int userid) {
        mViewModel.AddToFav(product.getProductid(),userid);
        mViewModel.addToFavMutableLiveData.observe(this, addToFavModel -> {
            productsData.get(productsData.indexOf(product)).setFavid(addToFavModel.getFavid());
            productsData.get(productsData.indexOf(product)).setFavoret(true);
            moreSalesProductsAdapter.notifyItemChanged((productsData.indexOf(product)));
        });
    }

    @Override
    public void onRemoveProductFav(Product product, int favid) {
        mViewModel.DeleteFav(favid);
        mViewModel.deleteFav.observe(this, deletfav -> {
            if (deletfav.isSuccess())
            {
                productsData.get(productsData.indexOf(product)).setFavid(0);
                productsData.get(productsData.indexOf(product)).setFavoret(false);
                moreSalesProductsAdapter.notifyItemChanged((productsData.indexOf(product)));
            }
        });
    }
}
