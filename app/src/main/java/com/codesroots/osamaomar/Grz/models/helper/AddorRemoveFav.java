package com.codesroots.osamaomar.Grz.models.helper;


import com.codesroots.osamaomar.Grz.models.entities.Product;

public interface AddorRemoveFav {

    public void onAddProductFav(Product product,int userid);
    public void onRemoveProductFav(Product product,int userid);
}
