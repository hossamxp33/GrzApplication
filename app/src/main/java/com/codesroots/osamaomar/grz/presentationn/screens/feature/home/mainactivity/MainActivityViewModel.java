package com.codesroots.osamaomar.grz.presentationn.screens.feature.home.mainactivity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.codesroots.osamaomar.grz.datalayer.apidata.ServerGateway;
import com.codesroots.osamaomar.grz.datalayer.localdata.product.entities.ProductDB;
import com.codesroots.osamaomar.grz.datalayer.repositries.ProductAndCategries;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;


class MainActivityViewModel extends ViewModel {


    public MutableLiveData<Integer> cartItemsCount = new MutableLiveData<>();
    public MutableLiveData<Throwable> throwableMutableLiveData = new MutableLiveData<>();
    private ServerGateway serverGateway;
    private ProductAndCategries productAndCategries;


     MainActivityViewModel(ServerGateway serverGateway1, ProductAndCategries repositry) {
        serverGateway = serverGateway1;
         productAndCategries = repositry;

     }




    public void retrieveCount()
    {
        productAndCategries.getAllProduct().subscribe(new SingleObserver<List<ProductDB>>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onSuccess(List<ProductDB> integers) {
                cartItemsCount.postValue(integers.size());
            }

            @Override
            public void onError(Throwable e) {
            }
        });
    }


}
