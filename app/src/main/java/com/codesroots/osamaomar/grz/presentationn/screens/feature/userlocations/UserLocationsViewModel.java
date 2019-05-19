package com.codesroots.osamaomar.grz.presentationn.screens.feature.userlocations;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.codesroots.osamaomar.grz.datalayer.apidata.ServerGateway;
import com.codesroots.osamaomar.grz.models.entities.AddLocation;
import com.codesroots.osamaomar.grz.models.entities.UserLocations;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class UserLocationsViewModel extends ViewModel {

    public MutableLiveData<List<UserLocations.DataBean>> locations = new MutableLiveData<>();
    public MutableLiveData<Throwable> error = new MutableLiveData<>();
    public MutableLiveData<AddLocation> addLocationMutableLiveData = new MutableLiveData<>();
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private ServerGateway serverGateway;

    public UserLocationsViewModel(ServerGateway serverGateway) {
        this.serverGateway = serverGateway;
    }


    public void retrieveUserLocations(int userid)
    {
       mCompositeDisposable.add(serverGateway.retrieveUserLocations(userid).
               observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).
                subscribe(userLocations -> locations.postValue(userLocations.getData()),
                        throwable -> error.postValue(throwable)));
    }


    public void addUserLocation(int userid,String address,String country,String city,String notes)
    {
        mCompositeDisposable.add(serverGateway.addBillingAddress(userid,address,country,city,notes).
                observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).
                subscribe(addLocation -> addLocationMutableLiveData.postValue(addLocation),
                        throwable -> error.postValue(throwable)));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.clear();
    }
}
