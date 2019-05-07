package com.codesroots.osamaomar.Grz.presentationn.screens.feature.home.mainfragment;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.codesroots.osamaomar.Grz.datalayer.repositries.ProductAndCategries;
import com.codesroots.osamaomar.Grz.models.entities.Product;
import com.codesroots.osamaomar.Grz.models.entities.mainData;
import com.codesroots.osamaomar.Grz.models.usecases.productsUseCase;
import java.util.List;
import io.reactivex.disposables.CompositeDisposable;


public class ProductsDetailsViewModel extends ViewModel {

    public MutableLiveData<mainData> mainViewMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Product> productMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<Product>> offerproductsMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> errormessage = new MutableLiveData<>();
    private ProductAndCategries productAndCategriesrepositry;
    private productsUseCase productsUseCase;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

     ProductsDetailsViewModel(ProductAndCategries productAndCategries, productsUseCase useCase) {
         productAndCategriesrepositry = productAndCategries;
         productsUseCase = useCase;
    }


    public void getData() {
        productsUseCase.retrieveHomeFragmentData(mCompositeDisposable,productAndCategriesrepositry,mainViewMutableLiveData,errormessage);
    }

    public void getOffersData() {
        productsUseCase.getOffersData(mCompositeDisposable,productAndCategriesrepositry,offerproductsMutableLiveData,errormessage);
    }


    public void getProductDetailsData(int productid,int userid) {
        productsUseCase.retrieveProductDetailsData(mCompositeDisposable,productAndCategriesrepositry,productMutableLiveData,errormessage,productid,userid);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.clear();
    }

//          addtocart.setOnClickListener(v -> {
//        if (userid > 0) {
//            if (PreferenceHelper.retriveCartItemsValue() != null) {
//                if (!PreferenceHelper.retriveCartItemsValue().contains(String.valueOf(productdetails.getProductsizes().get(productSizesAdapter.mSelectedItem).getId()))) {
//                    PreferenceHelper.addItemtoCart(productdetails.getProductsizes().get(productSizesAdapter.mSelectedItem).getId());
//                    ((AddorRemoveCallbacks) getActivity()).onAddProduct();
//                    Toast.makeText(getActivity(), getActivity().getText(R.string.addtocartsuccess), Toast.LENGTH_SHORT).show();
//                } else
//                    Toast.makeText(getActivity(), getActivity().getText(R.string.aleady_exists), Toast.LENGTH_SHORT).show();
//            } else {
//                PreferenceHelper.addItemtoCart(productdetails.getProductsizes().get(productSizesAdapter.mSelectedItem).getId());
//                ((AddorRemoveCallbacks) getActivity()).onAddProduct();
//                Toast.makeText(getActivity(), getActivity().getText(R.string.addtocartsuccess), Toast.LENGTH_SHORT).show();
//            }
//        } else
//            Toast.makeText(getActivity(), getActivity().getText(R.string.loginfirst), Toast.LENGTH_SHORT).show();
//    });

}
