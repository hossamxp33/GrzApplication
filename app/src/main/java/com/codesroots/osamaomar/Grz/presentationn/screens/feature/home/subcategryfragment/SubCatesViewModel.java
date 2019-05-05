package com.codesroots.osamaomar.Grz.presentationn.screens.feature.home.subcategryfragment;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.codesroots.osamaomar.Grz.datalayer.repositries.ProductAndCategries;
import com.codesroots.osamaomar.Grz.models.entities.AddToFavModel;
import com.codesroots.osamaomar.Grz.models.entities.DefaultAdd;
import com.codesroots.osamaomar.Grz.models.entities.SubCategriesWithProducts;
import com.codesroots.osamaomar.Grz.models.usecases.SubcatesWithProductsUseCase;
import io.reactivex.disposables.CompositeDisposable;


public class SubCatesViewModel extends ViewModel {

    public MutableLiveData<SubCategriesWithProducts> subCategriesWithProductsMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> throwableMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<AddToFavModel> addToFavMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<DefaultAdd> deleteFav = new MutableLiveData<>();
    public MutableLiveData<String> throwablefav = new MutableLiveData<>();
    public int current_item = 0;
    SubcatesWithProductsUseCase useCase ;

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private ProductAndCategries productAndCategriesrepositry;

     public SubCatesViewModel(ProductAndCategries productAndCategries, SubcatesWithProductsUseCase useCase1) {

        productAndCategriesrepositry = productAndCategries;
        useCase = useCase1;
    }

    public void getData(int catid,int userid) {
       useCase.retrieveSubCatesWithproductData(mCompositeDisposable,productAndCategriesrepositry,
               subCategriesWithProductsMutableLiveData,throwableMutableLiveData,catid,userid);
    }



    public  void AddToFav (int productid,int user)
    {
     useCase.productsUseCase.addFavToProduc(mCompositeDisposable,productAndCategriesrepositry,productid,user,addToFavMutableLiveData,throwablefav);
    }

    public  void DeleteFav (int favid)
    {
      useCase.productsUseCase.deleteFavToProduc(mCompositeDisposable,productAndCategriesrepositry,favid,deleteFav,throwablefav);
    }


//
//    //////////////// add to fav
//    @SuppressLint("CheckResult")
//    private Observable<AddToFavModel> getObservableToFavObservable(int productid) {
//        Observable<AddToFavModel> addToFavObservable = serverGateway.addToFave(userid,productid);
//        addToFavObservable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//        return addToFavObservable;
//    }
//
//    private DisposableObserver<AddToFavModel> getObserverAddFav() {
//        return new DisposableObserver<AddToFavModel>() {
//            @Override
//            public void onNext(@NonNull AddToFavModel result) {
//                addToFavMutableLiveData.postValue(result);
//            }
//            @Override
//            public void onError(@NonNull Throwable e) {
//                Log.d("Errors", "Error" + e);
//                e.printStackTrace();
//                throwablefav.postValue(e);
//            }
//            @Override
//            public void onComplete() {
//            }
//        };
//    }
//
//
//    ///////////// delete fav
//    @SuppressLint("CheckResult")
//    private Observable<DefaultAdd> getObservableToDeleteFav(int favid) {
//        Observable<DefaultAdd> addToFavObservable = serverGateway.DeleteFav(favid);
//        addToFavObservable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//        return addToFavObservable;
//    }
//
//    private DisposableObserver<DefaultAdd> getObserverDeletFav() {
//        return new DisposableObserver<DefaultAdd>() {
//            @Override
//            public void onNext(@NonNull DefaultAdd result) {
//                deleteToFavMutableLiveData.postValue(result.isSuccess());
//            }
//            @Override
//            public void onError(@NonNull Throwable e) {
//                Log.d("Errors", "Error" + e);
//                e.printStackTrace();
//                throwablefav.postValue(e);
//            }
//            @Override
//            public void onComplete() {
//            }
//        };
//    }
}
