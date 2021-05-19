package com.codesroots.osamaomar.grz.presentationn.screens.feature.home.cartfragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.codesroots.osamaomar.grz.models.entities.names.ORDER;

public class CartFragment extends Fragment implements AddorRemoveToCartCallbacks {

    private static final int REQUEST_CODE_LOCATION = 117;
    RecyclerView cartsRecycle;
    private TextView sale,totalvalue;
    private CartAdapter cartAdapter;
    private FrameLayout progress;
    private ArrayList product_ids = new ArrayList<>();
    OrderModel orderModel = new OrderModel();
    List<Product> products = new ArrayList<>();
    List<ProductDB> productsDbs = new ArrayList<>();
    CartViewModel mViewModel;
    double tot_price=0;
    String curentCurrency;
    public List<OrderModel.productSize> productsSizes = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cart_fragment, container, false);
        initialize(view);
        productsSizes.clear();
        mViewModel = ViewModelProviders.of(this, getViewModelFactory()).get(CartViewModel.class);
        mViewModel.cartItemsMutableLiveData.observe(this, dataBeans -> {
            {
                progress.setVisibility(View.GONE);
                products = dataBeans;
                cartAdapter = new CartAdapter(getActivity(), products, CartFragment.this);
                cartsRecycle.setAdapter(cartAdapter);
                tot_price = 0;
                    for (int i = 0; i < products.size(); i++) {
                        productsSizes.add(new OrderModel.productSize(products.get(i).getProductid()));
                        productsSizes.get(i).setTotal(String.valueOf(products.get(i).getPricewithoutcoin()  * productsDbs.get(i).getProduct_count()));

                        if (products.get(i).isHasoffer())
                            productsSizes.get(i).setNotice("خصم بسبب العرض رقم " + products.get(i).getOfferid());

                        productsSizes.get(i).setOriginalTotal(products.get(i).getPricewithoutcoin());
                        productsSizes.get(i).setColor(productsDbs.get(i).getProductcolor_id());
                        productsSizes.get(i).setSize(productsDbs.get(i).getProductsize_id());

                            this.tot_price += products.get(i).getPricewithoutcoin()  * productsDbs.get(i).getProduct_count();



                    }
                orderModel.setOrderdetails(productsSizes);

                totalvalue.setText(new DecimalFormat("##.##").
                        format(tot_price)+" "+ PreferenceHelper.getCurrency());
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
            if (PreferenceHelper.getUserId()>0) {

                Fragment fragment = new UserLocationsFragment();
                Bundle bundle = new Bundle();
                if (cartAdapter!=null) {
                  //  orderModel.setOrderdetails(cartAdapter.products);
                    Log.d("size",productsSizes.size()+"");
                    Log.d("sizeproductsDbs",productsDbs.size()+"");

                    Log.d("orderModel",orderModel.getOrder_gtotal()+"");


                    bundle.putSerializable(ORDER, orderModel);
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).addToBackStack(null).commit();
                }
            }
            else
                Toast.makeText(getContext(),getText(R.string.loginfirst),Toast.LENGTH_SHORT).show();
        });


        return view;
    }

    private void initialize(View view) {
        cartsRecycle = view.findViewById(R.id.cart_Rec);
        sale = view.findViewById(R.id.sale);
        progress = view.findViewById(R.id.progress);
        totalvalue = view.findViewById(R.id.totalvalue);
        curentCurrency =getContext().getText(R.string.realcoin).toString();
    }

    private ViewModelProvider.Factory getViewModelFactory() {
        return new MainViewModelFactory(getActivity().getApplication());
    }

    @Override
    public void onAddProduct(int pid, int cid, int sid, String colorname, String sizename,int product_count) {

    }

    @Override
    public void onRemoveProduct(int pid, int position) {
        mViewModel.deleteItem(pid);
        products.remove(position);
        productsDbs.remove(position);
        productsSizes.remove(position);
        cartAdapter.notifyDataSetChanged();
        ((AddorRemoveCallbacks) getActivity()).onRemoveProduct();
    }

    @Override
    public void onChangeCart() {
        tot_price = 0;
        for (int i = 0; i < orderModel.getOrderdetails().size(); i++)
            tot_price += orderModel.getOrderdetails().get(i).getOriginalTotal();

        if (PreferenceHelper.getCurrency()!=null)
            curentCurrency = PreferenceHelper.getCurrency();
        totalvalue.setText(new DecimalFormat("##.##").
                format(tot_price)+" "+curentCurrency);
    }
}
