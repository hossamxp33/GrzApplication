package com.codesroots.osamaomar.grz.presentationn.screens.feature.home.myorders;

import android.app.Application;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.codesroots.osamaomar.grz.datalayer.apidata.ApiClient;
import com.codesroots.osamaomar.grz.datalayer.apidata.ServerGateway;

public class MyOrdersViewModelFactory implements ViewModelProvider.Factory {

    private Application application;
    private int userid;

    public MyOrdersViewModelFactory(Application application1, int id) {
        application = application1;
        userid = id;
    }

    @SuppressWarnings("SingleStatementInBlock")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
         if (modelClass == MyOrdersViewModel.class)
        {
            return (T) new MyOrdersViewModel(getApiService(), userid);
        }
        throw new IllegalArgumentException("Invalid view model class type");
    }


    private ServerGateway getApiService() {
        return ApiClient.getClient().create(ServerGateway.class);
    }

}
