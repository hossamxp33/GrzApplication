package com.codesroots.osamaomar.Grz.models.usecases;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;

import com.codesroots.osamaomar.Grz.datalayer.repositries.ProductAndCategries;
import com.codesroots.osamaomar.Grz.models.entities.AddToFavModel;
import com.codesroots.osamaomar.Grz.models.entities.DefaultAdd;
import com.codesroots.osamaomar.Grz.models.entities.MainView;
import com.codesroots.osamaomar.Grz.models.entities.ProductDetails;
import com.codesroots.osamaomar.Grz.models.entities.Products;
import com.codesroots.osamaomar.Grz.models.entities.mainData;
import com.codesroots.osamaomar.Grz.models.entities.Product;
import com.codesroots.osamaomar.Grz.models.entities.offers;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class productsUseCase {


    @SuppressLint("CheckResult")
    public void retrieveHomeFragmentData(CompositeDisposable mCompositeDisposable,
                                         ProductAndCategries productAndCategries, MutableLiveData<mainData> data,
                                         MutableLiveData<String> errormessage) {
        productAndCategries.retrieveHomeFragmentData().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(mainView ->
                this.postDataResponse(mainView, data), throwable -> postError(throwable, errormessage));
    }


    @SuppressLint("CheckResult")
    public void retrieveProductDetailsData(CompositeDisposable mCompositeDisposable,
                                           ProductAndCategries productAndCategries, MutableLiveData<Product> data,
                                           MutableLiveData<String> errormessage, int productid) {

        productAndCategries.retrieveDetailsObservable(productid).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(mainView ->
                this.postProductDetailsData(mainView, data), throwable -> postError(throwable, errormessage));
    }


    @SuppressLint("CheckResult")
    public void retrieveProductsData(CompositeDisposable mCompositeDisposable,
                                     ProductAndCategries productAndCategries, MutableLiveData<List<Product>> data,
                                     MutableLiveData<String> errormessage, int catid, List<Product> resultData) {

        productAndCategries.retrieveProductsData(catid).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(mainView ->
                this.postProducsData(mainView, data, resultData), throwable -> postError(throwable, errormessage));
    }


    @SuppressLint("CheckResult")
    public void retrieveSearchProductsData(CompositeDisposable mCompositeDisposable,
                                           ProductAndCategries productAndCategries, MutableLiveData<List<Product>> data,
                                           MutableLiveData<String> errormessage, String searchkey, String type, List<Product> resultData) {

        productAndCategries.retrieveSearchProductsData(searchkey, type).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(mainView ->
                this.postProducsData(mainView, data, resultData), throwable ->
                postError(throwable, errormessage));
    }

    private void postProducsData(Products products, MutableLiveData<List<Product>> data, List<Product> resultData) {
        data.postValue(reshapProducts(products.getProductsbycategory()));
    }

    private void postDataResponse(MainView mainViewData, MutableLiveData<mainData> data) {

        mainData mainData = new mainData();
        mainData.setSlider(mainViewData.getSliders());
        mainData.setCategories(mainViewData.getCategory());
        mainData.setProducts(reshapProducts(mainViewData.getProductsbyrate()));
        data.postValue(mainData);
    }

    private void postProductDetailsData(ProductDetails mainViewData, MutableLiveData<Product> data) {
        data.postValue(reshapProducts(mainViewData.getProductdetails()).get(0));
    }

    public void getOffersData(CompositeDisposable mCompositeDisposable,
                              ProductAndCategries productAndCategries, MutableLiveData<List<Product>> data,
                              MutableLiveData<String> errormessag) {

        mCompositeDisposable.add(
                productAndCategries.retrieveOffer()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(offers -> this.postOffersData(offers, data),
                                throwable -> this.postError(throwable, errormessag)));
    }

    private void postOffersData(offers productRates, MutableLiveData<List<Product>> offers) {
        List<ProductDetails.product> products = new ArrayList<>();
        for (int i = 0; i < productRates.getData().size(); i++) {
            if (productRates.getData().get(i).getProduct() != null)
                products.add((productRates.getData().get(i).getProduct()));
        }
        offers.postValue(reshapProducts(products));
    }


    public List<Product> reshapProducts(List<ProductDetails.product> productsbyrate) {

        List<Product> products = new ArrayList<>();
        for (int i = 0; i < productsbyrate.size(); i++) {
            Product product = new Product();
            product.setProductid(productsbyrate.get(i).getId());
            product.setAmount(productsbyrate.get(i).getAmount());
            product.setPrice(productsbyrate.get(i).getCurrentPrice());
            product.setSizes(productsbyrate.get(i).getProductsizes());
            product.setColores(productsbyrate.get(i).getProduct_colors()
            );

            product.setName(productsbyrate.get(i).getName());
            if (productsbyrate.get(i).getTotal_rating() != null) {
                calcaulateRate(productsbyrate.get(i).getTotal_rating(), product);
            } else {
                product.setRate(0);
                product.setRatecount(0);
            }
            if (productsbyrate.get(i).getProductphotos() != null) {
                if (productsbyrate.get(i).getProductphotos().size() > 0) {
                    product.setPhotos(productsbyrate.get(i).getProductphotos());
                    product.setPhoto(productsbyrate.get(i).getProductphotos().get(0).getPhoto());
                }
            }
            if (productsbyrate.get(i).getDescription() != null)
                product.setDescription(productsbyrate.get(i).getDescription());
            else
                product.setDescription("غير متوفر");

            if (productsbyrate.get(i).getFavourites() != null) {
                if (productsbyrate.get(i).getFavourites().size() > 0) {
                    product.setFavoret(true);
                    product.setFavid(productsbyrate.get(i).getFavourites().get(0).getId());
                    product.setOfferpercentage(Float.valueOf(productsbyrate.get(i).getOffers().get(0).getPercentage()));
                }
            }

            if (productsbyrate.get(i).getOffers() != null) {
                if (productsbyrate.get(i).getOffers().size() > 0) {
                    product.setOfferid(productsbyrate.get(i).getId());
                    product.setAfteroffer(Float.valueOf(productsbyrate.get(i).getCurrentPrice()) -
                            Float.valueOf(productsbyrate.get(i).getCurrentPrice()) *
                                    Integer.valueOf(productsbyrate.get(i).getOffers().get(0).getPercentage()) / 100);
                    product.setDiscountpercentage(Integer.valueOf(productsbyrate.get(i).getOffers().get(0).getPercentage()));
                }
            }
            products.add(product);
        }
        return products;
    }

    private void calcaulateRate(List<ProductDetails.product.TotalRatingBean> total_rating, Product product) {
        if (total_rating.size() > 0) {
            product.setRate(total_rating.get(0).getStars() / total_rating.get(0).getCount());
            product.setRatecount(total_rating.get(0).getCount());
        } else {
            product.setRate(0);
            product.setRatecount(0);
        }
    }

    private void postError(Throwable throwable, MutableLiveData<String> errormessage) {
        errormessage.postValue(throwable.toString());
    }

    @SuppressLint("CheckResult")
    public void addFavToProduc(CompositeDisposable compositeDisposable, ProductAndCategries productAndCategries,
                               int productid, int uderid,
                               MutableLiveData<AddToFavModel> add, MutableLiveData<String> error) {
        productAndCategries.addToFav(uderid, productid).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(mainView ->
                this.postAddToFav(mainView, add), throwable -> postError(throwable, error));
    }


    @SuppressLint("CheckResult")
    public void deleteFavToProduc(CompositeDisposable compositeDisposable, ProductAndCategries productAndCategries, int favid,
                                  MutableLiveData<DefaultAdd> add, MutableLiveData<String> error) {
        productAndCategries.deleteFav(favid).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(mainView ->
                this.postAddToDelete(mainView, add), throwable -> postError(throwable, error));
    }

    private void postAddToDelete(DefaultAdd mainViewData, MutableLiveData<DefaultAdd> data) {
        data.postValue(mainViewData);
    }


    private void postAddToFav(AddToFavModel mainViewData, MutableLiveData<AddToFavModel> data) {
        data.postValue(mainViewData);
    }

}
