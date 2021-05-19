package com.codesroots.osamaomar.grz.presentationn.screens.feature.home.myorders.productsinsideorder;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.codesroots.osamaomar.grz.R;
import com.codesroots.osamaomar.grz.models.entities.Products;

import static com.codesroots.osamaomar.grz.models.entities.names.ORDER_ID;

public class ProductsInsidePrderFragment extends Fragment {

    private ProductsInsideOrderViewModel mViewModel;
    RecyclerView productsRecycle;
    int orderid;
    private Products productsData;
    private FrameLayout progress;
    private TextView notfound;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.products_insideorderfragment, container, false);

        orderid = getArguments().getInt(ORDER_ID, 0);
        productsRecycle = view.findViewById(R.id.allProducts);
        progress = view.findViewById(R.id.progress);

    //    productsRecycle.setAdapter(new AllProductsInsideOrderAdapter(getActivity(), order.getOrderdetails()));
        mViewModel = ViewModelProviders.of(this, getViewModelFactory()).get(ProductsInsideOrderViewModel.class);

        mViewModel.productsMutableLiveData.observe(this, products ->
        {
//                    productsData = productsUseCase;
//                    progress.setVisibility(View.GONE);
//                    if (productsUseCase.getProductsbycategory()!=null) {
//                        if (productsUseCase.getProductsbycategory().size() > 0) {
//                            productsRecycle.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//                            productsRecycle.setAdapter(new AllProductsAdapter(getActivity(), 0, productsUseCase.getProductsbycategory()));
//                        }
//                        else {
//                            notfound.setVisibility(View.GONE);
//                            changeSpane.setEnabled(false);
//                            filter.setEnabled(false);
//                        }
//                    }
//                    else {
//                        notfound.setVisibility(View.GONE);
//                        changeSpane.setEnabled(false);
//                        filter.setEnabled(false);
//                    }
        });

        mViewModel.throwableMutableLiveData.observe(this, throwable ->
        {
            progress.setVisibility(View.GONE);
            Toast.makeText(getActivity(), throwable.toString(), Toast.LENGTH_SHORT).show();
        });


        return view;
    }

    private ProductsInsideOrderViewModelFactory getViewModelFactory() {
        return new ProductsInsideOrderViewModelFactory(this.getActivity().getApplication(),orderid);
    }


}
