package com.codesroots.osamaomar.grz.presentationn.screens.feature.home.myorders;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codesroots.osamaomar.grz.R;
import com.codesroots.osamaomar.grz.databinding.MyOrdersFragmentBinding;
import com.codesroots.osamaomar.grz.models.helper.PreferenceHelper;
import com.codesroots.osamaomar.grz.presentationn.screens.feature.home.myorders.adapters.MyOrdersAdapter;

public class MyOrdersFragment extends Fragment {

    private MyOrdersViewModel mViewModel;
    private RecyclerView orders;
    private int userid = PreferenceHelper.getUserId();
    private FrameLayout loading;
    private TextView product_notfound;
    private MyOrdersAdapter myOrdersAdapter;
    private MyOrdersFragmentBinding myOrdersFragmentBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        myOrdersFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.my_orders_fragment, container, false);
        mViewModel = ViewModelProviders.of(this, getViewModelFactory()).get(MyOrdersViewModel.class);
        mViewModel.myOrdersMutableLiveData.observe(this, myOrders ->
        {
            myOrdersFragmentBinding.progress.setVisibility(View.GONE);
            if (myOrders.getData().size() > 0) {
                { myOrdersAdapter = new MyOrdersAdapter(getActivity(), myOrders.getData());
                    myOrdersFragmentBinding.setAdapter(myOrdersAdapter);
                }
            } else
                myOrdersFragmentBinding.productNotfound.setVisibility(View.VISIBLE);
        });

        mViewModel.throwableMutableLiveData.observe(this, throwable ->
                {
                    myOrdersFragmentBinding.progress.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                });

        return myOrdersFragmentBinding.getRoot();
    }

    private MyOrdersViewModelFactory getViewModelFactory() {
        return new MyOrdersViewModelFactory(getActivity().getApplication(), userid);
    }
}
