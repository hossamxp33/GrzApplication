package com.codesroots.osamaomar.Grz.presentationn.screens.feature.home.productfragment;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.codesroots.osamaomar.Grz.datalayer.apidata.ServerGateway;
import com.codesroots.osamaomar.Grz.datalayer.repositries.ProductAndCategries;
import com.codesroots.osamaomar.Grz.models.entities.AddToFavModel;
import com.codesroots.osamaomar.Grz.models.entities.DefaultAdd;
import com.codesroots.osamaomar.Grz.models.entities.Product;
import com.codesroots.osamaomar.Grz.models.usecases.productsUseCase;
import java.util.Collections;
import java.util.List;
import io.reactivex.disposables.CompositeDisposable;

public class ProductsViewModel extends ViewModel {

    public MutableLiveData<List<Product>> productsMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<AddToFavModel> addToFavMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<DefaultAdd> deleteToFavMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> errorMessage = new MutableLiveData<>();
    public MutableLiveData<String> throwablefav = new MutableLiveData<>();
    private ServerGateway serverGateway;
    private int subcattegry_id, userid,type;
    private  List<Product> resultData;
    public int current_item = 0;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private productsUseCase productsUseCase;
    private ProductAndCategries productAndCategries;

    public List<Product> getResultData() {
        return resultData;
    }

    public void setResultData(List<Product> resultData) {
        this.resultData = resultData;
    }

    public ProductsViewModel(ProductAndCategries repositry, productsUseCase useCase ) {
       productAndCategries = repositry;
        productsUseCase = useCase;
    }

    public void getData (int id)
    {
        productsUseCase.retrieveProductsData(mCompositeDisposable,productAndCategries,productsMutableLiveData,
                errorMessage,id,resultData);
    }

    public void getSearchProductData (String type1,String key)
    {
        productsUseCase.retrieveSearchProductsData
                (mCompositeDisposable,productAndCategries,productsMutableLiveData,errorMessage,key,type1,resultData);
    }


    public  void AddToFav (int productid,int user)
    {
        productsUseCase.addFavToProduc(mCompositeDisposable,productAndCategries,productid,user,addToFavMutableLiveData,throwablefav);
    }

    public  void DeleteFav (int favid)
    {
        productsUseCase.deleteFavToProduc(mCompositeDisposable,productAndCategries,favid,deleteToFavMutableLiveData,throwablefav);
    }


    public  void comparewithprice()
    {
        Collections.sort(resultData, (o1, o2) -> {
            return Float.valueOf(o1.getPrice()).compareTo(Float.valueOf(o2.getPrice()));
        });
        productsMutableLiveData.postValue(resultData);
    }

    public  void comparewithspilling()
    {
        Collections.sort(resultData, (o1, o2) -> {
            return o1.getName().compareTo(o2.getName());
        });
        productsMutableLiveData.postValue(resultData);
    }


}
