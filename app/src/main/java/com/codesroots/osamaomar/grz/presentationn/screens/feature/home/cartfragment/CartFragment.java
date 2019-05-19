package com.codesroots.osamaomar.grz.presentationn.screens.feature.home.cartfragment;

import android.arch.lifecycle.ViewModelProvider;
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
import android.widget.Toast;

import com.codesroots.osamaomar.grz.R;
import com.codesroots.osamaomar.grz.datalayer.localdata.product.entities.ProductDB;
import com.codesroots.osamaomar.grz.models.entities.OrderModel;
import com.codesroots.osamaomar.grz.models.entities.Product;
import com.codesroots.osamaomar.grz.models.helper.AddorRemoveCallbacks;
import com.codesroots.osamaomar.grz.models.helper.AddorRemoveToCartCallbacks;
import com.codesroots.osamaomar.grz.models.helper.PreferenceHelper;
import com.codesroots.osamaomar.grz.presentationn.screens.feature.home.cartfragment.adapter.CartAdapter;
import com.codesroots.osamaomar.grz.presentationn.screens.feature.home.mainfragment.MainViewModelFactory;
import com.codesroots.osamaomar.grz.presentationn.screens.feature.userlocations.UserLocationsFragment;

import java.util.ArrayList;
import java.util.List;

import static com.codesroots.osamaomar.grz.models.entities.names.ORDER;

public class CartFragment extends Fragment implements AddorRemoveToCartCallbacks {

    private static final int REQUEST_CODE_LOCATION = 117;
    RecyclerView cartsRecycle;
    private TextView sale;
    private CartAdapter cartAdapter;
    private FrameLayout progress;
    private ArrayList product_ids = new ArrayList<>();
    OrderModel orderModel = new OrderModel();
    List<Product> products = new ArrayList<>();
    List<ProductDB> productsDbs = new ArrayList<>();
    CartViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cart_fragment, container, false);
        initialize(view);
        mViewModel = ViewModelProviders.of(this, getViewModelFactory()).get(CartViewModel.class);
        mViewModel.cartItemsMutableLiveData.observe(this, dataBeans -> {
            {
                progress.setVisibility(View.GONE);
                products = dataBeans;
                cartAdapter = new CartAdapter(getActivity(), products, CartFragment.this);
                cartsRecycle.setAdapter(cartAdapter);
            }
        });

        mViewModel.noItemsFound.observe(this, aBoolean -> {
            progress.setVisibility(View.GONE);
            Snackbar.make(view, getText(R.string.noitemsincart), Snackbar.LENGTH_SHORT).show();
        });

        mViewModel.listMutableLiveData.observe(this,productDBS -> productsDbs=productDBS);
        mViewModel.throwableMutableLiveData.observe(this, throwable ->
        {
            progress.setVisibility(View.GONE);
            Toast.makeText(getActivity(), throwable.toString(), Toast.LENGTH_SHORT).show();
        });

        sale.setOnClickListener(v -> {
            Fragment fragment = new UserLocationsFragment();
            Bundle bundle = new Bundle();
            orderModel.setOrderdetails(cartAdapter.products);
            for (int i=0;i<cartAdapter.products.size();i++)
            {
                cartAdapter.products.get(i).setColor(productsDbs.get(i).getProductcolor_id());
                cartAdapter.products.get(i).setSize(productsDbs.get(i).getProductsize_id());
            }

            bundle.putSerializable(ORDER, orderModel);
            fragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).addToBackStack(null).commit();
        });
        return view;
    }

    private void initialize(View view) {
        cartsRecycle = view.findViewById(R.id.cart_Rec);
        sale = view.findViewById(R.id.sale);
        progress = view.findViewById(R.id.progress);

    }

    private ViewModelProvider.Factory getViewModelFactory() {
        return new MainViewModelFactory(getActivity().getApplication());
    }

    @Override
    public void onAddProduct(int pid, int cid, int sid) {
    }

    @Override
    public void onRemoveProduct(int pid, int position) {
        mViewModel.deleteItem(pid);
        products.remove(position);
        productsDbs.remove(position);
        cartAdapter.notifyDataSetChanged();
        ((AddorRemoveCallbacks) getActivity()).onRemoveProduct();
    }

    @Override
    public void onClearCart() {

    }
}
