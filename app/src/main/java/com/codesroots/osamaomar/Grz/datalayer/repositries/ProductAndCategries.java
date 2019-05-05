package com.codesroots.osamaomar.Grz.datalayer.repositries;
import com.codesroots.osamaomar.Grz.datalayer.apidata.ServerGateway;
import com.codesroots.osamaomar.Grz.models.entities.AddToFavModel;
import com.codesroots.osamaomar.Grz.models.entities.DefaultAdd;
import com.codesroots.osamaomar.Grz.models.entities.MainView;
import com.codesroots.osamaomar.Grz.models.entities.ProductDetails;
import com.codesroots.osamaomar.Grz.models.entities.Products;
import com.codesroots.osamaomar.Grz.models.entities.SubCategriesWithProducts;
import com.codesroots.osamaomar.Grz.models.entities.offers;
import io.reactivex.Observable;

public class ProductAndCategries {

    ServerGateway serverGateway;

    public ProductAndCategries(ServerGateway serverGateway) {
        this.serverGateway = serverGateway;
    }


    public Observable<MainView> retrieveHomeFragmentData()
    {
       return serverGateway.getMainViewData();
    }



    public Observable<ProductDetails> retrieveDetailsObservable(int productid,int userid)
    {
        return serverGateway.getProductDetails(productid,userid);
    }

    public Observable<Products> retrieveProductsData(int catid, int type, int userid)
    {
        return serverGateway.getProducts(catid,type,userid);
    }

    public Observable<offers> retrieveOffer()
    {
        return serverGateway.retrieveOffers();
    }


    public Observable<Products> retrieveSearchProductsData(String searchkey, String type, int userid)
    {
        return serverGateway.getSearchResult(searchkey,type,userid);
    }


    public Observable<SubCategriesWithProducts> retrieveSubCatesWithProduct(int categryid, int userid)
    {
        return serverGateway.getSubCatswithProducts(categryid,userid);
    }

    public Observable<AddToFavModel> addToFav(int userid, int productid)
    {
        return serverGateway.addToFave(userid,productid);
    }

    public Observable<DefaultAdd> deleteFav(int favid)
    {
        return serverGateway.DeleteFav(favid);
    }

}
