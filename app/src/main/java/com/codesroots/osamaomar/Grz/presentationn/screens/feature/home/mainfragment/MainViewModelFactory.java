package com.codesroots.osamaomar.Grz.presentationn.screens.feature.home.mainfragment;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.codesroots.osamaomar.Grz.datalayer.apidata.ApiClient;
import com.codesroots.osamaomar.Grz.datalayer.apidata.ServerGateway;
import com.codesroots.osamaomar.Grz.datalayer.repositries.ProductAndCategries;
import com.codesroots.osamaomar.Grz.models.usecases.SubcatesWithProductsUseCase;
import com.codesroots.osamaomar.Grz.models.usecases.productsUseCase;
import com.codesroots.osamaomar.Grz.presentationn.screens.feature.home.productfragment.ProductsViewModel;
import com.codesroots.osamaomar.Grz.presentationn.screens.feature.home.subcategryfragment.SubCatesViewModel;

public class MainViewModelFactory implements ViewModelProvider.Factory {


    private Application application;
    public MainViewModelFactory(Application application1) {
        application = application1;

    }

    @SuppressWarnings("SingleStatementInBlock")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
         if (modelClass == ProductsDetailsViewModel.class)
        {
            return (T) new ProductsDetailsViewModel(getRepopsitry(),getUseCase());
        }
        else if (modelClass== SubCatesViewModel.class)
         {
             return (T) new SubCatesViewModel(getRepopsitry(),getProductUseCase());
         }
         else if (modelClass== ProductsViewModel.class)
         {
             return (T) new ProductsViewModel(getRepopsitry(),getUseCase());
         }
        throw new IllegalArgumentException("Invalid view model class type");
    }


    private ServerGateway getApiService() {
        return ApiClient.getClient().create(ServerGateway.class);
    }


    private productsUseCase getUseCase() {
        return new productsUseCase();
    }

    private SubcatesWithProductsUseCase getProductUseCase() {
        return new SubcatesWithProductsUseCase(getUseCase());
    }


    private ProductAndCategries getRepopsitry() {
        return new ProductAndCategries(getApiService());
    }

}
